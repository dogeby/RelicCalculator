package com.dogeby.reliccalculator.core.database.model.hoyo.index

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dogeby.reliccalculator.core.model.hoyo.index.ElementInfo
import org.jetbrains.annotations.TestOnly

@Entity(tableName = "elementsInfo")
data class ElementInfoEntity(
    @PrimaryKey val id: String,
    val name: String,
    val color: String,
    val icon: String,
)

fun ElementInfoEntity.toElementInfo() = ElementInfo(
    id = id,
    name = name,
    color = color,
    icon = icon,
)

@TestOnly
val sampleElementInfoEntity = ElementInfoEntity(
    id = "Ice",
    name = "얼음",
    color = "#F84F36",
    icon = "icon/element/Ice.png",
)
