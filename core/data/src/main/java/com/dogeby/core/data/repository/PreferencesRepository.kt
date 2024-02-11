package com.dogeby.core.data.repository

import com.dogeby.reliccalculator.core.model.preferences.UpdateChecksData
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Instant

interface PreferencesRepository {

    fun getUpdateChecksData(): Flow<UpdateChecksData>

    suspend fun setDefaultPresetLastCheckDate(instant: Instant): Result<Unit>

    suspend fun setDefaultPresetCheckIntervalSecond(second: Int): Result<Unit>
}
