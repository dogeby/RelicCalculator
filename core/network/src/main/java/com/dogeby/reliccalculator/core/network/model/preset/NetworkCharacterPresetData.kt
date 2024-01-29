package com.dogeby.reliccalculator.core.network.model.preset

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkCharacterPresetData(
    @SerialName("update_date") val updateDate: String,
    @SerialName("character_presets") val characterPresets: List<NetworkCharacterPreset>
)
