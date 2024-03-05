package com.dogeby.reliccalculator.core.domain.model

import com.dogeby.reliccalculator.core.model.mihomo.index.AffixInfo
import com.dogeby.reliccalculator.core.model.mihomo.index.PropertyInfo
import com.dogeby.reliccalculator.core.model.preset.AffixWeight

data class AffixWithDetails(
    val id: String,
    val propertyInfo: PropertyInfo,
)

fun AffixInfo.toAffixWithDetails(propertyInfo: PropertyInfo) = AffixWithDetails(
    id = affixId,
    propertyInfo = propertyInfo,
)

fun AffixWithDetails.toAffixWeightWithInfo() = AffixWeightWithInfo(
    affixWeight = AffixWeight(
        affixId = id,
        type = propertyInfo.type,
        weight = 0f,
    ),
    propertyInfo = propertyInfo,
)
