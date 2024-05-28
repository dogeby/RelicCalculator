package com.dogeby.reliccalculator.core.database.model.hoyo

import androidx.room.ColumnInfo
import com.dogeby.reliccalculator.core.model.mihomo.SpaceInfo

data class DatabaseSpaceInfo(
    @ColumnInfo(name = "achievement_count") val achievementCount: Int,
)

fun DatabaseSpaceInfo.toSpaceInfo() = SpaceInfo(achievementCount)
