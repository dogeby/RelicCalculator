package com.dogeby.reliccalculator.core.network.model.preset

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkPreset(
    @SerialName("character_id") val characterId: String,
    @SerialName("relic_set_ids") val relicSetIds: List<String>,
    @SerialName("piece_main_affix_weights")
    val pieceMainAffixWeights: Map<Int, List<NetworkAffixWeight>>,
    @SerialName("sub_affix_weights") val subAffixWeights: List<NetworkAffixWeight>,
)
