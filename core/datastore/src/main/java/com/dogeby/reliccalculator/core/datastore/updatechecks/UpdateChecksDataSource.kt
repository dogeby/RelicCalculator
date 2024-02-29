package com.dogeby.reliccalculator.core.datastore.updatechecks

import com.dogeby.reliccalculator.core.model.preferences.UpdateChecksData
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Instant

interface UpdateChecksDataSource {

    val updateChecksData: Flow<UpdateChecksData>

    suspend fun setDefaultPresetLastCheckDate(instant: Instant): Result<Unit>

    suspend fun setDefaultPresetCheckIntervalSecond(second: Int): Result<Unit>
}
