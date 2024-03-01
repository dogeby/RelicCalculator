package com.dogeby.reliccalculator.core.model.preset

import com.dogeby.reliccalculator.core.model.mihomo.index.RelicPiece
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import org.jetbrains.annotations.TestOnly

@Serializable
data class Preset(
    @SerialName("character_id") val characterId: String,
    @SerialName("relic_set_ids") val relicSetIds: List<String>,
    @SerialName("piece_main_affix_weights")
    val pieceMainAffixWeights: Map<RelicPiece, List<AffixWeight>>,
    @SerialName("sub_affix_weights") val subAffixWeights: List<AffixWeight>,
    @Transient val isAutoUpdate: Boolean = true,
    @SerialName("attr_comparisons") val attrComparisons: List<AttrComparison>,
)

@TestOnly
val samplePreset = Preset(
    characterId = "1212",
    relicSetIds = listOf("104"),
    pieceMainAffixWeights = mapOf(
        Pair(RelicPiece.HEAD, listOf(AffixWeight("1", "HPDelta", 1.0f))),
        Pair(RelicPiece.HAND, listOf(AffixWeight("1", "AttackDelta", 1.0f))),
        Pair(RelicPiece.BODY, listOf(AffixWeight("4", "CriticalChanceBase", 1.0f))),
        Pair(RelicPiece.FOOT, listOf(AffixWeight("4", "SpeedDelta", 1.0f))),
        Pair(RelicPiece.NECK, listOf(AffixWeight("4", "PhysicalAddedRatio", 1.0f))),
        Pair(RelicPiece.OBJECT, listOf(AffixWeight("2", "SPRatioBase", 1.0f))),
    ),
    subAffixWeights = listOf(
        AffixWeight(
            affixId = "5",
            type = "AttackAddedRatio",
            weight = 0.75f,
        ),
        AffixWeight(
            affixId = "7",
            type = "SpeedDelta",
            weight = 1f,
        ),
        AffixWeight(
            affixId = "9",
            type = "CriticalDamageBase",
            weight = 1f,
        ),
    ),
    isAutoUpdate = false,
    attrComparisons = listOf(
        AttrComparison(
            type = "SpeedDelta",
            field = "spd",
            comparedValue = 134.0f,
            display = "134",
            percent = false,
            comparisonOperator = ComparisonOperator.GREATER_THAN,
        ),
    ),
)
