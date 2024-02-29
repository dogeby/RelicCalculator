package com.dogeby.reliccalculator.core.database.model.preset

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.dogeby.reliccalculator.core.database.util.AffixWeightListConverter
import com.dogeby.reliccalculator.core.database.util.AttrComparisonListConverter
import com.dogeby.reliccalculator.core.database.util.MainAffixWeightMapConverter
import com.dogeby.reliccalculator.core.database.util.StringListConverter
import com.dogeby.reliccalculator.core.model.hoyo.index.RelicPiece
import com.dogeby.reliccalculator.core.model.preset.AffixWeight
import com.dogeby.reliccalculator.core.model.preset.AttrComparison
import com.dogeby.reliccalculator.core.model.preset.ComparisonOperator
import com.dogeby.reliccalculator.core.model.preset.Preset
import org.jetbrains.annotations.TestOnly

@Entity(tableName = "presets")
@TypeConverters(
    StringListConverter::class,
    MainAffixWeightMapConverter::class,
    AffixWeightListConverter::class,
    AttrComparisonListConverter::class,
)
data class PresetEntity(
    @PrimaryKey
    @ColumnInfo(name = "character_id")
    val characterId: String,
    @ColumnInfo(name = "relic_set_ids") val relicSetIds: List<String>,
    @ColumnInfo(name = "piece_main_affix_weights")
    val pieceMainAffixWeights: Map<RelicPiece, List<AffixWeight>>,
    @ColumnInfo(name = "sub_affix_weights") val subAffixWeights: List<AffixWeight>,
    @ColumnInfo(name = "is_auto_update") val isAutoUpdate: Boolean,
    @ColumnInfo(name = "attr_comparisons") val attrComparisons: List<AttrComparison>,
)

fun PresetEntity.toPreset() = Preset(
    characterId = characterId,
    relicSetIds = relicSetIds,
    pieceMainAffixWeights = pieceMainAffixWeights,
    subAffixWeights = subAffixWeights,
    isAutoUpdate = isAutoUpdate,
    attrComparisons = attrComparisons,
)

@TestOnly
val samplePresetEntity = PresetEntity(
    characterId = "1212",
    relicSetIds = listOf("00", "01"),
    pieceMainAffixWeights = mapOf(
        Pair(RelicPiece.HEAD, listOf(AffixWeight("1", "HPDelta", 1.0f))),
        Pair(RelicPiece.HAND, listOf(AffixWeight("1", "AttackDelta", 1.0f))),
        Pair(RelicPiece.BODY, listOf(AffixWeight("4", "CriticalChanceBase", 1.0f))),
        Pair(RelicPiece.FOOT, listOf(AffixWeight("4", "SpeedDelta", 1.0f))),
        Pair(RelicPiece.NECK, listOf(AffixWeight("4", "PhysicalAddedRatio", 1.0f))),
        Pair(RelicPiece.OBJECT, listOf(AffixWeight("2", "SPRatioBase", 1.0f))),
    ),
    subAffixWeights = listOf(
        AffixWeight(
            affixId = "1",
            type = "type1",
            weight = 1.0f,
        ),
        AffixWeight(
            affixId = "2",
            type = "type2",
            weight = 0.5f,
        ),
    ),
    isAutoUpdate = false,
    attrComparisons = listOf(
        AttrComparison(
            type = "AttackDelta",
            field = "atk",
            comparedValue = 500.0f,
            display = "500",
            percent = false,
            comparisonOperator = ComparisonOperator.GREATER_THAN,
        ),
    ),
)
