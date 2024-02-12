package com.dogeby.reliccalculator.core.model.hoyo.index

import kotlinx.serialization.Serializable

@Serializable
data class ElementInfo(
    val id: String,
    val name: String,
    val color: String,
    val icon: String,
)
