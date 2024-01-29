package com.dogeby.core.database.model.preset

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.dogeby.core.database.util.RelicSetIdListConverter
import com.dogeby.core.database.util.RelicStatWeightListConverter
import org.jetbrains.annotations.TestOnly

@Entity(tableName = "presets")
@TypeConverters(
    RelicSetIdListConverter::class,
    RelicStatWeightListConverter::class,
)
data class PresetEntity(
    @PrimaryKey
    @ColumnInfo(name = "character_id")
    val characterId: String,
    @ColumnInfo(name = "relic_set_ids") val relicSetIds: List<String>,
    @ColumnInfo(name = "relic_stat_weights") val relicStatWeights: List<DatabaseRelicStatWeight>,
)

@TestOnly
val samplePresetEntity = PresetEntity(
    characterId = "1212",
    relicSetIds = listOf("00", "01"),
    relicStatWeights = listOf(
        DatabaseRelicStatWeight(
            type = "type0",
            weight = 1.0f,
        ),
        DatabaseRelicStatWeight(
            type = "type1",
            weight = 0.5f,
        ),
    ),
)
