package com.dogeby.reliccalculator.core.model.data.hoyo

import kotlinx.serialization.Serializable

@Serializable
data class Path(
    val id: String,
    val name: String,
    val icon: String,
)
