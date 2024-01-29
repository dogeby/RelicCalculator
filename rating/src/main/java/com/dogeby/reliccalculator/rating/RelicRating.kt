package com.dogeby.reliccalculator.rating

import com.dogeby.reliccalculator.core.model.data.hoyo.Character
import com.dogeby.reliccalculator.core.model.data.hoyo.Relic
import com.dogeby.reliccalculator.core.model.data.hoyo.SubAffix
import com.dogeby.reliccalculator.core.model.data.preset.Preset
import com.dogeby.reliccalculator.core.model.data.report.AffixReport
import com.dogeby.reliccalculator.core.model.data.report.CharacterReport
import com.dogeby.reliccalculator.core.model.data.report.RelicReport
import com.dogeby.reliccalculator.rating.model.SubStatValueTable
import java.io.File
import kotlin.math.floor
import kotlinx.serialization.json.Json

object RelicRating {

    private val subStatValueTable: SubStatValueTable = Json.decodeFromString(
        File("src/main/resources/sub_stat_value_table.json").readText(),
    )
    private const val HIGH = "High"
    private const val HEAD_MAIN_AFFIX_TYPE = "HPDelta"
    private const val HAND_MAIN_AFFIX_TYPE = "AttackDelta"

    private const val STAT_INCREASE_DEFAULT = 0.0
    private const val MAX_STAT_WEIGHT = 1f

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

    private fun Float.convertRatingExpression(
        minScore: Float = MIN_SCORE,
        maxScore: Float = MAX_SCORE,
    ): Float {
        require(minScore <= maxScore) { "minScore must be less than or equal to maxScore" }
        return (floor(this * 10) / 10).run {
            coerceIn(minScore, maxScore)
        }
    }

    private fun getRelicStatTypeWeight(
        type: String,
        preset: Preset,
    ): Float {
        return preset.relicStatWeights.find { type == it.type }?.weight ?: 0f
    }

    fun calculateSubAffixScore(subAffix: SubAffix): Float {
        val statIncreaseValues = subStatValueTable.stats[subAffix.type] ?: emptyMap()
        val maxStat = statIncreaseValues.getOrDefault(HIGH, STAT_INCREASE_DEFAULT) * subAffix.count
        return (subAffix.value * MAX_SCORE / maxStat).toFloat().convertRatingExpression()
    }

    fun calculateRelicScore(
        relic: Relic,
        preset: Preset,
    ): RelicReport {
        val mainAffixReport = relic.mainAffix.run {
            val weight = if (type == HEAD_MAIN_AFFIX_TYPE || type == HAND_MAIN_AFFIX_TYPE) {
                MAX_STAT_WEIGHT
            } else {
                getRelicStatTypeWeight(type, preset)
            }
            AffixReport(
                type = type,
                score = MAX_SCORE * weight,
            )
        }

        val topWeightsSum = preset.relicStatWeights
            .asSequence()
            .filter { subStatValueTable.stats.contains(it.type) }
            .sortedByDescending { it.weight }
            .take(MAX_SUB_AFFIXES)
            .map { it.weight }
            .sum()
        val subAffixReports = relic.subAffix.map { subAffix ->
            val score = calculateSubAffixScore(subAffix)
            AffixReport(
                type = subAffix.type,
                score = score * getRelicStatTypeWeight(subAffix.type, preset),
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

        return CharacterReport(
            character = character,
            score = characterScore.convertRatingExpression(),
            relicReports = relicReports,
        )
    }
}
