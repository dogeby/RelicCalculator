package com.dogeby.reliccalculator.core.domain.model

import com.dogeby.reliccalculator.core.model.mihomo.index.PropertyInfo
import com.dogeby.reliccalculator.core.model.preset.AffixWeight

data class AffixWeightWithInfo(
    val affixWeight: AffixWeight,
    val propertyInfo: PropertyInfo,
)
