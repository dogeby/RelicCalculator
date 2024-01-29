package com.dogeby.reliccalculator.core.model.data.preset

data class Preset(
    val characterId: String,
    val relicSetIds: List<String>,
    val relicStatWeights: List<RelicStatWeight>,
)