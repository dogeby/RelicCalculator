package com.dogeby.reliccalculator.rating

import com.dogeby.reliccalculator.core.model.data.hoyo.SubAffix
import com.dogeby.reliccalculator.rating.model.SubStatValueTable
import java.io.File
import kotlin.math.floor
import kotlinx.serialization.json.Json

object RelicRating {

    private val subStatValueTable: SubStatValueTable = Json.decodeFromString(
        File("src/main/resources/sub_stat_value_table.json").readText(),
    )
    private const val HIGH = "High"

    fun calculateStatScore(subAffix: SubAffix): Float {
        val statIncreaseValues = subStatValueTable.stats[subAffix.type] ?: emptyMap()
        val maxStat = statIncreaseValues.getOrDefault(HIGH, 0.0) * subAffix.count
        return (floor(subAffix.value * 50 / maxStat) / 10).toFloat().run {
            coerceIn(0f, 5f)
        }
    }
}
