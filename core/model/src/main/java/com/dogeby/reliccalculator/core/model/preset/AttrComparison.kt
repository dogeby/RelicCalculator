package com.dogeby.reliccalculator.core.model.preset

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AttrComparison(
    val type: String,
    val field: String,
    @SerialName("compared_value") val comparedValue: Float,
    val display: String,
    val percent: Boolean,
    @SerialName("comparison_operator") val comparisonOperator: ComparisonOperator,
)
