package com.dogeby.reliccalculator.core.model.hoyo.index

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.jetbrains.annotations.TestOnly

@Serializable
data class RelicInfo(
    val id: String,
    @SerialName("set_id") val setId: String,
    val name: String,
    val rarity: Int,
    val type: RelicPiece,
    @SerialName("max_level") val maxLevel: Int,
    @SerialName("main_affix_id") val mainAffixId: String,
    @SerialName("sub_affix_id") val subAffixId: String,
    val icon: String,
)

@TestOnly
val sampleRelicInfo = RelicInfo(
    id = "61041",
    setId = "104",
    name = "Hunter's Artaius Hood",
    rarity = 5,
    type = RelicPiece.HEAD,
    maxLevel = 15,
    mainAffixId = "51",
    subAffixId = "5",
    icon = "icon/relic/104_0.png",
)
