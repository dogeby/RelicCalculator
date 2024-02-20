package com.dogeby.core.data.fake

import com.dogeby.core.data.repository.PresetRepository
import com.dogeby.reliccalculator.core.model.preset.Preset
import com.dogeby.reliccalculator.core.model.preset.PresetData
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Instant
import org.jetbrains.annotations.TestOnly

@TestOnly
class FakePresetRepository : PresetRepository {

    private val presetsFlow: MutableSharedFlow<List<Preset>> =
        MutableSharedFlow<List<Preset>>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
            .apply {
                tryEmit(emptyList())
            }

    private val defaultPresetDataInServerFlow: MutableSharedFlow<PresetData> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    private val defaultPresetDataInStorageFlow: MutableSharedFlow<PresetData> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    override fun getPresets(): Flow<List<Preset>> = presetsFlow

    override fun getPresets(ids: Set<String>): Flow<List<Preset>> = presetsFlow
        .map { presets ->
            presets.filter { it.characterId in ids }
        }

    override suspend fun getDefaultPresetsInStorage(): Result<List<Preset>> = runCatching {
        presetsFlow.first()
    }

    override suspend fun getDefaultPresetsInStorage(ids: Set<String>): Result<List<Preset>> =
        runCatching {
            getPresets(ids).first()
        }

    override suspend fun insertPresets(presets: List<Preset>): Result<List<Long>> = runCatching {
        val presetsInFlow = presetsFlow.first()
        val newPresets = presets.filter { preset ->
            presetsInFlow.any { it.characterId == preset.characterId }.not()
        }.distinctBy { it.characterId }

        presetsFlow.tryEmit(presetsInFlow + newPresets)
        (presetsInFlow.size + 1L..presetsInFlow.size + newPresets.size).toList()
    }

    override suspend fun updatePresets(presets: List<Preset>): Result<Int> = runCatching {
        val presetsInFlow = presetsFlow.first()
        val containedPresets = presets.filter { preset ->
            presetsInFlow.any { it.characterId == preset.characterId }.not()
        }.associateBy { it.characterId }

        var updatedCount = 0
        presetsInFlow.map { preset ->
            val containedPreset = containedPresets[preset.characterId]
            if (containedPreset != null && preset != containedPreset) {
                ++updatedCount
                return@map containedPreset
            }
            preset
        }
        updatedCount
    }

    override suspend fun upsertPresets(presets: List<Preset>): Result<List<Long>> {
        val updatedCount = updatePresets(presets)
        val insertRows = insertPresets(presets)
        return updatedCount.map {
            List(it) { -1L } + insertRows.getOrDefault(emptyList())
        }
    }

    override suspend fun deletePresets(presets: List<Preset>): Result<Int> = runCatching {
        val deletedPresetIds = presets.map { it.characterId }
        var count = 0
        presetsFlow.run {
            tryEmit(
                first().filter {
                    it.characterId in deletedPresetIds
                }.also {
                    count = it.size
                },
            )
        }
        count
    }

    override suspend fun downloadDefaultPresetData(): Result<PresetData> = runCatching {
        defaultPresetDataInServerFlow.first()
    }

    override suspend fun updateDefaultPresetDataInStorage(presetData: PresetData): Result<Instant> =
        runCatching {
            defaultPresetDataInStorageFlow.tryEmit(presetData)
            presetData.updateDate
        }

    override suspend fun updateDefaultPresetsInDb(presets: List<Preset>): Result<Int> {
        val presetIdsAutoUpdate = presetsFlow
            .first()
            .filter { it.isAutoUpdate }
            .map { it.characterId }
        val newPresets = presets.filter { it.characterId in presetIdsAutoUpdate }

        return upsertPresets(newPresets).map { it.size }
    }

    fun sendDefaultPresetDataInServer(presetData: PresetData) {
        defaultPresetDataInServerFlow.tryEmit(presetData)
    }
}
