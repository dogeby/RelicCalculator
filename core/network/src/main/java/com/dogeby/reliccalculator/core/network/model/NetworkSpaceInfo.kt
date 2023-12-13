package com.dogeby.reliccalculator.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkSpaceInfo(
    @SerialName("achievement_count") val achievementCount: Int,
)
