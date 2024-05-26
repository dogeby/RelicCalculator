package com.dogeby.reliccalculator.core.database.model.hoyo

import androidx.room.ColumnInfo

data class DatabaseSpaceInfo(
    @ColumnInfo(name = "achievement_count") val achievementCount: Int,
)
