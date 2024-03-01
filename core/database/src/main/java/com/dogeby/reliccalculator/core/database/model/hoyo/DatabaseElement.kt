package com.dogeby.reliccalculator.core.database.model.hoyo

import androidx.room.ColumnInfo

data class DatabaseElement(
    @ColumnInfo(name = "element_id") val id: String,
    @ColumnInfo(name = "element_name") val name: String,
    @ColumnInfo(name = "element_icon") val icon: String,
)
