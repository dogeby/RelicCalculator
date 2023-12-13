package com.dogeby.reliccalculator.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkPlayer(
    val uid: String,
    val nickname: String,
    val achievementCount: Int,
)
