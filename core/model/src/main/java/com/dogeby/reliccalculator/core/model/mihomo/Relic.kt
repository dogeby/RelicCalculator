package com.dogeby.reliccalculator.core.model.mihomo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Relic(
    val id: String,
    @SerialName("set_id") val setId: String,
    val rarity: Int,
    val level: Int,
    @SerialName("main_affix") val mainAffix: MainAffix,
    @SerialName("sub_affix") val subAffix: List<SubAffix>,
)
