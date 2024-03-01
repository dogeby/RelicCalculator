package com.dogeby.reliccalculator.core.model.mihomo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpaceInfo(
    @SerialName("achievement_count") val achievementCount: Int,
)
