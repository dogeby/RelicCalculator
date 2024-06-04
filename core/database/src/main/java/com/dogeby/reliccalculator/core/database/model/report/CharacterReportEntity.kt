package com.dogeby.reliccalculator.core.database.model.report

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.dogeby.reliccalculator.core.database.util.AffixCountListConverter
import com.dogeby.reliccalculator.core.database.util.AttrComparisonReportListConverter
import com.dogeby.reliccalculator.core.database.util.CharacterConverter
import com.dogeby.reliccalculator.core.database.util.InstantConverter
import com.dogeby.reliccalculator.core.database.util.PresetConverter
import com.dogeby.reliccalculator.core.database.util.RelicReportListConverter
import com.dogeby.reliccalculator.core.model.mihomo.Character
import com.dogeby.reliccalculator.core.model.mihomo.LightCone
import com.dogeby.reliccalculator.core.model.preset.ComparisonOperator
import com.dogeby.reliccalculator.core.model.preset.Preset
import com.dogeby.reliccalculator.core.model.report.AffixCount
import com.dogeby.reliccalculator.core.model.report.AffixReport
import com.dogeby.reliccalculator.core.model.report.AttrComparisonReport
import com.dogeby.reliccalculator.core.model.report.CharacterReport
import com.dogeby.reliccalculator.core.model.report.RelicReport
import kotlinx.datetime.Instant
import org.jetbrains.annotations.TestOnly

@Entity(tableName = "character_reports")
@TypeConverters(
    RelicReportListConverter::class,
    AttrComparisonReportListConverter::class,
    InstantConverter::class,
    AffixCountListConverter::class,
    CharacterConverter::class,
    PresetConverter::class,
)
data class CharacterReportEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "report_id")
    val id: Int = 0,
    @ColumnInfo(name = "character_id") val characterId: String,
    val character: Character,
    val preset: Preset,
    @ColumnInfo(name = "character_score") val score: Float,
    @ColumnInfo(name = "relic_reports") val relicReports: List<RelicReport>,
    @ColumnInfo(name = "attr_comparison_reports")
    val attrComparisonReports: List<AttrComparisonReport>,
    @ColumnInfo(name = "valid_affix_counts")
    val validAffixCounts: List<AffixCount>,
    val generationTime: Instant,
)

fun CharacterReportEntity.toCharacterReport() = CharacterReport(
    id = id,
    character = character,
    preset = preset,
    score = score,
    relicReports = relicReports,
    attrComparisonReports = attrComparisonReports,
    validAffixCounts = validAffixCounts,
    generationTime = generationTime,
)

@TestOnly
val sampleCharacterReportEntity = CharacterReportEntity(
    id = 0,
    characterId = "1212",
    character = Character(
        id = "1212",
        lightCone = LightCone("", 5, 1, 80),
        relics = emptyList(),
        relicSets = emptyList(),
        attributes = emptyList(),
        additions = emptyList(),
    ),
    preset = Preset(
        characterId = "1212",
        emptyList(),
        emptyMap(),
        emptyList(),
        attrComparisons = emptyList(),
    ),
    score = 5.0f,
    relicReports = List(3) { index ->
        RelicReport(
            id = "$index",
            score = index.toFloat(),
            mainAffixReport = AffixReport(
                type = "type$index",
                score = index.toFloat(),
            ),
            subAffixReports = List(3) {
                AffixReport(
                    type = "type$it",
                    score = it.toFloat(),
                )
            },
        )
    },
    attrComparisonReports = listOf(
        AttrComparisonReport(
            type = "AttackDelta",
            field = "atk",
            comparedValue = 500.0f,
            comparisonOperator = ComparisonOperator.GREATER_THAN,
            isPass = false,
        ),
    ),
    validAffixCounts = List(3) {
        AffixCount(
            type = "type$it",
            count = it,
        )
    },
    generationTime = Instant.parse("2024-02-11T00:04:07.553347500Z"),
)
