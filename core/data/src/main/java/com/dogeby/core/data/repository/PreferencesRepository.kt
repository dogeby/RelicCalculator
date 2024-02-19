package com.dogeby.core.data.repository

import com.dogeby.reliccalculator.core.model.GameTextLanguage
import com.dogeby.reliccalculator.core.model.preferences.AppPreferencesData
import com.dogeby.reliccalculator.core.model.preferences.CharacterSortField
import com.dogeby.reliccalculator.core.model.preferences.PresetListPreferencesData
import com.dogeby.reliccalculator.core.model.preferences.UpdateChecksData
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Instant

interface PreferencesRepository {

    fun getUpdateChecksData(): Flow<UpdateChecksData>

    fun getAppPreferencesData(): Flow<AppPreferencesData>

    fun getGameTextLanguage(): Flow<GameTextLanguage>

    fun getPresetListPreferencesData(): Flow<PresetListPreferencesData>

    suspend fun setDefaultPresetLastCheckDate(instant: Instant): Result<Unit>

    suspend fun setDefaultPresetCheckIntervalSecond(second: Int): Result<Unit>

    suspend fun setGameTextLanguage(lang: GameTextLanguage): Result<Unit>

    suspend fun setFilteredRarities(rarities: Set<Int>): Result<Unit>

    suspend fun setFilteredPathIds(ids: Set<String>): Result<Unit>

    suspend fun setFilteredElementIds(ids: Set<String>): Result<Unit>

    suspend fun setSortField(characterSortField: CharacterSortField): Result<Unit>

    suspend fun setFilteredData(
        filteredRarities: Set<Int>,
        filteredPathIds: Set<String>,
        filteredElementIds: Set<String>,
    ): Result<Unit>

    suspend fun setPresetListPreferencesData(
        presetListPreferencesData: PresetListPreferencesData,
    ): Result<Unit>

    suspend fun clearFilteredData(): Result<Unit>
}
