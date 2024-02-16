package com.dogeby.core.data.repository

import com.dogeby.core.datastore.apppreferences.AppPreferencesDataSource
import com.dogeby.core.datastore.presetlistpreferences.PresetListPreferencesDataSource
import com.dogeby.core.datastore.updatechecks.UpdateChecksDataSource
import com.dogeby.reliccalculator.core.model.GameTextLanguage
import com.dogeby.reliccalculator.core.model.preferences.AppPreferencesData
import com.dogeby.reliccalculator.core.model.preferences.CharacterSortField
import com.dogeby.reliccalculator.core.model.preferences.PresetListPreferencesData
import com.dogeby.reliccalculator.core.model.preferences.UpdateChecksData
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Instant

@Singleton
class PreferencesRepositoryImpl @Inject constructor(
    private val updateChecksDataSource: UpdateChecksDataSource,
    private val appPreferencesDataSource: AppPreferencesDataSource,
    private val presetListPreferencesDataSource: PresetListPreferencesDataSource,
) : PreferencesRepository {

    override fun getUpdateChecksData(): Flow<UpdateChecksData> =
        updateChecksDataSource.updateChecksData

    override fun getAppPreferencesData(): Flow<AppPreferencesData> =
        appPreferencesDataSource.appPreferencesData

    override fun getGameTextLanguage(): Flow<GameTextLanguage> =
        appPreferencesDataSource.appPreferencesData.map { it.gameTextLanguage }

    override fun getPresetListPreferencesData(): Flow<PresetListPreferencesData> =
        presetListPreferencesDataSource.presetListPreferencesData

    override suspend fun setDefaultPresetLastCheckDate(instant: Instant): Result<Unit> =
        updateChecksDataSource.setDefaultPresetLastCheckDate(instant)

    override suspend fun setDefaultPresetCheckIntervalSecond(second: Int): Result<Unit> =
        updateChecksDataSource.setDefaultPresetCheckIntervalSecond(second)

    override suspend fun setGameTextLanguage(lang: GameTextLanguage): Result<Unit> =
        appPreferencesDataSource.setGameTextLanguage(lang)

    override suspend fun setFilteredRarities(rarities: Set<Int>): Result<Unit> =
        presetListPreferencesDataSource.setFilteredRarities(rarities)

    override suspend fun setFilteredPathIds(ids: Set<String>): Result<Unit> =
        presetListPreferencesDataSource.setFilteredPathIds(ids)

    override suspend fun setFilteredElementIds(ids: Set<String>): Result<Unit> =
        presetListPreferencesDataSource.setFilteredElementIds(ids)

    override suspend fun setSortField(characterSortField: CharacterSortField): Result<Unit> =
        presetListPreferencesDataSource.setSortField(characterSortField)

    override suspend fun setFilteredData(
        presetListPreferencesData: PresetListPreferencesData,
    ): Result<Unit> = presetListPreferencesDataSource.setFilteredData(presetListPreferencesData)

    override suspend fun clearFilteredData(): Result<Unit> =
        presetListPreferencesDataSource.clearFilteredData()
}
