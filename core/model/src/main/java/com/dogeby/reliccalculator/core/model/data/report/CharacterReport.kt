package com.dogeby.reliccalculator.core.model.data.report

import com.dogeby.reliccalculator.core.model.data.hoyo.Character

data class CharacterReport(
    val character: Character,
    val score: Float,
    val relicReports: List<RelicReport>,
)
