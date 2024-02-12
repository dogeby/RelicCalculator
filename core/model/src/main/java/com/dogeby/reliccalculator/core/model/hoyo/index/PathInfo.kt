package com.dogeby.reliccalculator.core.model.hoyo.index

import kotlinx.serialization.Serializable

@Serializable
data class PathInfo(
    val id: String,
    val name: String,
    val icon: String,
)
