package com.dogeby.core.database.model.preset

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.dogeby.core.database.util.AffixWeightListConverter
import com.dogeby.core.database.util.AffixWeightMapConverter
import com.dogeby.core.database.util.AttrComparisonListConverter
import com.dogeby.core.database.util.RelicSetIdListConverter
import com.dogeby.reliccalculator.core.model.data.preset.ComparisonOperator
import com.dogeby.reliccalculator.core.model.data.preset.Preset
import org.jetbrains.annotations.TestOnly

@Entity(tableName = "presets")
@TypeConverters(
    RelicSetIdListConverter::class,
    AffixWeightMapConverter::class,
    AffixWeightListConverter::class,
    AttrComparisonListConverter::class,
)
data class PresetEntity(
    @PrimaryKey
    @ColumnInfo(name = "character_id")
    val characterId: String,
    @ColumnInfo(name = "relic_set_ids") val relicSetIds: List<String>,
    @ColumnInfo(name = "piece_main_affix_weights")
    val pieceMainAffixWeights: Map<Int, List<DatabaseAffixWeight>>,
    @ColumnInfo(name = "sub_affix_weights") val subAffixWeights: List<DatabaseAffixWeight>,
    @ColumnInfo(name = "is_auto_update") val isAutoUpdate: Boolean,
    @ColumnInfo(name = "attr_comparisons") val attrComparisons: List<DatabaseAttrComparison>,
)

fun PresetEntity.toPreset() = Preset(
    characterId = characterId,
    relicSetIds = relicSetIds,
    pieceMainAffixWeights = pieceMainAffixWeights.mapValues {
        it.value.map(DatabaseAffixWeight::toAffixWeight)
    },
    subAffixWeights = subAffixWeights.map(DatabaseAffixWeight::toAffixWeight),
    isAutoUpdate = isAutoUpdate,
    attrComparisons = attrComparisons.map(DatabaseAttrComparison::toAttrComparison),
)

@TestOnly
val samplePresetEntity = PresetEntity(
    characterId = "1212",
    relicSetIds = listOf("00", "01"),
    pieceMainAffixWeights = mapOf(
        Pair(1, listOf(DatabaseAffixWeight("HPDelta", 1.0f))),
        Pair(2, listOf(DatabaseAffixWeight("AttackDelta", 1.0f))),
        Pair(3, listOf(DatabaseAffixWeight("CriticalChanceBase", 1.0f))),
        Pair(4, listOf(DatabaseAffixWeight("SpeedDelta", 1.0f))),
        Pair(5, listOf(DatabaseAffixWeight("PhysicalAddedRatio", 1.0f))),
        Pair(6, listOf(DatabaseAffixWeight("SPRatioBase", 1.0f))),
    ),
    subAffixWeights = listOf(
        DatabaseAffixWeight(
            type = "type0",
            weight = 1.0f,
        ),
        DatabaseAffixWeight(
            type = "type1",
            weight = 0.5f,
        ),
    ),
    isAutoUpdate = false,
    attrComparisons = listOf(
        DatabaseAttrComparison(
            field = "atk",
            name = "ATK",
            icon = "icon/property/IconAttack.png",
            percent = false,
            comparedValue = 500.0f,
            comparisonOperator = ComparisonOperator.GREATER_THAN,
        ),
    ),
)
