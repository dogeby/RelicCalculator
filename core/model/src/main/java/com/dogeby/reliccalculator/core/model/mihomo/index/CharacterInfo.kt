package com.dogeby.reliccalculator.core.model.mihomo.index

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.jetbrains.annotations.TestOnly

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

@TestOnly
val sampleCharacterInfo = CharacterInfo(
    id = "1212",
    name = "경류",
    tag = "jingliu",
    rarity = 5,
    path = "Warrior",
    element = "Ice",
    maxSp = 140,
    icon = "icon/character/1212.png",
    preview = "image/character_preview/1212.png",
    portrait = "image/character_portrait/1212.png",
)
