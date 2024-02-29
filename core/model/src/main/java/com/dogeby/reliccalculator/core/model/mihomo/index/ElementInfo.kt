package com.dogeby.reliccalculator.core.model.mihomo.index

import kotlinx.serialization.Serializable
import org.jetbrains.annotations.TestOnly

@Serializable
data class ElementInfo(
    val id: String,
    val name: String,
    val color: String,
    val icon: String,
)

@TestOnly
val sampleElementInfo = ElementInfo(
    id = "Ice",
    name = "얼음",
    color = "#F84F36",
    icon = "icon/element/Ice.png",
)
