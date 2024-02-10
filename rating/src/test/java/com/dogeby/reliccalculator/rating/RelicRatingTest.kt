package com.dogeby.reliccalculator.rating

import com.dogeby.reliccalculator.core.model.data.hoyo.Attribute
import com.dogeby.reliccalculator.core.model.data.hoyo.Character
import com.dogeby.reliccalculator.core.model.data.hoyo.Element
import com.dogeby.reliccalculator.core.model.data.hoyo.LightCone
import com.dogeby.reliccalculator.core.model.data.hoyo.MainAffix
import com.dogeby.reliccalculator.core.model.data.hoyo.Path
import com.dogeby.reliccalculator.core.model.data.hoyo.Relic
import com.dogeby.reliccalculator.core.model.data.hoyo.RelicSet
import com.dogeby.reliccalculator.core.model.data.hoyo.SubAffix
import com.dogeby.reliccalculator.core.model.data.preset.AffixWeight
import com.dogeby.reliccalculator.core.model.data.preset.AttrComparison
import com.dogeby.reliccalculator.core.model.data.preset.ComparisonOperator
import com.dogeby.reliccalculator.core.model.data.preset.Preset
import org.junit.Assert
import org.junit.Test

class RelicRatingTest {

    private val relicRating = RelicRating
    private val relic: Relic = Relic(
        id = "61091",
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
    private val character = Character(
        id = "",
        name = "",
        icon = "",
        preview = "",
        portrait = "",
        path = Path("", "", ""),
        element = Element("", "", ""),
        lightCone = LightCone("", "", "", ""),
        relics = listOf(relic),
        relicSets = List(3) { RelicSet("", "", "") },
        attributes = listOf(
            Attribute(
                field = "atk",
                name = "ATK",
                icon = "icon/property/IconAttack.png",
                value = 1266.5520000000001,
                display = "1266",
                percent = false,
            ),
        ),
        additions = listOf(
            Attribute(
                field = "atk",
                name = "ATK",
                icon = "icon/property/IconAttack.png",
                value = 2010.1731806723133,
                display = "2010",
                percent = false,
            ),
        ),
    )
    private val preset = Preset(
        characterId = "",
        relicSetIds = listOf("104"),
        pieceMainAffixWeights = mapOf(
            Pair(1, listOf(AffixWeight("HPDelta", 1.0f))),
            Pair(2, listOf(AffixWeight("AttackDelta", 1.0f))),
            Pair(3, listOf(AffixWeight("CriticalChanceBase", 1.0f))),
            Pair(4, listOf(AffixWeight("SpeedDelta", 1.0f))),
            Pair(5, listOf(AffixWeight("PhysicalAddedRatio", 1.0f))),
            Pair(6, listOf(AffixWeight("SPRatioBase", 1.0f))),
        ),
        subAffixWeights = listOf(
            AffixWeight(
                type = "AttackAddedRatio",
                weight = 0.75f,
            ),
            AffixWeight(
                type = "SpeedDelta",
                weight = 1f,
            ),
            AffixWeight(
                type = "CriticalDamageBase",
                weight = 1f,
            ),
        ),
        isAutoUpdate = false,
        attrComparisons = listOf(
            AttrComparison(
                field = "atk",
                name = "ATK",
                icon = "icon/property/IconAttack.png",
                comparedValue = 500.0f,
                display = "500",
                percent = false,
                comparisonOperator = ComparisonOperator.GREATER_THAN,
            ),
        ),
    )

    @Test
    fun test_calculateSubAffixScore_success() {
        val expectedResult = listOf(4.5f, 5.0f, 4.1f, 4.0f)
        relic.subAffix.forEachIndexed { index, subAffix ->
            val actualResult = relicRating.calculateSubAffixScore(subAffix)
            Assert.assertEquals(expectedResult[index], actualResult)
        }
    }

    @Test
    fun test_calculateRelicScore_success() {
        Assert.assertEquals(4.6f, relicRating.calculateRelicScore(relic, preset).score)
    }

    @Test
    fun test_calculateAttrComparison_success() {
        Assert.assertTrue(
            relicRating.calculateAttrComparison(
                character = character,
                attrComparison = preset.attrComparisons.first(),
            )?.isPass ?: false,
        )
    }

    @Test
    fun test_calculateCharacterScore_success() {
        Assert.assertEquals(0.7f, relicRating.calculateCharacterScore(character, preset).score)
    }

    @Test
    fun test_calculateCharacterScore_mismatchedRelicSets() {
        val mismatchedRelicSetsCharacter = character.copy(
            relicSets = List(2) { RelicSet("", "", "") },
        )
        Assert.assertEquals(
            0.2f,
            relicRating.calculateCharacterScore(
                mismatchedRelicSetsCharacter,
                preset,
            ).score,
        )
    }
}
