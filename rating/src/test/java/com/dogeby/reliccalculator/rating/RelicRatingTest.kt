package com.dogeby.reliccalculator.rating

import com.dogeby.reliccalculator.core.model.data.hoyo.MainAffix
import com.dogeby.reliccalculator.core.model.data.hoyo.Relic
import com.dogeby.reliccalculator.core.model.data.hoyo.SubAffix
import com.dogeby.reliccalculator.core.model.data.preset.CharacterPreset
import com.dogeby.reliccalculator.core.model.data.preset.RelicStatWeight
import org.junit.Assert
import org.junit.Test

class RelicRatingTest {

    private val relicRating = RelicRating
    private val relic: Relic = Relic(
        id = "",
        name = "",
        setId = "104",
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
    private val preset = CharacterPreset(
        id = "",
        characterId = "",
        relicSetIds = listOf("104"),
        relicStatWeights = listOf(
            RelicStatWeight(
                type = "AttackAddedRatio",
                weight = 0.75f,
            ),
            RelicStatWeight(
                type = "SpeedDelta",
                weight = 1f,
            ),
            RelicStatWeight(
                type = "CriticalDamageBase",
                weight = 1f,
            ),
        ),
    )

    @Test
    fun test_calculate_sub_affix_score() {
        val expectedResult = listOf(4.5f, 5.0f, 4.1f, 4.0f)
        relic.subAffix.forEachIndexed { index, subAffix ->
            val actualResult = relicRating.calculateSubAffixScore(subAffix)
            Assert.assertEquals(expectedResult[index], actualResult)
        }
    }

    @Test
    fun test_calculate_relic_score() {
        Assert.assertEquals(4.6f, relicRating.calculateRelicScore(relic, preset).score)
    }
}
