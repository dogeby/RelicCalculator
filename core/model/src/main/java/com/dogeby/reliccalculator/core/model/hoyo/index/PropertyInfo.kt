package com.dogeby.reliccalculator.core.model.hoyo.index

import kotlinx.serialization.Serializable

@Serializable
data class PropertyInfo(
    val type: String,
    val name: String,
    val field: String,
    val affix: Boolean,
    val ratio: Boolean,
    val percent: Boolean,
    val order: Int,
    val icon: String,
)
