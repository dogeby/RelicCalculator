package com.dogeby.core.database.model.report

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DatabaseRelicReport(
    @SerialName("relic_id") val id: String,
    @SerialName("relic_score") val score: Float,
    @SerialName("main_affix_report") val mainAffixReport: DatabaseAffixReport,
    @SerialName("sub_affix_report") val subAffixReports: List<DatabaseAffixReport>,
)
