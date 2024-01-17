package com.dogeby.core.database.model.hoyo

import androidx.room.ColumnInfo
import kotlinx.serialization.Serializable

@Serializable
data class DatabaseRelicSet(
    @ColumnInfo(name = "relic_set_id") val id: String,
    @ColumnInfo(name = "relic_set_name") val name: String,
    @ColumnInfo(name = "relic_set_icon") val icon: String,
)
