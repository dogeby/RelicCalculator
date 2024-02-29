package com.dogeby.reliccalculator.core.model.mihomo

import kotlinx.serialization.Serializable

@Serializable
data class MainAffix(
    val type: String,
    val name: String,
    val icon: String,
    val value: Double,
    val display: String,
    val percent: Boolean,
)
