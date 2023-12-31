package com.dogeby.reliccalculator.core.network.model.hoyo

import kotlinx.serialization.Serializable

@Serializable
data class NetworkProfile(
    val player: NetworkPlayer,
    val characters: List<NetworkCharacter>,
)
