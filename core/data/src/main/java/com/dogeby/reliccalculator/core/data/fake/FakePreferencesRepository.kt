package com.dogeby.reliccalculator.core.data.fake

import com.dogeby.reliccalculator.core.data.repository.PreferencesRepository
import com.dogeby.reliccalculator.core.model.GameTextLanguage
import com.dogeby.reliccalculator.core.model.preferences.CharacterListPreferencesData
import com.dogeby.reliccalculator.core.model.preferences.CharacterSortField
import com.dogeby.reliccalculator.core.model.preferences.GamePreferencesData
import com.dogeby.reliccalculator.core.model.preferences.UpdateChecksData
import com.dogeby.reliccalculator.core.model.preferences.sampleCharacterListPreferencesData
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

    private val gamePreferencesDataFlow: MutableSharedFlow<GamePreferencesData> =
        MutableSharedFlow<GamePreferencesData>(
            replay = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST,
        ).apply {
            tryEmit(GamePreferencesData(GameTextLanguage.EN, ""))
        }

    private val presetListPreferencesDataFlow: MutableSharedFlow<CharacterListPreferencesData> =
        MutableSharedFlow<CharacterListPreferencesData>(
            replay = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST,
        ).apply {
            tryEmit(sampleCharacterListPreferencesData)
        }

    private val charSimpleReportListPrefsDataFlow: MutableSharedFlow<CharacterListPreferencesData> =
        MutableSharedFlow<CharacterListPreferencesData>(
            replay = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST,
        ).apply {
            tryEmit(sampleCharacterListPreferencesData)
        }

    override fun getUpdateChecksData(): Flow<UpdateChecksData> = updateChecksDataFlow

    override fun getGamePreferencesData(): Flow<GamePreferencesData> = gamePreferencesDataFlow

    override fun getGameTextLanguage(): Flow<GameTextLanguage> = gamePreferencesDataFlow.map {
        it.gameTextLanguage
    }

    override fun getPresetListPreferencesData(): Flow<CharacterListPreferencesData> =
        presetListPreferencesDataFlow

    override fun getCharSimpleReportListPrefsData(): Flow<CharacterListPreferencesData> =
        charSimpleReportListPrefsDataFlow

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
        gamePreferencesDataFlow.run {
            tryEmit(first().copy(gameTextLanguage = lang))
        }
    }

    override suspend fun setUid(uid: String): Result<Unit> = runCatching {
        gamePreferencesDataFlow.run {
            tryEmit(first().copy(uid = uid))
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
        presetListPreferencesData: CharacterListPreferencesData,
    ): Result<Unit> = runCatching {
        presetListPreferencesDataFlow.tryEmit(presetListPreferencesData)
    }

    override suspend fun clearPresetListFilteredData(): Result<Unit> = runCatching {
        presetListPreferencesDataFlow.tryEmit(
            CharacterListPreferencesData(
                filteredRarities = emptySet(),
                filteredPathIds = emptySet(),
                filteredElementIds = emptySet(),
                sortField = CharacterSortField.ID_ASC,
            ),
        )
    }

    override suspend fun setCharSimpleReportListFilteredRarities(rarities: Set<Int>): Result<Unit> =
        runCatching {
            charSimpleReportListPrefsDataFlow.run {
                tryEmit(first().copy(filteredRarities = rarities))
            }
        }

    override suspend fun setCharSimpleReportListFilteredPathIds(ids: Set<String>): Result<Unit> =
        runCatching {
            charSimpleReportListPrefsDataFlow.run {
                tryEmit(first().copy(filteredPathIds = ids))
            }
        }

    override suspend fun setCharSimpleReportListFilteredElementIds(ids: Set<String>): Result<Unit> =
        runCatching {
            charSimpleReportListPrefsDataFlow.run {
                tryEmit(first().copy(filteredElementIds = ids))
            }
        }

    override suspend fun setCharSimpleReportListSortField(
        characterSortField: CharacterSortField,
    ): Result<Unit> = runCatching {
        charSimpleReportListPrefsDataFlow.run {
            tryEmit(first().copy(sortField = characterSortField))
        }
    }

    override suspend fun setCharSimpleReportListFilteredData(
        filteredRarities: Set<Int>,
        filteredPathIds: Set<String>,
        filteredElementIds: Set<String>,
    ): Result<Unit> = runCatching {
        charSimpleReportListPrefsDataFlow.run {
            tryEmit(
                first().copy(
                    filteredRarities = filteredRarities,
                    filteredPathIds = filteredPathIds,
                    filteredElementIds = filteredElementIds,
                ),
            )
        }
    }

    override suspend fun setCharSimpleReportListPrefsData(
        charSimpleReportListPrefsData: CharacterListPreferencesData,
    ): Result<Unit> = runCatching {
        charSimpleReportListPrefsDataFlow.tryEmit(charSimpleReportListPrefsData)
    }

    override suspend fun clearCharSimpleReportListFilteredData(): Result<Unit> = runCatching {
        charSimpleReportListPrefsDataFlow.tryEmit(
            CharacterListPreferencesData(
                filteredRarities = emptySet(),
                filteredPathIds = emptySet(),
                filteredElementIds = emptySet(),
                sortField = CharacterSortField.ID_ASC,
            ),
        )
    }
}
