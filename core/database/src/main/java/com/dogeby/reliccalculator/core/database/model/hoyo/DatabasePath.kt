package com.dogeby.reliccalculator.core.database.model.hoyo

import androidx.room.ColumnInfo

data class DatabasePath(
    @ColumnInfo(name = "path_id") val id: String,
    @ColumnInfo(name = "path_name") val name: String,
    @ColumnInfo(name = "path_icon") val icon: String,
)
