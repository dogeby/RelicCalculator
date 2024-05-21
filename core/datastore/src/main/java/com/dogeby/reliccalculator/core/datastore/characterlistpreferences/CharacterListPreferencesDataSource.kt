package com.dogeby.reliccalculator.core.datastore.characterlistpreferences

import com.dogeby.reliccalculator.core.model.preferences.CharacterListPreferencesData
import com.dogeby.reliccalculator.core.model.preferences.CharacterSortField
import kotlinx.coroutines.flow.Flow

interface CharacterListPreferencesDataSource {

    val characterListPreferencesData: Flow<CharacterListPreferencesData>

    suspend fun setFilteredRarities(rarities: Set<Int>): Result<Unit>

    suspend fun setFilteredPathIds(ids: Set<String>): Result<Unit>

    suspend fun setFilteredElementIds(ids: Set<String>): Result<Unit>

    suspend fun setSortField(characterSortField: CharacterSortField): Result<Unit>

    suspend fun setFilteredData(
        filteredRarities: Set<Int>,
        filteredPathIds: Set<String>,
        filteredElementIds: Set<String>,
    ): Result<Unit>

    suspend fun setCharacterListPreferencesData(
        characterListPreferencesData: CharacterListPreferencesData,
    ): Result<Unit>

    suspend fun clearFilteredData(): Result<Unit>

    suspend fun clearData(): Result<Unit>
}
