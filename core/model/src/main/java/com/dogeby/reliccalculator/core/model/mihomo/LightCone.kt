package com.dogeby.reliccalculator.core.model.mihomo

import kotlinx.serialization.Serializable

@Serializable
data class LightCone(
    val id: String,
    val rarity: Int,
    val rank: Int,
    val level: Int,
)
