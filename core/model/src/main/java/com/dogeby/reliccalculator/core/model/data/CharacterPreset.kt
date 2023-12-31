package com.dogeby.reliccalculator.core.model.data

data class CharacterPreset(
    val id: String,
    val characterId: String,
    val relicSetIds: List<String>,
    val relicWeights: List<RelicWeight>,
)
