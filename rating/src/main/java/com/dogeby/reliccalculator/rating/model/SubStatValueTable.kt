package com.dogeby.reliccalculator.rating.model

import kotlinx.serialization.Serializable

@Serializable
data class SubStatValueTable(
    val stats: Map<String, Map<String, Double>>,
)
