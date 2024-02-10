package com.dogeby.reliccalculator.core.model.hoyo

import kotlinx.serialization.Serializable

@Serializable
data class SubAffix(
    val type: String,
    val name: String,
    val icon: String,
    val value: Double,
    val display: String,
    val percent: Boolean,
    val count: Int,
)
