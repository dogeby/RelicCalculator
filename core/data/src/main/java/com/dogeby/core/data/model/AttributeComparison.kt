package com.dogeby.core.data.model

import com.dogeby.core.database.model.preset.DatabaseAttrComparison
import com.dogeby.reliccalculator.core.model.data.preset.AttrComparison

fun AttrComparison.toDatabaseAttrComparison() = DatabaseAttrComparison(
    field = field,
    name = name,
    icon = icon,
    percent = percent,
    comparedValue = comparedValue,
    comparisonOperator = comparisonOperator,
)
