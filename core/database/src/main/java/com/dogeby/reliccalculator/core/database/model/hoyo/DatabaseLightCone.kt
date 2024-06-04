package com.dogeby.reliccalculator.core.database.model.hoyo

import androidx.room.ColumnInfo

data class DatabaseLightCone(
    @ColumnInfo(name = "light_cone_id") val id: String,
    @ColumnInfo(name = "light_cone_rarity") val rarity: Int,
    @ColumnInfo(name = "light_cone_rank") val rank: Int,
    @ColumnInfo(name = "light_cone_level") val level: Int,
)
