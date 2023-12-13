package com.dogeby.reliccalculator.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkProfile(
    val player: NetworkPlayer,
    val characters: List<NetworkCharacter>,
)
