package com.dogeby.reliccalculator.core.model.report

import kotlinx.serialization.Serializable

@Serializable
data class AffixReport(
    val type: String,
    val score: Float,
)
