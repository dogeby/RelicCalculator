package com.dogeby.reliccalculator.core.model.report

import com.dogeby.reliccalculator.core.model.hoyo.Character
import com.dogeby.reliccalculator.core.model.preset.Preset

data class CharacterReport(
    val character: Character,
    val preset: Preset,
    val score: Float,
    val relicReports: List<RelicReport>,
    val attrComparisonReports: List<AttrComparisonReport>,
)
