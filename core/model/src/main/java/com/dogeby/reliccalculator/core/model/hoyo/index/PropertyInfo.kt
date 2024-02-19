package com.dogeby.reliccalculator.core.model.hoyo.index

import kotlinx.serialization.Serializable
import org.jetbrains.annotations.TestOnly

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

@TestOnly
val samplePropertyInfo = PropertyInfo(
    type = "SpeedDelta",
    name = "속도",
    field = "spd",
    affix = true,
    ratio = false,
    percent = false,
    order = 100,
    icon = "icon/property/IconSpeed.png",
)
