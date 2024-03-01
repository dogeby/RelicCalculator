package com.dogeby.reliccalculator.core.model.mihomo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Character(
    val id: String,
    val name: String,
    val icon: String,
    val preview: String,
    val portrait: String,
    val path: Path,
    val element: Element,
    @SerialName("light_cone") val lightCone: LightCone,
    val relics: List<Relic>,
    @SerialName("relic_sets") val relicSets: List<RelicSet>,
    val attributes: List<Attribute>,
    val additions: List<Attribute>,
)
