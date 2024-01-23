package com.dogeby.core.database.model.report

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DatabaseAffixReport(
    @SerialName("affix_type") val type: String,
    @SerialName("affix_score") val score: Float,
)
