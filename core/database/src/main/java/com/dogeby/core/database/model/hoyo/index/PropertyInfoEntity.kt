package com.dogeby.core.database.model.hoyo.index

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dogeby.reliccalculator.core.model.hoyo.index.PropertyInfo
import org.jetbrains.annotations.TestOnly

@Entity(tableName = "propertiesInfo")
data class PropertyInfoEntity(
    @PrimaryKey val type: String,
    val name: String,
    val field: String,
    val affix: Boolean,
    val ratio: Boolean,
    val percent: Boolean,
    val order: Int,
    val icon: String,
)

fun PropertyInfoEntity.toPropertyInfo() = PropertyInfo(
    type = type,
    name = name,
    field = field,
    affix = affix,
    ratio = ratio,
    percent = percent,
    order = order,
    icon = icon,
)

@TestOnly
val samplePropertyInfoEntity = PropertyInfoEntity(
    type = "SpeedDelta",
    name = "속도",
    field = "spd",
    affix = true,
    ratio = false,
    percent = false,
    order = 100,
    icon = "icon/property/IconSpeed.png",
)
