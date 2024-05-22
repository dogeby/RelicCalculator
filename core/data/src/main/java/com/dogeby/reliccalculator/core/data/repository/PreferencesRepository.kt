package com.dogeby.reliccalculator.core.data.repository

import com.dogeby.reliccalculator.core.model.GameTextLanguage
import com.dogeby.reliccalculator.core.model.preferences.AppPreferencesData
import com.dogeby.reliccalculator.core.model.preferences.CharacterListPreferencesData
import com.dogeby.reliccalculator.core.model.preferences.CharacterSortField
import com.dogeby.reliccalculator.core.model.preferences.UpdateChecksData
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Instant

interface PreferencesRepository {

    fun getUpdateChecksData(): Flow<UpdateChecksData>

    fun getAppPreferencesData(): Flow<AppPreferencesData>

    fun getGameTextLanguage(): Flow<GameTextLanguage>

    fun getPresetListPreferencesData(): Flow<CharacterListPreferencesData>

    fun getCharSimpleReportListPrefsData(): Flow<CharacterListPreferencesData>

    suspend fun setDefaultPresetLastCheckDate(instant: Instant): Result<Unit>

    suspend fun setDefaultPresetCheckIntervalSecond(second: Int): Result<Unit>

    suspend fun setGameTextLanguage(lang: GameTextLanguage): Result<Unit>

    suspend fun setPresetListFilteredRarities(rarities: Set<Int>): Result<Unit>

    suspend fun setPresetListFilteredPathIds(ids: Set<String>): Result<Unit>

    suspend fun setPresetListFilteredElementIds(ids: Set<String>): Result<Unit>

    suspend fun setPresetListSortField(characterSortField: CharacterSortField): Result<Unit>

    suspend fun setPresetListFilteredData(
        filteredRarities: Set<Int>,
        filteredPathIds: Set<String>,
        filteredElementIds: Set<String>,
    ): Result<Unit>

    suspend fun setPresetListPreferencesData(
        presetListPreferencesData: CharacterListPreferencesData,
    ): Result<Unit>

    suspend fun clearPresetListFilteredData(): Result<Unit>

    suspend fun setCharSimpleReportListFilteredRarities(rarities: Set<Int>): Result<Unit>

    suspend fun setCharSimpleReportListFilteredPathIds(ids: Set<String>): Result<Unit>

    suspend fun setCharSimpleReportListFilteredElementIds(ids: Set<String>): Result<Unit>

    suspend fun setCharSimpleReportListSortField(
        characterSortField: CharacterSortField,
    ): Result<Unit>

    suspend fun setCharSimpleReportListFilteredData(
        filteredRarities: Set<Int>,
        filteredPathIds: Set<String>,
        filteredElementIds: Set<String>,
    ): Result<Unit>

    suspend fun setCharSimpleReportListPrefsData(
        charSimpleReportListPrefsData: CharacterListPreferencesData,
    ): Result<Unit>

    suspend fun clearCharSimpleReportListFilteredData(): Result<Unit>
}
