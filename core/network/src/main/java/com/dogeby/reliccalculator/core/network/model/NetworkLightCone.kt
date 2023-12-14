package com.dogeby.reliccalculator.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkLightCone(
    val id: String,
    val name: String,
    val icon: String,
    val portrait: String,
)
