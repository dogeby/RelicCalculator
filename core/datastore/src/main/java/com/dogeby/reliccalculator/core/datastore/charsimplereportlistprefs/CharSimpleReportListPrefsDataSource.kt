package com.dogeby.reliccalculator.core.datastore.charsimplereportlistprefs

import com.dogeby.reliccalculator.core.model.preferences.CharacterListPreferencesData
import com.dogeby.reliccalculator.core.model.preferences.CharacterSortField
import kotlinx.coroutines.flow.Flow

interface CharSimpleReportListPrefsDataSource {

    val charSimpleReportListPrefsData: Flow<CharacterListPreferencesData>

    suspend fun setFilteredRarities(rarities: Set<Int>): Result<Unit>

    suspend fun setFilteredPathIds(ids: Set<String>): Result<Unit>

    suspend fun setFilteredElementIds(ids: Set<String>): Result<Unit>

    suspend fun setSortField(characterSortField: CharacterSortField): Result<Unit>

    suspend fun setFilteredData(
        filteredRarities: Set<Int>,
        filteredPathIds: Set<String>,
        filteredElementIds: Set<String>,
    ): Result<Unit>

    suspend fun setCharSimpleReportListPrefsData(
        charSimpleReportListPrefsData: CharacterListPreferencesData,
    ): Result<Unit>

    suspend fun clearFilteredData(): Result<Unit>

    suspend fun clearData(): Result<Unit>
}
