package com.dogeby.core.datastore.updatechecks

import androidx.datastore.core.DataStore
import com.dogeby.core.datastore.UpdateChecks
import com.dogeby.core.datastore.copy
import com.dogeby.reliccalculator.core.model.preferences.UpdateChecksData
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Instant
import kotlinx.datetime.toInstant

@Singleton
class UpdateChecksDataSourceImpl @Inject constructor(
    private val updateChecksDataStore: DataStore<UpdateChecks>,
) : UpdateChecksDataSource {

    override val updateChecksData: Flow<UpdateChecksData> =
        updateChecksDataStore.data
            .map {
                UpdateChecksData(
                    defaultPresetLastCheckDate = it.defaultPresetLastCheckDate.toInstant(),
                    defaultPresetCheckIntervalSecond = it.defaultPresetCheckIntervalSecond,
                )
            }

    override suspend fun setDefaultPresetLastCheckDate(instant: Instant): Result<Unit> =
        runCatching {
            updateChecksDataStore.updateData {
                it.copy {
                    this.defaultPresetLastCheckDate = instant.toString()
                }
            }
        }

    override suspend fun setDefaultPresetCheckIntervalSecond(second: Int): Result<Unit> =
        runCatching {
            updateChecksDataStore.updateData {
                it.copy {
                    this.defaultPresetCheckIntervalSecond = second
                }
            }
        }
}
