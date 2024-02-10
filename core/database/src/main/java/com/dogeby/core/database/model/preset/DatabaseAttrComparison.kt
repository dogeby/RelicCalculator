package com.dogeby.core.database.model.preset

import com.dogeby.reliccalculator.core.model.data.preset.AttrComparison
import com.dogeby.reliccalculator.core.model.data.preset.ComparisonOperator
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DatabaseAttrComparison(
    @SerialName("attr_comparison_field") val field: String,
    @SerialName("attr_comparison_name") val name: String,
    @SerialName("attr_comparison_icon") val icon: String,
    @SerialName("attr_comparison_percent") val percent: Boolean,
    @SerialName("attr_compared_value") val comparedValue: Float,
    @SerialName("attr_comparison_operator") val comparisonOperator: ComparisonOperator,
)

fun DatabaseAttrComparison.toAttrComparison() = AttrComparison(
    field = field,
    name = name,
    icon = icon,
    percent = percent,
    comparedValue = comparedValue,
    comparisonOperator = comparisonOperator,
)
