package com.dogeby.reliccalculator.core.domain.model

import com.dogeby.reliccalculator.core.model.hoyo.index.AffixInfo
import com.dogeby.reliccalculator.core.model.preset.AffixWeight

data class AffixWeightWithInfo(
    val affixWeight: AffixWeight,
    val affixInfo: AffixInfo,
)
