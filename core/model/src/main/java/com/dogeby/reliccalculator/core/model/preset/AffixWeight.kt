package com.dogeby.reliccalculator.core.model.preset

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AffixWeight(
    @SerialName("affix_id") val affixId: String,
    val type: String,
    val weight: Float,
)
