package com.dogeby.core.data.repository

import com.dogeby.core.common.util.DateUtil.toDate
import com.dogeby.core.data.exception.NoUpdateNeededException
import com.dogeby.core.data.model.toPresetEntity
import com.dogeby.core.database.dao.PresetDao
import com.dogeby.core.database.model.preset.PresetEntity
import com.dogeby.core.database.model.preset.toPreset
import com.dogeby.core.storage.StorageManager
import com.dogeby.core.storage.di.StorageManagerModule
import com.dogeby.reliccalculator.core.model.data.preset.Preset
import com.dogeby.reliccalculator.core.model.data.preset.PresetData
import com.dogeby.reliccalculator.core.network.PresetNetworkDataSource
import java.io.FileNotFoundException
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Singleton
class PresetRepositoryImpl @Inject constructor(
    private val dataJson: Json,
    private val presetDao: PresetDao,
    private val presetNetworkDataSource: PresetNetworkDataSource,
    @StorageManagerModule.InternalStorage private val storageManager: StorageManager,
) : PresetRepository {

    private val defaultPresetLocale = Locale.KOREA

    override fun getPresets(): Flow<List<Preset>> =
        presetDao.getPresets().map { it.map(PresetEntity::toPreset) }

    override fun getPresets(ids: Set<String>): Flow<List<Preset>> =
        presetDao.getPresets(ids).map { it.map(PresetEntity::toPreset) }

    override suspend fun getDefaultPresetsInStorage(): Result<List<Preset>> {
        return storageManager.loadStringFromFile(
            fileName = DEFAULT_PRESET_FILE_NAME,
            path = PRESET_PATH,
        ).map {
            dataJson.decodeFromString<PresetData>(it).presets
        }
    }

    override suspend fun getDefaultPresetsInStorage(ids: Set<String>): Result<List<Preset>> {
        return getDefaultPresetsInStorage().map { presets ->
            presets.filter { it.characterId in ids }
        }
    }

    override suspend fun insertPresets(presets: List<Preset>): Result<List<Long>> = runCatching {
        presetDao.insertOrIgnorePresets(presets.map(Preset::toPresetEntity))
    }

    override suspend fun updatePresets(presets: List<Preset>): Result<Int> = runCatching {
        presetDao.updatePresets(presets.map(Preset::toPresetEntity))
    }

    override suspend fun upsertPresets(presets: List<Preset>): Result<List<Long>> = runCatching {
        presetDao.upsertPresets(presets.map(Preset::toPresetEntity))
    }

    override suspend fun deletePresets(presets: List<Preset>): Result<Int> = runCatching {
        presetDao.deletePresets(presets.map(Preset::toPresetEntity))
    }

    override suspend fun downloadDefaultPresetData(): Result<PresetData> = runCatching {
        return presetNetworkDataSource.getDefaultPreset()
    }

    override suspend fun updateDefaultPresetDataInStorage(presetData: PresetData): Result<Date> =
        runCatching {
            val inputDataUpdateDate = presetData.updateDate.toDate(
                DATE_FORMAT,
                defaultPresetLocale,
            ).getOrThrow()
            val saveInStorage: suspend PresetData.() -> Date = {
                storageManager.saveStringToFile(
                    data = dataJson.encodeToString(this),
                    fileName = DEFAULT_PRESET_FILE_NAME,
                    path = PRESET_PATH,
                ).onFailure {
                    throw it
                }
                inputDataUpdateDate
            }
            val presetDataInStorage = storageManager
                .loadStringFromFile(
                    fileName = DEFAULT_PRESET_FILE_NAME,
                    path = PRESET_PATH,
                )
                .getOrElse { throwable ->
                    if (throwable !is FileNotFoundException) {
                        throw throwable
                    }
                    return@runCatching presetData.saveInStorage()
                }
                .run {
                    dataJson.decodeFromString<PresetData>(this)
                }
            if (presetDataInStorage.isLatestVersion(
                    presetData,
                )
            ) {
                throw NoUpdateNeededException(inputDataUpdateDate)
            }

            presetData.saveInStorage()
        }

    override suspend fun updateDefaultPresetsInDb(presets: List<Preset>): Result<Int> =
        runCatching {
            val autoUpdatePresets = getPresets()
                .first()
                .associate {
                    Pair(it.characterId, it.isAutoUpdate)
                }
            val newPresets = presets.filter { preset ->
                autoUpdatePresets.getOrDefault(preset.characterId, true)
            }
            upsertPresets(newPresets).getOrThrow().size
        }

    private fun PresetData.isLatestVersion(comparedPresetData: PresetData): Boolean {
        return updateDate.toDate(
            DATE_FORMAT,
            defaultPresetLocale,
        ).getOrThrow() >=
            comparedPresetData.updateDate.toDate(
                DATE_FORMAT,
                defaultPresetLocale,
            ).getOrThrow()
    }

    companion object {

        private const val DATE_FORMAT = "yy-MM-dd HH:mm:ss"
        private const val DEFAULT_PRESET_FILE_NAME = "star_rail_default_preset.json"
        private const val PRESET_PATH = "preset"
    }
}
