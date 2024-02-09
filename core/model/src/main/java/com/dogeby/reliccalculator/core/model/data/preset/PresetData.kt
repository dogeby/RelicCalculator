package com.dogeby.reliccalculator.core.model.data.preset

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PresetData(
    @SerialName("update_date") val updateDate: String,
    val presets: List<Preset>,
)
