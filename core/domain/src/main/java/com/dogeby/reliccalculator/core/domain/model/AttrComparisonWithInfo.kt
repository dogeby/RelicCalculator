package com.dogeby.reliccalculator.core.domain.model

import com.dogeby.reliccalculator.core.model.mihomo.index.PropertyInfo
import com.dogeby.reliccalculator.core.model.preset.AttrComparison

data class AttrComparisonWithInfo(
    val attrComparison: AttrComparison,
    val propertyInfo: PropertyInfo,
)
