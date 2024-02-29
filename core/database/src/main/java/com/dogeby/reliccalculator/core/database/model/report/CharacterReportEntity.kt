package com.dogeby.reliccalculator.core.database.model.report

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.dogeby.reliccalculator.core.database.util.AffixCountListConverter
import com.dogeby.reliccalculator.core.database.util.AttrComparisonReportListConverter
import com.dogeby.reliccalculator.core.database.util.InstantConverter
import com.dogeby.reliccalculator.core.database.util.RelicReportListConverter
import com.dogeby.reliccalculator.core.model.preset.ComparisonOperator
import com.dogeby.reliccalculator.core.model.report.AffixCount
import com.dogeby.reliccalculator.core.model.report.AffixReport
import com.dogeby.reliccalculator.core.model.report.AttrComparisonReport
import com.dogeby.reliccalculator.core.model.report.RelicReport
import kotlinx.datetime.Instant
import kotlinx.datetime.toInstant
import org.jetbrains.annotations.TestOnly

@Entity(tableName = "character_reports")
@TypeConverters(
    RelicReportListConverter::class,
    AttrComparisonReportListConverter::class,
    InstantConverter::class,
    AffixCountListConverter::class,
)
data class CharacterReportEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "report_id")
    val id: Int = 0,
    @ColumnInfo(name = "character_id") val characterId: String,
    @ColumnInfo(name = "character_score") val score: Float,
    @ColumnInfo(name = "relic_reports") val relicReports: List<RelicReport>,
    @ColumnInfo(name = "attr_comparison_reports")
    val attrComparisonReports: List<AttrComparisonReport>,
    @ColumnInfo(name = "valid_affix_counts")
    val validAffixCount: List<AffixCount>,
    val generationTime: Instant,
)

@TestOnly
val sampleCharacterReportEntity = CharacterReportEntity(
    id = 0,
    characterId = "1212",
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
    validAffixCount = List(3) {
        AffixCount(
            type = "type$it",
            count = it,
        )
    },
    generationTime = "2024-02-11T00:04:07.553347500Z".toInstant(),
)
