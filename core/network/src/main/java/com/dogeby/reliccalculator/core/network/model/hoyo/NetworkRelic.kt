package com.dogeby.reliccalculator.core.network.model.hoyo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkRelic(
    val id: String,
    val name: String,
    @SerialName("set_id") val setId: String,
    @SerialName("set_name") val setName: String,
    val rarity: Int,
    val level: Int,
    val icon: String,
    @SerialName("main_affix") val mainAffix: NetworkMainAffix,
    @SerialName("sub_affix") val subAffix: List<NetworkSubAffix>,
)
