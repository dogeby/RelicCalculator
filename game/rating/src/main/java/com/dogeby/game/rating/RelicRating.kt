package com.dogeby.game.rating

import com.dogeby.game.rating.model.SubAffixValueTable
import com.dogeby.reliccalculator.core.model.hoyo.Character
import com.dogeby.reliccalculator.core.model.hoyo.Relic
import com.dogeby.reliccalculator.core.model.hoyo.SubAffix
import com.dogeby.reliccalculator.core.model.preset.AffixWeight
import com.dogeby.reliccalculator.core.model.preset.AttrComparison
import com.dogeby.reliccalculator.core.model.preset.ComparisonOperator
import com.dogeby.reliccalculator.core.model.preset.Preset
import com.dogeby.reliccalculator.core.model.report.AffixCount
import com.dogeby.reliccalculator.core.model.report.AffixReport
import com.dogeby.reliccalculator.core.model.report.AttrComparisonReport
import com.dogeby.reliccalculator.core.model.report.CharacterReport
import com.dogeby.reliccalculator.core.model.report.RelicReport
import java.io.File
import kotlin.math.floor
import kotlinx.datetime.Clock
import kotlinx.serialization.json.Json

object RelicRating {

    private const val SUB_AFFIX_VALUE_TABLE_PATH = "src/main/resources/sub_affix_value_table.json"

    private const val HIGH = "High"

    private const val AFFIX_INCREASE_DEFAULT = 0.0
    private const val DEFAULT_AFFIX_WEIGHT = 0f

    private const val RELIC_MAX_LEVEL = 15
    private const val RELIC_MAX_RARITY = 5
    private const val MAX_RELICS = 6
    private const val MIN_ACTIVE_RELIC_SETS = 0
    private const val MAX_ACTIVE_RELIC_SETS = 3
    private const val MAX_SUB_AFFIXES = 4

    private const val MIN_SCORE = 0f
    private const val MAX_SCORE = 5f
    private const val RELIC_SET_DEMERIT = 0.5f

    private const val RELIC_SET_SCORE_WEIGHT = 1f
    private const val MAIN_AFFIX_SCORE_WEIGHT = 2f
    private const val SUB_AFFIX_SCORE_WEIGHT = 4f
    private const val TOTAL_RELIC_SCORE_WEIGHT = RELIC_SET_SCORE_WEIGHT +
        MAIN_AFFIX_SCORE_WEIGHT + SUB_AFFIX_SCORE_WEIGHT

    private val subAffixValueTable: SubAffixValueTable = Json.decodeFromString(
        File(SUB_AFFIX_VALUE_TABLE_PATH).readText(),
    )

    private fun Float.convertRatingExpression(
        minScore: Float = MIN_SCORE,
        maxScore: Float = MAX_SCORE,
    ): Float {
        require(minScore <= maxScore) { "minScore must be less than or equal to maxScore" }
        return (floor(this * 10) / 10).run {
            coerceIn(minScore, maxScore)
        }
    }

    private fun getAffixTypeWeight(
        type: String,
        affixWeights: List<AffixWeight>,
    ): Float {
        return affixWeights.find { type == it.type }?.weight ?: DEFAULT_AFFIX_WEIGHT
    }

    fun calculateSubAffixScore(subAffix: SubAffix): Float {
        val affixIncreaseValues = subAffixValueTable.affixes[subAffix.type] ?: emptyMap()
        val maxAffix = affixIncreaseValues.getOrDefault(HIGH, AFFIX_INCREASE_DEFAULT) *
            subAffix.count
        return (subAffix.value * MAX_SCORE / maxAffix).toFloat().convertRatingExpression()
    }

    private fun calculateMainAffixReport(
        relic: Relic,
        preset: Preset,
    ): AffixReport {
        val piece = relic.id.last().digitToInt()
        val affixWeights = preset.pieceMainAffixWeights.getOrDefault(piece, emptyList())
        val weight = getAffixTypeWeight(
            type = relic.mainAffix.type,
            affixWeights = affixWeights,
        )

        return AffixReport(
            type = relic.mainAffix.type,
            score = MAX_SCORE * weight,
        )
    }

