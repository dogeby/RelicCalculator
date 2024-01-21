package com.dogeby.core.database.model.preset

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DatabaseRelicStatWeight(
    @SerialName("stat_type") val type: String,
    @SerialName("stat_weight") val weight: Float,
)
