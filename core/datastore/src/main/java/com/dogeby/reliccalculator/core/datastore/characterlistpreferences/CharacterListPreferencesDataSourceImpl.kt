package com.dogeby.reliccalculator.core.datastore.characterlistpreferences

import androidx.datastore.core.DataStore
import com.dogeby.core.datastore.CharacterListPreferences
import com.dogeby.core.datastore.copy
import com.dogeby.reliccalculator.core.model.preferences.CharacterListPreferencesData
import com.dogeby.reliccalculator.core.model.preferences.CharacterSortField
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CharacterListPreferencesDataSourceImpl @Inject constructor(
    private val characterListPreferencesDataStore: DataStore<CharacterListPreferences>,
) : CharacterListPreferencesDataSource {

    override val characterListPreferencesData: Flow<CharacterListPreferencesData> =
        characterListPreferencesDataStore.data
            .map {
                CharacterListPreferencesData(
                    filteredRarities = it.filteredRaritiesList.toSet(),
                    filteredPathIds = it.filteredPathIdsList.toSet(),
                    filteredElementIds = it.filteredElementIdsList.toSet(),
                    sortField = CharacterSortField.valueOf(it.sortField),
                )
            }

    override suspend fun setFilteredRarities(rarities: Set<Int>): Result<Unit> = runCatching {
        characterListPreferencesDataStore.updateData {
            it.copy {
                filteredRarities.clear()
                filteredRarities.addAll(rarities)
            }
        }
    }

    override suspend fun setFilteredPathIds(ids: Set<String>): Result<Unit> = runCatching {
        characterListPreferencesDataStore.updateData {
            it.copy {
                filteredPathIds.clear()
                filteredPathIds.addAll(ids)
            }
        }
    }

    override suspend fun setFilteredElementIds(ids: Set<String>): Result<Unit> = runCatching {
        characterListPreferencesDataStore.updateData {
            it.copy {
                filteredElementIds.clear()
                filteredElementIds.addAll(ids)
            }
        }
    }

    override suspend fun setSortField(characterSortField: CharacterSortField): Result<Unit> =
        runCatching {
            characterListPreferencesDataStore.updateData {
                it.copy {
                    sortField = characterSortField.name
                }
            }
        }

    override suspend fun setFilteredData(
        filteredRarities: Set<Int>,
        filteredPathIds: Set<String>,
        filteredElementIds: Set<String>,
    ): Result<Unit> = runCatching {
        characterListPreferencesDataStore.updateData {
            it.copy {
                this.filteredRarities.clear()
                this.filteredRarities.addAll(filteredRarities)
                this.filteredPathIds.clear()
                this.filteredPathIds.addAll(filteredPathIds)
                this.filteredElementIds.clear()
                this.filteredElementIds.addAll(filteredElementIds)
            }
        }
    }

    override suspend fun setCharacterListPreferencesData(
        characterListPreferencesData: CharacterListPreferencesData,
    ): Result<Unit> = runCatching {
        setFilteredData(
            filteredRarities = characterListPreferencesData.filteredRarities,
            filteredPathIds = characterListPreferencesData.filteredPathIds,
            filteredElementIds = characterListPreferencesData.filteredElementIds,
        )
        setSortField(characterListPreferencesData.sortField)
    }

    override suspend fun clearFilteredData(): Result<Unit> = runCatching {
        characterListPreferencesDataStore.updateData {
            it.copy {
                filteredRarities.clear()
                filteredPathIds.clear()
                filteredElementIds.clear()
            }
        }
    }

    override suspend fun clearData(): Result<Unit> = runCatching {
        characterListPreferencesDataStore.updateData {
            it.copy {
                filteredRarities.clear()
                filteredPathIds.clear()
                filteredElementIds.clear()
                sortField = CharacterSortField.ID_ASC.name
            }
        }
    }
}
