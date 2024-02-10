package com.dogeby.reliccalculator.core.model.hoyo

import kotlinx.serialization.Serializable

@Serializable
data class LightCone(
    val id: String,
    val name: String,
    val icon: String,
    val portrait: String,
)