    fun calculateRelicScore(
        relic: Relic,
        preset: Preset,
    ): RelicReport {
        val mainAffixReport = calculateMainAffixReport(
            relic = relic,
            preset = preset,
        )

        val topWeightsSum = preset.subAffixWeights
            .asSequence()
            .filter { subAffixValueTable.affixes.contains(it.type) }
            .sortedByDescending { it.weight }
            .take(MAX_SUB_AFFIXES)
            .map { it.weight }
            .sum()
        val subAffixReports = relic.subAffix.map { subAffix ->
            val score = calculateSubAffixScore(subAffix)
            AffixReport(
                type = subAffix.type,
                score = score * getAffixTypeWeight(subAffix.type, preset.subAffixWeights),
            )
        }

        val relicSetScore = (if (relic.setId in preset.relicSetIds) MAX_SCORE else MIN_SCORE) *
            RELIC_SET_SCORE_WEIGHT
        val mainAffixScore = mainAffixReport.score * MAIN_AFFIX_SCORE_WEIGHT
        val subAffixesScore = subAffixReports.fold(0f) { acc, affixReport ->
            acc + affixReport.score
        } / topWeightsSum * SUB_AFFIX_SCORE_WEIGHT
        val relicScore = (relicSetScore + mainAffixScore + subAffixesScore) *
            relic.rarity / RELIC_MAX_RARITY *
            relic.level / RELIC_MAX_LEVEL / TOTAL_RELIC_SCORE_WEIGHT

        return RelicReport(
            id = relic.id,
            score = relicScore.convertRatingExpression(),
            mainAffixReport = mainAffixReport,
            subAffixReports = subAffixReports,
        )
    }

    fun calculateAttrComparison(
        character: Character,
        attrComparison: AttrComparison,
    ): AttrComparisonReport? {
        val attrValue = attrComparison.field.runCatching {
            character.attributes.first { it.field == this }.value +
                character.additions.first { it.field == this }.value
        }.getOrElse { return null }

        val isPass = when (attrComparison.comparisonOperator) {
            ComparisonOperator.GREATER_THAN -> {
                attrValue > attrComparison.comparedValue.toDouble()
            }
            ComparisonOperator.GREATER_THAN_OR_EQUAL_TO -> {
                attrValue >= attrComparison.comparedValue.toDouble()
            }
            ComparisonOperator.LESS_THAN -> {
                attrValue < attrComparison.comparedValue.toDouble()
            }
            ComparisonOperator.LESS_THAN_OR_EQUAL_TO -> {
                attrValue <= attrComparison.comparedValue.toDouble()
            }
        }
        return AttrComparisonReport(
            field = attrComparison.field,
            comparedValue = attrComparison.comparedValue,
            comparisonOperator = attrComparison.comparisonOperator,
            isPass = isPass,
        )
    }

    fun countValidAffixes(
        character: Character,
        preset: Preset,
    ): List<AffixCount> {
        val validAffixes = preset
            .subAffixWeights
            .filter { it.weight > 0 }
        val validAffixesCounts = mutableMapOf<String, Int>()

        character.relics.forEach { relic ->
            relic.subAffix.forEach { subAffix ->
                if (validAffixes.any { it.type == subAffix.type }) {
                    validAffixesCounts[subAffix.type] =
                        validAffixesCounts.getOrDefault(subAffix.type, 0) + subAffix.count
                }
            }
        }

        return validAffixesCounts.map {
            AffixCount(
                type = it.key,
                count = it.value,
            )
        }
    }

    fun calculateCharacterScore(
        character: Character,
        preset: Preset,
    ): CharacterReport {
        val relicReports = character.relics.map {
            calculateRelicScore(it, preset)
        }

        val relicSetDemerit = (MAX_ACTIVE_RELIC_SETS - character.relicSets.count())
            .coerceIn(MIN_ACTIVE_RELIC_SETS..MAX_ACTIVE_RELIC_SETS) *
            RELIC_SET_DEMERIT

        val characterScore = relicReports.fold(0f) { acc, relicReport ->
            acc + relicReport.score
        } / MAX_RELICS - relicSetDemerit

        val attrComparisonReports = preset.attrComparisons.mapNotNull {
            calculateAttrComparison(character, it)
        }

        val validAffixCounts = countValidAffixes(character, preset)

        return CharacterReport(
            character = character,
            preset = preset,
            score = characterScore.convertRatingExpression(),
            relicReports = relicReports,
            attrComparisonReports = attrComparisonReports,
            validAffixCounts = validAffixCounts,
            generationTime = Clock.System.now(),
        )
    }
}
