package com.dogeby.reliccalculator.core.database.model.report

import androidx.room.Embedded
import androidx.room.Relation
import com.dogeby.reliccalculator.core.database.model.hoyo.CharacterEntity

data class CharacterWithReports(
    @Embedded val characterEntity: CharacterEntity,
    @Relation(
        parentColumn = "character_id",
        entityColumn = "character_id",
    )
    val reports: List<CharacterReportEntity>,
)
