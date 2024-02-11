package com.dogeby.reliccalculator.core.model.hoyo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Relic(
    val id: String,
    val name: String,
    @SerialName("set_id") val setId: String,
    @SerialName("set_name") val setName: String,
    val rarity: Int,
    val level: Int,
    val icon: String,
    @SerialName("main_affix") val mainAffix: MainAffix,
    @SerialName("sub_affix") val subAffix: List<SubAffix>,
)
