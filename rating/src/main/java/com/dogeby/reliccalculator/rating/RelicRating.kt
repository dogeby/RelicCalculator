package com.dogeby.reliccalculator.rating

import com.dogeby.reliccalculator.core.model.data.hoyo.Relic
import com.dogeby.reliccalculator.core.model.data.hoyo.SubAffix
import com.dogeby.reliccalculator.core.model.data.preset.CharacterPreset
import com.dogeby.reliccalculator.core.model.data.report.AffixReport
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
    private const val RELIC_MAX_LEVEL = 15
    private const val RELIC_MAX_RARITY = 5

    private fun Float.convertRatingExpression(
        minScore: Float = 0f,
        maxScore: Float = 5f,
    ): Float {
        require(minScore <= maxScore) { "minScore must be less than or equal to maxScore" }
        return (floor(this * 10) / 10).run {
            coerceIn(minScore, maxScore)
        }
    }

    private fun getRelicStatTypeWeight(
        type: String,
        preset: CharacterPreset,
    ): Float {
        return preset.relicStatWeights.find { type == it.type }?.weight ?: 0f
    }

    fun calculateSubAffixScore(subAffix: SubAffix): Float {
        val statIncreaseValues = subStatValueTable.stats[subAffix.type] ?: emptyMap()
        val maxStat = statIncreaseValues.getOrDefault(HIGH, 0.0) * subAffix.count
        return (subAffix.value * 5 / maxStat).toFloat().convertRatingExpression()
    }

    fun calculateRelicScore(
        relic: Relic,
        preset: CharacterPreset,
    ): RelicReport {
        val mainAffixReport = relic.mainAffix.run {
            val weight = if (type == HEAD_MAIN_AFFIX_TYPE || type == HAND_MAIN_AFFIX_TYPE) {
                1f
            } else {
                getRelicStatTypeWeight(type, preset)
            }
            AffixReport(
                type = type,
                score = 5.0f * weight,
            )
        }

        val topWeightsSum = preset.relicStatWeights
            .asSequence()
            .filter { subStatValueTable.stats.contains(it.type) }
            .sortedByDescending { it.weight }
            .take(4)
            .map { it.weight }
            .sum()
        val subAffixReports = relic.subAffix.map { subAffix ->
            val score = calculateSubAffixScore(subAffix)
            AffixReport(
                type = subAffix.type,
                score = score * getRelicStatTypeWeight(subAffix.type, preset),
            )
        }

        val relicSetScore = if (relic.setId in preset.relicSetIds) 5.0f else 0f
        val mainAffixScore = mainAffixReport.score * 2f
        val subAffixesScore = subAffixReports.fold(0f) { acc, affixReport ->
            acc + affixReport.score
        } / topWeightsSum * 4f
        val relicScore = (relicSetScore + mainAffixScore + subAffixesScore) *
            relic.rarity * relic.level /
            RELIC_MAX_LEVEL / RELIC_MAX_RARITY / 7

        return RelicReport(
            id = relic.id,
            score = relicScore.convertRatingExpression(),
            mainAffixReport = mainAffixReport,
            subAffixReports = subAffixReports,
        )
    }
}
