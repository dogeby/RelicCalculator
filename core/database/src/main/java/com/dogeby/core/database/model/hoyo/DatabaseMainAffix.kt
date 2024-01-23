package com.dogeby.core.database.model.hoyo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DatabaseMainAffix(
    @SerialName("main_affix_type") val type: String,
    @SerialName("main_affix_name") val name: String,
    @SerialName("main_affix_icon") val icon: String,
    @SerialName("main_affix_value") val value: Double,
    @SerialName("main_affix_display") val display: String,
    @SerialName("main_affix_percent") val percent: Boolean,
)
