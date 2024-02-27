package com.dogeby.reliccalculator.core.model.preferences

import org.jetbrains.annotations.TestOnly

data class PresetListPreferencesData(
    val filteredRarities: Set<Int>,
    val filteredPathIds: Set<String>,
    val filteredElementIds: Set<String>,
    val sortField: CharacterSortField,
)

@TestOnly
val samplePresetListPreferencesData = PresetListPreferencesData(
    filteredRarities = setOf(5),
    filteredPathIds = emptySet(),
    filteredElementIds = emptySet(),
    sortField = CharacterSortField.ID_ASC,
)
