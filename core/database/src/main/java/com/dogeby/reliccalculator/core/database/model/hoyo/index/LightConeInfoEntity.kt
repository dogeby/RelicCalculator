package com.dogeby.reliccalculator.core.database.model.hoyo.index

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dogeby.reliccalculator.core.model.hoyo.index.LightConeInfo
import org.jetbrains.annotations.TestOnly

@Entity(tableName = "lightConesInfo")
data class LightConeInfoEntity(
    @PrimaryKey val id: String,
    val name: String,
    val rarity: Int,
    val path: String,
    val portrait: String,
)

fun LightConeInfoEntity.toLightConeInfo() = LightConeInfo(
    id = id,
    name = name,
    rarity = rarity,
    path = path,
    portrait = portrait,
)

@TestOnly
val sampleLightConeInfoEntity = LightConeInfoEntity(
    id = "23014",
    name = "이 몸이 검이니",
    rarity = 5,
    path = "Warrior",
    portrait = "image/light_cone_portrait/23014.png",
)
