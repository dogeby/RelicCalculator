package com.dogeby.reliccalculator.core.network.model.preset

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkPresetData(
    @SerialName("update_date") val updateDate: String,
    @SerialName("presets") val presets: List<NetworkPreset>,
)
