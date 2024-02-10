package com.dogeby.reliccalculator.core.model.report

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RelicReport(
    val id: String,
    val score: Float,
    @SerialName("main_affix_report") val mainAffixReport: AffixReport,
    @SerialName("sub_affix_report") val subAffixReports: List<AffixReport>,
)
