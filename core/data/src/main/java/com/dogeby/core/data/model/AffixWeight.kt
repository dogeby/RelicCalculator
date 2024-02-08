package com.dogeby.core.data.model

import com.dogeby.core.database.model.preset.DatabaseAffixWeight
import com.dogeby.reliccalculator.core.model.data.preset.AffixWeight

fun AffixWeight.toDatabaseAffixWeight() = DatabaseAffixWeight(
    type = type,
    weight = weight,
)
