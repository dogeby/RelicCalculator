package com.dogeby.core.data.repository

import com.dogeby.reliccalculator.core.model.GameTextLanguage
import com.dogeby.reliccalculator.core.model.preferences.AppPreferencesData
import com.dogeby.reliccalculator.core.model.preferences.UpdateChecksData
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Instant

interface PreferencesRepository {

    fun getUpdateChecksData(): Flow<UpdateChecksData>

    fun getAppPreferencesData(): Flow<AppPreferencesData>

    fun getGameTextLanguage(): Flow<GameTextLanguage>

    suspend fun setDefaultPresetLastCheckDate(instant: Instant): Result<Unit>

    suspend fun setDefaultPresetCheckIntervalSecond(second: Int): Result<Unit>

    suspend fun setGameTextLanguage(lang: GameTextLanguage): Result<Unit>
}
