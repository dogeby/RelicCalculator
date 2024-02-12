package com.dogeby.reliccalculator.core.model.hoyo.index

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AffixInfo(
    @SerialName("affix_id") val affixId: String,
    val property: String,
    val base: Double,
    val step: Double,
    @SerialName("step_num") val stepNum: Int = 0,
)
