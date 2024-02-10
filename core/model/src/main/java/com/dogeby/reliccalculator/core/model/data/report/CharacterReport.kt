package com.dogeby.reliccalculator.core.model.data.report

import com.dogeby.reliccalculator.core.model.data.hoyo.Character
import com.dogeby.reliccalculator.core.model.data.preset.Preset

data class CharacterReport(
    val character: Character,
    val preset: Preset,
    val score: Float,
    val relicReports: List<RelicReport>,
    val attrComparisonReports: List<AttrComparisonReport>,
)
