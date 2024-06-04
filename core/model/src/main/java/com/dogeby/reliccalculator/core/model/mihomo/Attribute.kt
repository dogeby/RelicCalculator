package com.dogeby.reliccalculator.core.model.mihomo

import kotlinx.serialization.Serializable

@Serializable
data class Attribute(
    val field: String,
    val value: Double,
    val display: String,
    val percent: Boolean,
)
