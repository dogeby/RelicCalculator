package com.dogeby.reliccalculator.core.model.mihomo

import kotlinx.serialization.Serializable

@Serializable
data class Profile(
    val player: Player,
    val characters: List<Character>,
)
