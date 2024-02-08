package com.dogeby.reliccalculator.core.network.model.preset

import com.dogeby.reliccalculator.core.model.data.preset.Preset
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

fun NetworkPreset.toPreset() = Preset(
    characterId = characterId,
    relicSetIds = relicSetIds,
    pieceMainAffixWeights = pieceMainAffixWeights.mapValues {
        it.value.map(NetworkAffixWeight::toAffixWeight)
    },
    subAffixWeights = subAffixWeights.map(NetworkAffixWeight::toAffixWeight),
    isAutoUpdate = false,
)
