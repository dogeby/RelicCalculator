package com.dogeby.core.datastore.presetlistpreferences

import androidx.datastore.core.DataStore
import com.dogeby.core.datastore.PresetListPreferences
import com.dogeby.core.datastore.copy
import com.dogeby.reliccalculator.core.model.preferences.CharacterSortField
import com.dogeby.reliccalculator.core.model.preferences.PresetListPreferencesData
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Singleton
class PresetListPreferencesDataSourceImpl @Inject constructor(
    private val presetListDataStore: DataStore<PresetListPreferences>,
) : PresetListPreferencesDataSource {

    override val presetListPreferencesData: Flow<PresetListPreferencesData> =
        presetListDataStore.data
            .map {
                PresetListPreferencesData(
                    filteredRarities = it.filteredRaritiesList.toSet(),
                    filteredPathIds = it.filteredPathIdsList.toSet(),
                    filteredElementIds = it.filteredElementIdsList.toSet(),
                    sortField = CharacterSortField.valueOf(it.sortField),
                )
            }

    override suspend fun setFilteredRarities(rarities: Set<Int>): Result<Unit> = runCatching {
        presetListDataStore.updateData {
            it.copy {
                filteredRarities.clear()
                filteredRarities.addAll(rarities)
            }
        }
    }

    override suspend fun setFilteredPathIds(ids: Set<String>): Result<Unit> = runCatching {
        presetListDataStore.updateData {
            it.copy {
                filteredPathIds.clear()
                filteredPathIds.addAll(ids)
            }
        }
    }

    override suspend fun setFilteredElementIds(ids: Set<String>): Result<Unit> = runCatching {
        presetListDataStore.updateData {
            it.copy {
                filteredElementIds.clear()
                filteredElementIds.addAll(ids)
            }
        }
    }

    override suspend fun setSortField(characterSortField: CharacterSortField): Result<Unit> =
        runCatching {
            presetListDataStore.updateData {
                it.copy {
                    sortField = characterSortField.name
                }
            }
        }

    override suspend fun setFilteredData(
        presetListPreferencesData: PresetListPreferencesData,
    ): Result<Unit> = runCatching {
        presetListDataStore.updateData {
            it.copy {
                filteredRarities.clear()
                filteredRarities.addAll(presetListPreferencesData.filteredRarities)
                filteredPathIds.clear()
                filteredPathIds.addAll(presetListPreferencesData.filteredPathIds)
                filteredElementIds.clear()
                filteredElementIds.addAll(presetListPreferencesData.filteredElementIds)
            }
        }
    }

    override suspend fun clearData(): Result<Unit> = runCatching {
        presetListDataStore.updateData {
            it.copy {
                filteredRarities.clear()
                filteredPathIds.clear()
                filteredElementIds.clear()
                sortField = CharacterSortField.LATEST_RELEASED.name
            }
        }
    }
}
