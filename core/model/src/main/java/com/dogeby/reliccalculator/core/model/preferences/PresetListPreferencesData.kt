package com.dogeby.reliccalculator.core.model.preferences

data class PresetListPreferencesData(
    val filteredRarities: Set<Int>,
    val filteredPathIds: Set<String>,
    val filteredElementIds: Set<String>,
    val sortField: CharacterSortField,
)
