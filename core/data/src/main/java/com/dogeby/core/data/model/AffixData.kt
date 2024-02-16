package com.dogeby.core.data.model

import com.dogeby.core.database.model.hoyo.index.AffixDataEntity
import com.dogeby.reliccalculator.core.model.hoyo.index.AffixData

fun AffixData.toAffixDataEntity() = AffixDataEntity(
    id = id,
    affixes = affixes,
)
