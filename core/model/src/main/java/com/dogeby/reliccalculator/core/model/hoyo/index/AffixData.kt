package com.dogeby.reliccalculator.core.model.hoyo.index

import kotlinx.serialization.Serializable

@Serializable
data class AffixData(
    val id: String,
    val affixes: Map<String, AffixInfo>,
)
