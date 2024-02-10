package com.dogeby.reliccalculator.core.model.data.hoyo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Player(
    val uid: String,
    val nickname: String,
    @SerialName("space_info") val spaceInfo: SpaceInfo,
)
