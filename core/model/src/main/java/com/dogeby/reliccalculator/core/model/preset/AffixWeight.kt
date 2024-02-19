package com.dogeby.reliccalculator.core.model.preset

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.jetbrains.annotations.TestOnly

@Serializable
data class AffixWeight(
    @SerialName("affix_id") val affixId: String,
    val type: String,
    val weight: Float,
)

@TestOnly
val sampleMainAffixWeight = AffixWeight(
    affixId = "1",
    type = "HPDelta",
    weight = 1.0f,
)

@TestOnly
val sampleSubAffixWeight = AffixWeight(
    affixId = "2",
    type = "AttackDelta",
    weight = 1.0f,
)
