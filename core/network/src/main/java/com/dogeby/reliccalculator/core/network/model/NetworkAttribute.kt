package com.dogeby.reliccalculator.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkAttribute(
    val field: String,
    val name: String,
    val icon: String,
    val value: Double,
    val display: String,
    val percent: Boolean,
)
