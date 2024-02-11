package com.dogeby.reliccalculator.core.model.report

import kotlinx.serialization.Serializable

@Serializable
data class AffixCount(
    val type: String,
    val count: Int,
)
