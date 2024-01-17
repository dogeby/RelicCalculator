package com.dogeby.core.database.model.hoyo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DatabaseRelic(
    @SerialName("relic_id") val id: String,
    @SerialName("relic_name") val name: String,
    @SerialName("relic_set_id") val setId: String,
    @SerialName("relic_set_name") val setName: String,
    @SerialName("relic_rarity") val rarity: Int,
    @SerialName("relic_level") val level: Int,
    @SerialName("relic_icon") val icon: String,
    @SerialName("relic_main_affix") val mainAffix: DatabaseMainAffix,
    @SerialName("relic_sub_affix") val subAffix: List<DatabaseSubAffix>,
)
