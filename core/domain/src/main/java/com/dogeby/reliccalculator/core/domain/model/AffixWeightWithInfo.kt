package com.dogeby.reliccalculator.core.domain.model

import com.dogeby.reliccalculator.core.model.mihomo.index.PropertyInfo
import com.dogeby.reliccalculator.core.model.preset.AffixWeight

data class AffixWeightWithInfo(
    val affixWeight: AffixWeight,
    val propertyInfo: PropertyInfo,
)

internal fun List<AffixWeight>.mapNotNullToAffixWeightsWithInfo(
    propertyInfoMap: Map<String, PropertyInfo>,
): List<AffixWeightWithInfo> {
    return mapNotNull { affixWeight ->
        propertyInfoMap[affixWeight.type]?.let {
            AffixWeightWithInfo(
                affixWeight = affixWeight,
                propertyInfo = it,
            )
        }
    }
}
