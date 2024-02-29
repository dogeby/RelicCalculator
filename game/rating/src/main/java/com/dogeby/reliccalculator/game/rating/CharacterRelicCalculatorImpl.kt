package com.dogeby.reliccalculator.game.rating

import com.dogeby.reliccalculator.core.model.mihomo.Character
import com.dogeby.reliccalculator.core.model.mihomo.Relic
import com.dogeby.reliccalculator.core.model.mihomo.SubAffix
import com.dogeby.reliccalculator.core.model.mihomo.index.AffixData
import com.dogeby.reliccalculator.core.model.mihomo.index.AffixInfo
import com.dogeby.reliccalculator.core.model.mihomo.index.RelicInfo
import com.dogeby.reliccalculator.core.model.mihomo.index.RelicPiece
import com.dogeby.reliccalculator.core.model.preset.AffixWeight
import com.dogeby.reliccalculator.core.model.preset.AttrComparison
import com.dogeby.reliccalculator.core.model.preset.ComparisonOperator
import com.dogeby.reliccalculator.core.model.preset.Preset
import com.dogeby.reliccalculator.core.model.report.AffixCount
import com.dogeby.reliccalculator.core.model.report.AffixReport
import com.dogeby.reliccalculator.core.model.report.AttrComparisonReport
import com.dogeby.reliccalculator.core.model.report.CharacterReport
import com.dogeby.reliccalculator.core.model.report.RelicReport
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.floor
import kotlin.math.pow
import kotlinx.datetime.Clock

@Singleton
class CharacterRelicCalculatorImpl @Inject constructor() : CharacterRelicCalculator {

    override fun calculateSubAffixScore(
        subAffix: SubAffix,
        subAffixesInfo: Map<String, AffixInfo>,
    ): Float {
        val affixIncreaseValues = subAffixesInfo[subAffix.type]?.let { affixInfo ->
            affixInfo.base.truncate(AFFIX_TRUNCATE_LENGTH) +
                affixInfo.step.truncate(AFFIX_TRUNCATE_LENGTH) *
                affixInfo.stepNum
        } ?: return DEFAULT_AFFIX_SCORE
        val maxAffix = affixIncreaseValues * subAffix.count
        return (subAffix.value * MAX_SCORE / maxAffix).toFloat().convertRatingExpression()
    }

    override fun calculateMainAffixReport(
        piece: RelicPiece,
        affixType: String,
        preset: Preset,
    ): AffixReport {
        val affixWeights = preset.pieceMainAffixWeights.getOrDefault(piece, emptyList())
        val weight = getAffixTypeWeight(
            type = affixType,
            affixWeights = affixWeights,
        )

        return AffixReport(
            type = affixType,
            score = MAX_SCORE * weight,
        )
    }

    override fun calculateRelicScore(
        relic: Relic,
        preset: Preset,
        relicInfo: RelicInfo,
        subAffixesData: Map<String, AffixData>,
    ): RelicReport {
        val affixTypeToSubAffixes = subAffixesData[relic.rarity.toString()]?.affixes?.mapKeys {
            it.value.property
        } ?: emptyMap()

        val mainAffixReport = calculateMainAffixReport(
            piece = relicInfo.type,
            affixType = relic.mainAffix.type,
            preset = preset,
        )

        val topWeightsSum = preset.subAffixWeights
            .asSequence()
            .sortedByDescending { it.weight }
            .take(MAX_SUB_AFFIXES)
            .map { it.weight }
            .sum()
        val subAffixReports = relic.subAffix.map { subAffix ->
            val score = calculateSubAffixScore(
                subAffix = subAffix,
                subAffixesInfo = affixTypeToSubAffixes,
            )
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

    override fun calculateAttrComparison(
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
            type = attrComparison.type,
            field = attrComparison.field,
            comparedValue = attrComparison.comparedValue,
            comparisonOperator = attrComparison.comparisonOperator,
            isPass = isPass,
        )
    }

    override fun countValidAffixes(
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

    override fun calculateCharacterScore(
        character: Character,
        preset: Preset,
        relicsInfo: Map<String, RelicInfo>,
        subAffixesData: Map<String, AffixData>,
    ): CharacterReport {
        val relicReports = character.relics.map { relic ->
            calculateRelicScore(
                relic = relic,
                preset = preset,
                relicInfo = relicsInfo[relic.id]
                    ?: throw IllegalArgumentException(
                        "Relic id: ${relic.id}, RelicsInfoKeys: ${relicsInfo.keys}",
                    ),
                subAffixesData = subAffixesData,
            )
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

    private fun Double.truncate(n: Int): Double {
        val pow10 = 10.0.pow(n)
        return floor(this * pow10) / pow10
    }

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

    private companion object {

        const val DEFAULT_AFFIX_SCORE = 0.0f
        const val DEFAULT_AFFIX_WEIGHT = 0f
        const val AFFIX_TRUNCATE_LENGTH = 6

        const val RELIC_MAX_LEVEL = 15
        const val RELIC_MAX_RARITY = 5
        const val MAX_RELICS = 6
        const val MIN_ACTIVE_RELIC_SETS = 0
        const val MAX_ACTIVE_RELIC_SETS = 3
        const val MAX_SUB_AFFIXES = 4

        const val MIN_SCORE = 0f
        const val MAX_SCORE = 5f
        const val RELIC_SET_DEMERIT = 0.5f

        const val RELIC_SET_SCORE_WEIGHT = 1f
        const val MAIN_AFFIX_SCORE_WEIGHT = 2f
        const val SUB_AFFIX_SCORE_WEIGHT = 4f
        const val TOTAL_RELIC_SCORE_WEIGHT = RELIC_SET_SCORE_WEIGHT +
            MAIN_AFFIX_SCORE_WEIGHT + SUB_AFFIX_SCORE_WEIGHT
    }
}
