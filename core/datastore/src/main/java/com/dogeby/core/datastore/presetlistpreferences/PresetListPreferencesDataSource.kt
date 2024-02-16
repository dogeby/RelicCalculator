package com.dogeby.core.datastore.presetlistpreferences

import com.dogeby.reliccalculator.core.model.preferences.CharacterSortField
import com.dogeby.reliccalculator.core.model.preferences.PresetListPreferencesData
import kotlinx.coroutines.flow.Flow

interface PresetListPreferencesDataSource {

    val presetListPreferencesData: Flow<PresetListPreferencesData>

    suspend fun setFilteredRarities(rarities: Set<Int>): Result<Unit>

    suspend fun setFilteredPathIds(ids: Set<String>): Result<Unit>

    suspend fun setFilteredElementIds(ids: Set<String>): Result<Unit>

    suspend fun setSortField(characterSortField: CharacterSortField): Result<Unit>

    suspend fun setFilteredData(presetListPreferencesData: PresetListPreferencesData): Result<Unit>

    suspend fun clearFilteredData(): Result<Unit>

    suspend fun clearData(): Result<Unit>
}
