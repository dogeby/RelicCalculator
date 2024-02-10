package com.dogeby.core.database.model.report

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.dogeby.core.database.util.RelicReportListConverter
import com.dogeby.reliccalculator.core.model.data.report.AffixReport
import com.dogeby.reliccalculator.core.model.data.report.RelicReport
import org.jetbrains.annotations.TestOnly

@Entity(tableName = "character_reports")
@TypeConverters(RelicReportListConverter::class)
data class CharacterReportEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "report_id")
    val id: Int = 0,
    @ColumnInfo(name = "character_id") val characterId: String,
    @ColumnInfo(name = "character_score") val score: Float,
    @ColumnInfo(name = "relic_reports") val relicReports: List<RelicReport>,
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
)
