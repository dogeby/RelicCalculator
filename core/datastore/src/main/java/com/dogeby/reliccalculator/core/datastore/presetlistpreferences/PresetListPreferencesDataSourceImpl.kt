package com.dogeby.reliccalculator.core.datastore.presetlistpreferences

import com.dogeby.reliccalculator.core.datastore.characterlistpreferences.CharacterListPreferencesDataSource
import com.dogeby.reliccalculator.core.datastore.di.DataStoreModule
import com.dogeby.reliccalculator.core.model.preferences.CharacterListPreferencesData
import com.dogeby.reliccalculator.core.model.preferences.CharacterSortField
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow

@Singleton
class PresetListPreferencesDataSourceImpl @Inject constructor(
    @DataStoreModule.PresetListCharacterListPreferences
    private val characterListPreferencesDataSource: CharacterListPreferencesDataSource,
) : PresetListPreferencesDataSource {

    override val presetListPreferencesData: Flow<CharacterListPreferencesData> =
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

    override suspend fun setPresetListPreferencesData(
        presetListPreferencesData: CharacterListPreferencesData,
    ): Result<Unit> = characterListPreferencesDataSource.setCharacterListPreferencesData(
        characterListPreferencesData = presetListPreferencesData,
    )

    override suspend fun clearFilteredData(): Result<Unit> =
        characterListPreferencesDataSource.clearFilteredData()

    override suspend fun clearData(): Result<Unit> = characterListPreferencesDataSource.clearData()
}
