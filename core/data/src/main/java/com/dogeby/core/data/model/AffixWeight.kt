package com.dogeby.core.data.model

import com.dogeby.core.database.model.preset.DatabaseAffixWeight
import com.dogeby.reliccalculator.core.model.data.preset.AffixWeight
import com.dogeby.reliccalculator.core.network.model.preset.NetworkAffixWeight

fun AffixWeight.toDatabaseAffixWeight() = DatabaseAffixWeight(
    type = type,
    weight = weight,
)

fun AffixWeight.toNetworkAffixWeight() = NetworkAffixWeight(
    type = type,
    weight = weight,
)
