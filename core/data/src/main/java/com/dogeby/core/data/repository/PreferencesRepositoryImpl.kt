package com.dogeby.core.data.repository

import com.dogeby.core.datastore.updatechecks.UpdateChecksDataSource
import com.dogeby.reliccalculator.core.model.preferences.UpdateChecksData
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Instant

@Singleton
class PreferencesRepositoryImpl @Inject constructor(
    private val updateChecksDataSource: UpdateChecksDataSource,
) : PreferencesRepository {

    override fun getUpdateChecksData(): Flow<UpdateChecksData> {
        return updateChecksDataSource.updateChecksData
    }

    override suspend fun setDefaultPresetLastCheckDate(instant: Instant): Result<Unit> {
        return updateChecksDataSource.setDefaultPresetLastCheckDate(instant)
    }

    override suspend fun setDefaultPresetCheckIntervalSecond(second: Int): Result<Unit> {
        return updateChecksDataSource.setDefaultPresetCheckIntervalSecond(second)
    }
}
