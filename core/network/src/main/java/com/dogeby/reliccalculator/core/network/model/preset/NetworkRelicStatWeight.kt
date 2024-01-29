package com.dogeby.reliccalculator.core.network.model.preset

import kotlinx.serialization.Serializable

@Serializable
data class NetworkRelicStatWeight(
    val type: String,
    val weight: Float,
)
