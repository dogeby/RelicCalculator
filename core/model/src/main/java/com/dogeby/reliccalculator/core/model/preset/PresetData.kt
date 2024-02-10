package com.dogeby.reliccalculator.core.model.preset

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PresetData(
    @SerialName("update_date") val updateDate: Instant,
    val presets: List<Preset>,
)
