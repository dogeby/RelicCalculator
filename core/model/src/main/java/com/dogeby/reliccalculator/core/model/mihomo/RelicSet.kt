package com.dogeby.reliccalculator.core.model.mihomo

import kotlinx.serialization.Serializable

@Serializable
data class RelicSet(
    val id: String,
    val name: String,
    val icon: String,
)
