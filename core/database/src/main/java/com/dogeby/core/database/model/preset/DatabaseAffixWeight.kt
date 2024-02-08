package com.dogeby.core.database.model.preset

import com.dogeby.reliccalculator.core.model.data.preset.AffixWeight
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DatabaseAffixWeight(
    @SerialName("affix_type") val type: String,
    @SerialName("affix_weight") val weight: Float,
)

fun DatabaseAffixWeight.toAffixWeight() = AffixWeight(
    type = type,
    weight = weight,
)
