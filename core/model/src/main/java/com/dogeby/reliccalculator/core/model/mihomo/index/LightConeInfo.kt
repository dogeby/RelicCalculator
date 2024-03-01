package com.dogeby.reliccalculator.core.model.mihomo.index

import kotlinx.serialization.Serializable

@Serializable
data class LightConeInfo(
    val id: String,
    val name: String,
    val rarity: Int,
    val path: String,
    val portrait: String,
)
