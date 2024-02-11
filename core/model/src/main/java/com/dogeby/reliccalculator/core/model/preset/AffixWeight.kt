package com.dogeby.reliccalculator.core.model.preset

import kotlinx.serialization.Serializable

@Serializable
data class AffixWeight(
    val type: String,
    val weight: Float,
)
