package com.dogeby.reliccalculator.core.database.model.hoyo.index

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dogeby.reliccalculator.core.model.mihomo.index.CharacterInfo
import org.jetbrains.annotations.TestOnly

@Entity(tableName = "charactersInfo")
data class CharacterInfoEntity(
    @PrimaryKey val id: String,
    val name: String,
    val tag: String,
    val rarity: Int,
    val path: String,
    val element: String,
    @ColumnInfo("max_sp") val maxSp: Int,
    val icon: String,
    val preview: String,
    val portrait: String,
)

fun CharacterInfoEntity.toCharacterInfo() = CharacterInfo(
    id = id,
    name = name,
    tag = tag,
    rarity = rarity,
    path = path,
    element = element,
    maxSp = maxSp,
    icon = icon,
    preview = preview,
    portrait = portrait,
)

@TestOnly
val sampleCharacterInfoEntity = CharacterInfoEntity(
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
