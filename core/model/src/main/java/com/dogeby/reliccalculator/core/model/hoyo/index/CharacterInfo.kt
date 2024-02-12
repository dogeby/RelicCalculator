package com.dogeby.reliccalculator.core.model.hoyo.index

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharacterInfo(
    val id: String,
    val name: String,
    val tag: String,
    val rarity: Int,
    val path: String,
    val element: String,
    @SerialName("max_sp") val maxSp: Int,
    val icon: String,
    val preview: String,
    val portrait: String,
)
