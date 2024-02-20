package com.dogeby.reliccalculator.core.model.preset

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.jetbrains.annotations.TestOnly

@Serializable
data class AttrComparison(
    val type: String,
    val field: String,
    @SerialName("compared_value") val comparedValue: Float,
    val display: String,
    val percent: Boolean,
    @SerialName("comparison_operator") val comparisonOperator: ComparisonOperator,
)

@TestOnly
val sampleAttrComparison = AttrComparison(
    type = "AttackDelta",
    field = "atk",
    comparedValue = 500.0f,
    display = "500",
    percent = false,
    comparisonOperator = ComparisonOperator.GREATER_THAN,
)
