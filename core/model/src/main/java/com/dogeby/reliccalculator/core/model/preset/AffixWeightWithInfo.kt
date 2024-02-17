package com.dogeby.reliccalculator.core.model.preset

import com.dogeby.reliccalculator.core.model.hoyo.index.AffixInfo

data class AffixWeightWithInfo(
    val affixWeight: AffixWeight,
    val affixInfo: AffixInfo,
)
