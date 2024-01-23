package com.dogeby.core.database.model.hoyo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DatabaseAttribute(
    @SerialName("attribute_field") val field: String,
    @SerialName("attribute_name") val name: String,
    @SerialName("attribute_icon") val icon: String,
    @SerialName("attribute_value") val value: Double,
    @SerialName("attribute_display") val display: String,
    @SerialName("attribute_percent") val percent: Boolean,
)
