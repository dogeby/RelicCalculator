package com.dogeby.reliccalculator.core.model.data.report

import com.dogeby.reliccalculator.core.model.data.preset.ComparisonOperator
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AttrComparisonReport(
    val field: String,
    @SerialName("compared_value") val comparedValue: Float,
    @SerialName("comparison_operator") val comparisonOperator: ComparisonOperator,
    @SerialName("is_pass") val isPass: Boolean,
)
