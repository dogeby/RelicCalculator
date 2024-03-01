package com.dogeby.reliccalculator.core.data.fake

import com.dogeby.reliccalculator.core.data.repository.PreferencesRepository
import com.dogeby.reliccalculator.core.model.GameTextLanguage
import com.dogeby.reliccalculator.core.model.preferences.AppPreferencesData
import com.dogeby.reliccalculator.core.model.preferences.CharacterSortField
import com.dogeby.reliccalculator.core.model.preferences.PresetListPreferencesData
import com.dogeby.reliccalculator.core.model.preferences.UpdateChecksData
import com.dogeby.reliccalculator.core.model.preferences.samplePresetListPreferencesData
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.jetbrains.annotations.TestOnly

@TestOnly
class FakePreferencesRepository : PreferencesRepository {

    private val updateChecksDataFlow: MutableSharedFlow<UpdateChecksData> =
        MutableSharedFlow<UpdateChecksData>(
            replay = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST,
        ).apply {
            tryEmit(
                UpdateChecksData(
                    defaultPresetLastCheckDate = Clock.System.now(),
                    defaultPresetCheckIntervalSecond = 86_400,
                ),
            )
        }

    private val appPreferencesDataFlow: MutableSharedFlow<AppPreferencesData> =
        MutableSharedFlow<AppPreferencesData>(
            replay = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST,
        ).apply {
            tryEmit(AppPreferencesData(GameTextLanguage.EN))
        }

    private val presetListPreferencesDataFlow: MutableSharedFlow<PresetListPreferencesData> =
        MutableSharedFlow<PresetListPreferencesData>(
            replay = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST,
        ).apply {
            tryEmit(samplePresetListPreferencesData)
        }

    override fun getUpdateChecksData(): Flow<UpdateChecksData> = updateChecksDataFlow

    override fun getAppPreferencesData(): Flow<AppPreferencesData> = appPreferencesDataFlow

    override fun getGameTextLanguage(): Flow<GameTextLanguage> = appPreferencesDataFlow.map {
        it.gameTextLanguage
    }

    override fun getPresetListPreferencesData(): Flow<PresetListPreferencesData> =
        presetListPreferencesDataFlow

    override suspend fun setDefaultPresetLastCheckDate(instant: Instant): Result<Unit> =
        runCatching {
            updateChecksDataFlow.run {
                tryEmit(first().copy(defaultPresetLastCheckDate = instant))
            }
        }

    override suspend fun setDefaultPresetCheckIntervalSecond(second: Int): Result<Unit> =
        runCatching {
            updateChecksDataFlow.run {
                tryEmit(first().copy(defaultPresetCheckIntervalSecond = second))
            }
        }

    override suspend fun setGameTextLanguage(lang: GameTextLanguage): Result<Unit> = runCatching {
        appPreferencesDataFlow.run {
            tryEmit(first().copy(gameTextLanguage = lang))
        }
    }

    override suspend fun setPresetListFilteredRarities(rarities: Set<Int>): Result<Unit> =
        runCatching {
            presetListPreferencesDataFlow.run {
                tryEmit(first().copy(filteredRarities = rarities))
            }
        }

    override suspend fun setPresetListFilteredPathIds(ids: Set<String>): Result<Unit> =
        runCatching {
            presetListPreferencesDataFlow.run {
                tryEmit(first().copy(filteredPathIds = ids))
            }
        }

    override suspend fun setPresetListFilteredElementIds(ids: Set<String>): Result<Unit> =
        runCatching {
            presetListPreferencesDataFlow.run {
                tryEmit(first().copy(filteredElementIds = ids))
            }
        }

    override suspend fun setPresetListSortField(
        characterSortField: CharacterSortField,
    ): Result<Unit> = runCatching {
        presetListPreferencesDataFlow.run {
            tryEmit(first().copy(sortField = characterSortField))
        }
    }

    override suspend fun setPresetListFilteredData(
        filteredRarities: Set<Int>,
        filteredPathIds: Set<String>,
        filteredElementIds: Set<String>,
    ): Result<Unit> = runCatching {
        presetListPreferencesDataFlow.run {
            tryEmit(
                first().copy(
                    filteredRarities = filteredRarities,
                    filteredPathIds = filteredPathIds,
                    filteredElementIds = filteredElementIds,
                ),
            )
        }
    }

    override suspend fun setPresetListPreferencesData(
        presetListPreferencesData: PresetListPreferencesData,
    ): Result<Unit> = runCatching {
        presetListPreferencesDataFlow.tryEmit(presetListPreferencesData)
    }

    override suspend fun clearFilteredData(): Result<Unit> = runCatching {
        presetListPreferencesDataFlow.tryEmit(
            PresetListPreferencesData(
                filteredRarities = emptySet(),
                filteredPathIds = emptySet(),
                filteredElementIds = emptySet(),
                sortField = CharacterSortField.ID_ASC,
            ),
        )
    }
}
