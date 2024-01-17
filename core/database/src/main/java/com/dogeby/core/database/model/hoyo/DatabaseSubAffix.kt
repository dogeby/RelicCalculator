package com.dogeby.core.database.model.hoyo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DatabaseSubAffix(
    @SerialName("sub_affix_type") val type: String,
    @SerialName("sub_affix_name") val name: String,
    @SerialName("sub_affix_icon") val icon: String,
    @SerialName("sub_affix_value") val value: Double,
    @SerialName("sub_affix_display") val display: String,
    @SerialName("sub_affix_percent") val percent: Boolean,
    @SerialName("sub_affix_count") val count: Int,
)
