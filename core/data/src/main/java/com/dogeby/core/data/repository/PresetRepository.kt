package com.dogeby.core.data.repository

import com.dogeby.reliccalculator.core.model.preset.Preset
import com.dogeby.reliccalculator.core.model.preset.PresetData
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Instant

interface PresetRepository {

    fun getPresets(): Flow<List<Preset>>

    fun getPresets(ids: Set<String>): Flow<List<Preset>>

    suspend fun getDefaultPresetsInStorage(): Result<List<Preset>>

    suspend fun getDefaultPresetsInStorage(ids: Set<String>): Result<List<Preset>>

    suspend fun insertPresets(presets: List<Preset>): Result<List<Long>>

    suspend fun updatePresets(presets: List<Preset>): Result<Int>

    suspend fun updatePresetsAutoUpdate(
        ids: Set<String>,
        isAutoUpdate: Boolean,
    ): Result<Int>

    suspend fun upsertPresets(presets: List<Preset>): Result<List<Long>>

    suspend fun deletePresets(presets: List<Preset>): Result<Int>

    suspend fun downloadDefaultPresetData(): Result<PresetData>

    suspend fun updateDefaultPresetDataInStorage(presetData: PresetData): Result<Instant>

    suspend fun updateDefaultPresetsInDb(presets: List<Preset>): Result<Int>
}
