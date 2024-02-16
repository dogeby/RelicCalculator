package com.dogeby.core.database.model.hoyo.index

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dogeby.reliccalculator.core.model.hoyo.index.PathInfo
import org.jetbrains.annotations.TestOnly

@Entity(tableName = "pathsInfo")
data class PathInfoEntity(
    @PrimaryKey val id: String,
    val name: String,
    val icon: String,
)

fun PathInfoEntity.toPathInfo() = PathInfo(
    id = id,
    name = name,
    icon = icon,
)

@TestOnly
val samplePathInfoEntity = PathInfoEntity(
    id = "Warrior",
    name = "파멸",
    icon = "icon/path/Destruction.png",
)
