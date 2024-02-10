package com.dogeby.reliccalculator.core.model.data.hoyo

import kotlinx.serialization.Serializable

@Serializable
data class Profile(
    val player: Player,
    val characters: List<Character>,
)
