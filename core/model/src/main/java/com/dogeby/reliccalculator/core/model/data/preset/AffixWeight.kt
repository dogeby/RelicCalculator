package com.dogeby.reliccalculator.core.model.data.preset

import kotlinx.serialization.Serializable

@Serializable
data class AffixWeight(
    val type: String,
    val weight: Float,
)
