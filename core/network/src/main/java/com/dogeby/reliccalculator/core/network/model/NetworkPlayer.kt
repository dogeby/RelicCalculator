package com.dogeby.reliccalculator.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkPlayer(
    val uid: String,
    val nickname: String,
    @SerialName("space_info") val networkSpaceInfo: NetworkSpaceInfo,
)
