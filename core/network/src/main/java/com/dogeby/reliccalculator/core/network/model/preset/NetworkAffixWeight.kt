package com.dogeby.reliccalculator.core.network.model.preset

import kotlinx.serialization.Serializable

@Serializable
data class NetworkAffixWeight(
    val type: String,
    val weight: Float,
)
