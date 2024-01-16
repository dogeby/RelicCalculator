package com.dogeby.reliccalculator.core.network.model.hoyo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkCharacter(
    val id: String,
    val name: String,
    val icon: String,
    val preview: String,
    val portrait: String,
    val path: NetworkPath,
    val element: NetworkElement,
    @SerialName("light_cone") val lightCone: NetworkLightCone,
    val relics: List<NetworkRelic>,
    @SerialName("relic_sets") val relicSets: List<NetworkRelicSet>,
    val attributes: List<NetworkAttribute>,
    val additions: List<NetworkAttribute>,
)
