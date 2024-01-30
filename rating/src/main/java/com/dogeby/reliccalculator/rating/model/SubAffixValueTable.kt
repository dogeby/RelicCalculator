package com.dogeby.reliccalculator.rating.model

import kotlinx.serialization.Serializable

@Serializable
data class SubAffixValueTable(
    val affixes: Map<String, Map<String, Double>>,
)
