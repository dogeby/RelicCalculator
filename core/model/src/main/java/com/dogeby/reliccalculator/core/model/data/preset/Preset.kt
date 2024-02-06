package com.dogeby.reliccalculator.core.model.data.preset

data class Preset(
    val characterId: String,
    val relicSetIds: List<String>,
    val pieceMainAffixWeights: Map<Int, List<AffixWeight>>,
    val subAffixWeights: List<AffixWeight>,
    val isAutoUpdate: Boolean,
)
