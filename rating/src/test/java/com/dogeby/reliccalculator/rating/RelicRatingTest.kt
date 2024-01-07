package com.dogeby.reliccalculator.rating

import com.dogeby.reliccalculator.core.model.data.hoyo.MainAffix
import com.dogeby.reliccalculator.core.model.data.hoyo.Relic
import com.dogeby.reliccalculator.core.model.data.hoyo.SubAffix
import org.junit.Assert
import org.junit.Test

class RelicRatingTest {

    private val relicRating = RelicRating
    private val relic: Relic = Relic(
        id = "",
        name = "",
        setId = "",
        setName = "",
        rarity = 5,
        level = 15,
        icon = "",
        mainAffix = MainAffix(
            type = "HPDelta",
            name = "",
            icon = "",
            value = 705.6000000101048,
            display = "705",
            percent = false,
        ),
        subAffix = listOf(
            SubAffix(
                type = "DefenceDelta",
                name = "",
                icon = "",
                value = 19.05189600144513,
                display = "19",
                percent = false,
                count = 1,
            ),
            SubAffix(
                type = "AttackAddedRatio",
                name = "",
                icon = "",
                value = 0.043200000654907,
                display = "4.3%",
                percent = true,
                count = 1,
            ),
            SubAffix(
                type = "SpeedDelta",
                name = "",
                icon = "",
                value = 10.90000000083819,
                display = "10",
                percent = false,
                count = 5,
            ),
            SubAffix(
                type = "CriticalDamageBase",
                name = "",
                icon = "",
                value = 0.051840000785887,
                display = "5.1%",
                percent = true,
                count = 1,
            ),
        ),
    )

    @Test
    fun test_calculate_stat_score() {
        val expectedResult = listOf(4.5f, 5.0f, 4.1f, 4.0f)
        relic.subAffix.forEachIndexed { index, subAffix ->
            val actualResult = relicRating.calculateStatScore(subAffix)
            Assert.assertEquals(expectedResult[index], actualResult)
        }
    }
}
