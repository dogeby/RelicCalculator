package com.dogeby.reliccalculator.core.data.model

import com.dogeby.reliccalculator.core.database.model.report.CharacterReportEntity
import com.dogeby.reliccalculator.core.model.report.CharacterReport

fun CharacterReport.toCharacterReportEntity() = CharacterReportEntity(
    id = id,
    characterId = character.id,
    character = character,
    preset = preset,
    score = score,
    relicReports = relicReports,
    attrComparisonReports = attrComparisonReports,
    validAffixCounts = validAffixCounts,
    generationTime = generationTime,
)
