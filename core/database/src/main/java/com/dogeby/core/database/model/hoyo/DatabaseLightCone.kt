package com.dogeby.core.database.model.hoyo

import androidx.room.ColumnInfo

data class DatabaseLightCone(
    @ColumnInfo(name = "light_cone_id") val id: String,
    @ColumnInfo(name = "light_cone_name") val name: String,
    @ColumnInfo(name = "light_cone_icon") val icon: String,
    @ColumnInfo(name = "light_cone_portrait") val portrait: String,
)
