package com.dogeby.reliccalculator.core.model.report

import com.dogeby.reliccalculator.core.model.mihomo.Character
import com.dogeby.reliccalculator.core.model.preset.Preset
import kotlinx.datetime.Instant

data class CharacterReport(
    val id: Int = 0,
    val character: Character,
    val preset: Preset,
    val score: Float,
    val relicReports: List<RelicReport>,
    val attrComparisonReports: List<AttrComparisonReport>,
    val validAffixCounts: List<AffixCount>,
    val generationTime: Instant,
)
