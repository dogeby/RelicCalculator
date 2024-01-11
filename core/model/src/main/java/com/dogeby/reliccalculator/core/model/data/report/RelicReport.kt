package com.dogeby.reliccalculator.core.model.data.report

data class RelicReport(
    val id: String,
    val score: Float,
    val mainAffixReport: AffixReport,
    val subAffixReports: List<AffixReport>,
)
