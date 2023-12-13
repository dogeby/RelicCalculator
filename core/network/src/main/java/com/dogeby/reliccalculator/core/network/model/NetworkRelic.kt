package com.dogeby.reliccalculator.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkRelic(
    val id: String,
    val name: String,
    val setId: String,
    val setName: String,
    val rarity: Int,
    val level: Int,
    val icon: String,
    val mainAffix: NetworkMainAffix,
    val subAffix: NetworkSubAffix,
)
