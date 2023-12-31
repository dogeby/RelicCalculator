package com.dogeby.reliccalculator.core.model.data.hoyo

data class Character(
    val id: String,
    val name: String,
    val icon: String,
    val preview: String,
    val portrait: String,
    val path: Path,
    val element: Element,
    val lightCone: LightCone,
    val relics: List<Relic>,
    val relicSets: List<RelicSet>,
    val attributes: List<Attribute>,
    val additions: List<Attribute>,
)
