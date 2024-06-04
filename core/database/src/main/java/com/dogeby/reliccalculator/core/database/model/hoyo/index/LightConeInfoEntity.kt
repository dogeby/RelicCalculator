package com.dogeby.reliccalculator.core.database.model.hoyo.index

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dogeby.reliccalculator.core.model.mihomo.index.LightConeInfo
import org.jetbrains.annotations.TestOnly

@Entity(tableName = "lightConeInfoTable")
data class LightConeInfoEntity(
    @PrimaryKey val id: String,
    val name: String,
    val rarity: Int,
    val path: String,
    val icon: String,
    val portrait: String,
)

fun LightConeInfoEntity.toLightConeInfo() = LightConeInfo(
    id = id,
    name = name,
    rarity = rarity,
    path = path,
    icon = icon,
    portrait = portrait,
)

@TestOnly
val sampleLightConeInfoEntity = LightConeInfoEntity(
    id = "23014",
    name = "이 몸이 검이니",
    rarity = 5,
    path = "Warrior",
    icon = "icon/light_cone/23014.png",
    portrait = "image/light_cone_portrait/23014.png",
)
