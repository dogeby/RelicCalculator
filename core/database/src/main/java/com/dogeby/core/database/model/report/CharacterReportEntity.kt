package com.dogeby.core.database.model.report

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.dogeby.core.database.util.RelicReportListConverter
import org.jetbrains.annotations.TestOnly

@Entity(tableName = "character_reports")
@TypeConverters(RelicReportListConverter::class)
data class CharacterReportEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "report_id")
    val id: Int = 0,
    @ColumnInfo(name = "character_id") val characterId: String,
    @ColumnInfo(name = "character_score") val score: Float,
    @ColumnInfo(name = "relic_reports") val relicReports: List<DatabaseRelicReport>,
)

@TestOnly
val sampleCharacterReportEntity = CharacterReportEntity(
    id = 0,
    characterId = "1212",
    score = 5.0f,
    relicReports = List(3) { index ->
        DatabaseRelicReport(
            id = "$index",
            score = index.toFloat(),
            mainAffixReport = DatabaseAffixReport(
                type = "type$index",
                score = index.toFloat(),
            ),
            subAffixReports = List(3) {
                DatabaseAffixReport(
                    type = "type$it",
                    score = it.toFloat(),
                )
            },
        )
    },
)
