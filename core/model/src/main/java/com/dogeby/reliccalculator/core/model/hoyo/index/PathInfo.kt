package com.dogeby.reliccalculator.core.model.hoyo.index

import kotlinx.serialization.Serializable
import org.jetbrains.annotations.TestOnly

@Serializable
data class PathInfo(
    val id: String,
    val name: String,
    val icon: String,
)

@TestOnly
val samplePathInfo = PathInfo(
    id = "Warrior",
    name = "파멸",
    icon = "icon/path/Destruction.png",
)
