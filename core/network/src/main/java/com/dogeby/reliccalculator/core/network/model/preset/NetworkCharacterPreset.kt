package com.dogeby.reliccalculator.core.network.model.preset

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkCharacterPreset(
    @SerialName("character_id") val characterId: String,
    @SerialName("relic_set_ids") val relicSetIds: List<String>,
    @SerialName("relic_stat_weights") val relicStatWeights: List<NetworkRelicStatWeight>,
)
