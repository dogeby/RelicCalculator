package com.dogeby.reliccalculator.core.model.data

data class Relic(
    val id: String,
    val name: String,
    val setId: String,
    val setName: String,
    val rarity: Int,
    val level: Int,
    val icon: String,
    val mainAffix: MainAffix,
    val subAffix: SubAffix,
)
