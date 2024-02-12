package com.dogeby.reliccalculator.core.model.hoyo.index

import kotlinx.serialization.Serializable

@Serializable
data class RelicSetInfo(
    val id: String,
    val name: String,
    val desc: List<String>,
    val icon: String,
)
