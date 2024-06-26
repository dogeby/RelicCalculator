package com.dogeby.reliccalculator.core.data.repository

import com.dogeby.reliccalculator.core.datastore.charsimplereportlistprefs.CharSimpleReportListPrefsDataSource
import com.dogeby.reliccalculator.core.datastore.gamepreferences.GamePreferencesDataSource
import com.dogeby.reliccalculator.core.datastore.presetlistpreferences.PresetListPreferencesDataSource
import com.dogeby.reliccalculator.core.datastore.updatechecks.UpdateChecksDataSource
import com.dogeby.reliccalculator.core.model.GameTextLanguage
import com.dogeby.reliccalculator.core.model.preferences.CharacterListPreferencesData
import com.dogeby.reliccalculator.core.model.preferences.CharacterSortField
import com.dogeby.reliccalculator.core.model.preferences.GamePreferencesData
import com.dogeby.reliccalculator.core.model.preferences.UpdateChecksData
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Instant

@Singleton
class PreferencesRepositoryImpl @Inject constructor(
    private val updateChecksDataSource: UpdateChecksDataSource,
    private val gamePreferencesDataSource: GamePreferencesDataSource,
    private val presetListPreferencesDataSource: PresetListPreferencesDataSource,
    private val charSimpleReportListPrefsDataSource: CharSimpleReportListPrefsDataSource,
) : PreferencesRepository {

    override fun getUpdateChecksData(): Flow<UpdateChecksData> =
        updateChecksDataSource.updateChecksData

    override fun getGamePreferencesData(): Flow<GamePreferencesData> =
        gamePreferencesDataSource.gamePreferencesData

    override fun getGameTextLanguage(): Flow<GameTextLanguage> =
        gamePreferencesDataSource.gamePreferencesData.map { it.gameTextLanguage }

    override fun getPresetListPreferencesData(): Flow<CharacterListPreferencesData> =
        presetListPreferencesDataSource.presetListPreferencesData

    override fun getCharSimpleReportListPrefsData(): Flow<CharacterListPreferencesData> =
        charSimpleReportListPrefsDataSource.charSimpleReportListPrefsData

    override suspend fun setDefaultPresetLastCheckDate(instant: Instant): Result<Unit> =
        updateChecksDataSource.setDefaultPresetLastCheckDate(instant)

    override suspend fun setDefaultPresetCheckIntervalSecond(second: Int): Result<Unit> =
        updateChecksDataSource.setDefaultPresetCheckIntervalSecond(second)

    override suspend fun setGameTextLanguage(lang: GameTextLanguage): Result<Unit> =
        gamePreferencesDataSource.setGameTextLanguage(lang)

    override suspend fun setUid(uid: String): Result<Unit> = gamePreferencesDataSource.setUid(uid)

    override suspend fun setPresetListFilteredRarities(rarities: Set<Int>): Result<Unit> =
        presetListPreferencesDataSource.setFilteredRarities(rarities)

    override suspend fun setPresetListFilteredPathIds(ids: Set<String>): Result<Unit> =
        presetListPreferencesDataSource.setFilteredPathIds(ids)

    override suspend fun setPresetListFilteredElementIds(ids: Set<String>): Result<Unit> =
        presetListPreferencesDataSource.setFilteredElementIds(ids)

    override suspend fun setPresetListSortField(
        characterSortField: CharacterSortField,
    ): Result<Unit> = presetListPreferencesDataSource.setSortField(characterSortField)

    override suspend fun setPresetListFilteredData(
        filteredRarities: Set<Int>,
        filteredPathIds: Set<String>,
        filteredElementIds: Set<String>,
    ): Result<Unit> = presetListPreferencesDataSource.setFilteredData(
        filteredRarities = filteredRarities,
        filteredPathIds = filteredPathIds,
        filteredElementIds = filteredElementIds,
    )

    override suspend fun setPresetListPreferencesData(
        presetListPreferencesData: CharacterListPreferencesData,
    ): Result<Unit> = presetListPreferencesDataSource.setPresetListPreferencesData(
        presetListPreferencesData = presetListPreferencesData,
    )

    override suspend fun clearPresetListFilteredData(): Result<Unit> =
        presetListPreferencesDataSource.clearFilteredData()

    override suspend fun setCharSimpleReportListFilteredRarities(rarities: Set<Int>): Result<Unit> =
        charSimpleReportListPrefsDataSource.setFilteredRarities(rarities)

    override suspend fun setCharSimpleReportListFilteredPathIds(ids: Set<String>): Result<Unit> =
        charSimpleReportListPrefsDataSource.setFilteredPathIds(ids)

    override suspend fun setCharSimpleReportListFilteredElementIds(ids: Set<String>): Result<Unit> =
        charSimpleReportListPrefsDataSource.setFilteredElementIds(ids)

    override suspend fun setCharSimpleReportListSortField(
        characterSortField: CharacterSortField,
    ): Result<Unit> = charSimpleReportListPrefsDataSource.setSortField(characterSortField)

    override suspend fun setCharSimpleReportListFilteredData(
        filteredRarities: Set<Int>,
        filteredPathIds: Set<String>,
        filteredElementIds: Set<String>,
    ): Result<Unit> = charSimpleReportListPrefsDataSource.setFilteredData(
        filteredRarities = filteredRarities,
        filteredPathIds = filteredPathIds,
        filteredElementIds = filteredElementIds,
    )

    override suspend fun setCharSimpleReportListPrefsData(
        charSimpleReportListPrefsData: CharacterListPreferencesData,
    ): Result<Unit> = charSimpleReportListPrefsDataSource.setCharSimpleReportListPrefsData(
        charSimpleReportListPrefsData = charSimpleReportListPrefsData,
    )

    override suspend fun clearCharSimpleReportListFilteredData(): Result<Unit> =
        charSimpleReportListPrefsDataSource.clearFilteredData()
}
