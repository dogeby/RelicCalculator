package com.dogeby.reliccalculator.core.model.preset

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class Preset(
    @SerialName("character_id") val characterId: String,
    @SerialName("relic_set_ids") val relicSetIds: List<String>,
    @SerialName("piece_main_affix_weights")
    val pieceMainAffixWeights: Map<Int, List<AffixWeight>>,
    @SerialName("sub_affix_weights") val subAffixWeights: List<AffixWeight>,
    @Transient val isAutoUpdate: Boolean = true,
    @SerialName("attr_comparisons") val attrComparisons: List<AttrComparison>,
)
