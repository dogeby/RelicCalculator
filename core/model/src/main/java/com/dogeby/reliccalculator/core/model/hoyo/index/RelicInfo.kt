package com.dogeby.reliccalculator.core.model.hoyo.index

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RelicInfo(
    val id: String,
    @SerialName("set_id") val setId: String,
    val name: String,
    val rarity: Int,
    val type: String,
    @SerialName("max_level") val maxLevel: Int,
    @SerialName("main_affix_id") val mainAffixId: String,
    @SerialName("sub_affix_id") val subAffixId: String,
    val icon: String,
)
