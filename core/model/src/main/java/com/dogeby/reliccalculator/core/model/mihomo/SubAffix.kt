package com.dogeby.reliccalculator.core.model.mihomo

import kotlinx.serialization.Serializable

@Serializable
data class SubAffix(
    val type: String,
    val value: Double,
    val display: String,
    val percent: Boolean,
    val count: Int,
)
