package com.dogeby.reliccalculator.core.model.data.hoyo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpaceInfo(
    @SerialName("achievement_count") val achievementCount: Int,
)
