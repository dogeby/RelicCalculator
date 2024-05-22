package com.dogeby.reliccalculator.core.datastore.charsimplereportlistprefs

import com.dogeby.reliccalculator.core.datastore.characterlistpreferences.CharacterListPreferencesDataSource
import com.dogeby.reliccalculator.core.datastore.di.DataStoreModule
import com.dogeby.reliccalculator.core.model.preferences.CharacterListPreferencesData
import com.dogeby.reliccalculator.core.model.preferences.CharacterSortField
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class CharSimpleReportListPrefsDataSourceImpl @Inject constructor(
    @DataStoreModule.CharSimpleReportListCharListPrefs
    private val characterListPreferencesDataSource: CharacterListPreferencesDataSource,
) : CharSimpleReportListPrefsDataSource {

    override val charSimpleReportListPrefsData: Flow<CharacterListPreferencesData> =
        characterListPreferencesDataSource.characterListPreferencesData

    override suspend fun setFilteredRarities(rarities: Set<Int>): Result<Unit> =
        characterListPreferencesDataSource.setFilteredRarities(rarities)

    override suspend fun setFilteredPathIds(ids: Set<String>): Result<Unit> =
        characterListPreferencesDataSource.setFilteredPathIds(ids)

    override suspend fun setFilteredElementIds(ids: Set<String>): Result<Unit> =
        characterListPreferencesDataSource.setFilteredElementIds(ids)

    override suspend fun setSortField(characterSortField: CharacterSortField): Result<Unit> =
        characterListPreferencesDataSource.setSortField(characterSortField)

    override suspend fun setFilteredData(
        filteredRarities: Set<Int>,
        filteredPathIds: Set<String>,
        filteredElementIds: Set<String>,
    ): Result<Unit> = characterListPreferencesDataSource.setFilteredData(
        filteredRarities = filteredRarities,
        filteredPathIds = filteredPathIds,
        filteredElementIds = filteredElementIds,
    )

    override suspend fun setCharSimpleReportListPrefsData(
        charSimpleReportListPrefsData: CharacterListPreferencesData,
    ): Result<Unit> = characterListPreferencesDataSource.setCharacterListPreferencesData(
        characterListPreferencesData = charSimpleReportListPrefsData,
    )

    override suspend fun clearFilteredData(): Result<Unit> =
        characterListPreferencesDataSource.clearFilteredData()

    override suspend fun clearData(): Result<Unit> = characterListPreferencesDataSource.clearData()
}
