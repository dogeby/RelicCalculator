package com.dogeby.reliccalculator.core.network.model.preset

import com.dogeby.reliccalculator.core.model.data.preset.AffixWeight
import kotlinx.serialization.Serializable

@Serializable
data class NetworkAffixWeight(
    val type: String,
    val weight: Float,
)

fun NetworkAffixWeight.toAffixWeight() = AffixWeight(
    type = type,
    weight = weight,
)
