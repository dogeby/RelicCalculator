package com.dogeby.reliccalculator.core.model.data.preset

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AttrComparison(
    val field: String,
    val name: String,
    val icon: String,
    val percent: Boolean,
    @SerialName("compared_value") val comparedValue: Float,
    @SerialName("comparison_operator") val comparisonOperator: ComparisonOperator,
)
