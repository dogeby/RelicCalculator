package com.dogeby.core.data.repository

import com.dogeby.core.common.di.CommonModule
import com.dogeby.core.data.exception.NoUpdateNeededException
import com.dogeby.core.data.fake.FakePresetDao
import com.dogeby.core.data.fake.FakeStorageManager
import com.dogeby.core.data.model.toPresetEntity
import com.dogeby.core.database.dao.PresetDao
import com.dogeby.reliccalculator.core.model.data.preset.Preset
import com.dogeby.reliccalculator.core.model.data.preset.PresetData
import com.dogeby.reliccalculator.core.network.PresetNetworkDataSource
import com.dogeby.reliccalculator.core.network.fake.FakePresetNetworkDataSource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.encodeToString
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class PresetRepositoryTest {

    private lateinit var presetRepository: PresetRepository
    private lateinit var fakePresetNetworkDataSource: PresetNetworkDataSource
    private lateinit var fakeStorageManager: FakeStorageManager
    private lateinit var fakePresetDao: PresetDao

    private val testDispatcher = StandardTestDispatcher()
    private val dataJson = CommonModule.providesJson()
    private val defaultPresetFileName = "star_rail_default_preset.json"
    private val defaultPresetPath = "preset"

    @Before
    fun setUp() {
        fakePresetNetworkDataSource = FakePresetNetworkDataSource(
            ioDispatcher = testDispatcher,
            networkJson = dataJson,
        )
        fakeStorageManager = FakeStorageManager()
        fakePresetDao = FakePresetDao()
        presetRepository = PresetRepositoryImpl(
            dataJson = dataJson,
            presetDao = fakePresetDao,
            presetNetworkDataSource = fakePresetNetworkDataSource,
            storageManager = fakeStorageManager,
        )
    }

    @Test
    fun test_getDefaultPresetsInStorage_success() = runTest(testDispatcher) {
        val defaultPresetDataFromNetwork = fakePresetNetworkDataSource
            .getDefaultPreset()
            .getOrThrow()
        fakeStorageManager.saveStringToFile(
            data = dataJson.encodeToString(defaultPresetDataFromNetwork),
            fileName = defaultPresetFileName,
            path = defaultPresetPath,
        )
        val presetsInStorage = presetRepository.getDefaultPresetsInStorage().getOrThrow()

        Assert.assertEquals(
            defaultPresetDataFromNetwork.presets,
            presetsInStorage,
        )
    }

    @Test
    fun test_downloadDefaultPresetData_success() = runTest(testDispatcher) {
        val defaultPresetDataFromNetwork = fakePresetNetworkDataSource
            .getDefaultPreset()
            .getOrThrow()
        val downloadedDefaultPresetData = presetRepository.downloadDefaultPresetData().getOrThrow()

        Assert.assertEquals(
            defaultPresetDataFromNetwork,
            downloadedDefaultPresetData,
        )
    }

    @Test
    fun test_updateDefaultPresetDataInStorage_success() = runTest(testDispatcher) {
        val defaultPresetDataFromNetwork = fakePresetNetworkDataSource
            .getDefaultPreset()
            .getOrThrow()
        presetRepository.updateDefaultPresetDataInStorage(defaultPresetDataFromNetwork)
        val defaultPresetDataInStorage = fakeStorageManager
            .loadStringFromFile(
                fileName = defaultPresetFileName,
                path = defaultPresetPath,
            )
            .run {
                dataJson.decodeFromString<PresetData>(this.getOrThrow())
            }

        Assert.assertEquals(
            defaultPresetDataFromNetwork,
            defaultPresetDataInStorage,
        )
    }

    @Test
    fun test_updateDefaultPresetDataInStorage_noUpdateNeeded() = runTest(testDispatcher) {
        val defaultPresetDataFromNetwork = fakePresetNetworkDataSource
            .getDefaultPreset()
            .getOrThrow()
        presetRepository.updateDefaultPresetDataInStorage(defaultPresetDataFromNetwork)
        val updateDefaultPresetResult = presetRepository.updateDefaultPresetDataInStorage(
            defaultPresetDataFromNetwork,
        )

        Assert.assertThrows(NoUpdateNeededException::class.java) {
            updateDefaultPresetResult.getOrThrow()
        }
    }

    @Test
    fun test_updateDefaultPresetsInDb_emptyTable() = runTest(testDispatcher) {
        val downloadedDefaultPresets = presetRepository
            .downloadDefaultPresetData()
            .getOrThrow()
            .presets
        val updatedPresetsCount = presetRepository
            .updateDefaultPresetsInDb(downloadedDefaultPresets)
            .getOrThrow()

        Assert.assertEquals(
            downloadedDefaultPresets.count(),
            updatedPresetsCount,
        )
    }

    @Test
    fun test_updateDefaultPresetsInDb_autoUpdateIsFalse() = runTest(testDispatcher) {
        val downloadedDefaultPresets = presetRepository
            .downloadDefaultPresetData()
            .getOrThrow()
            .presets
        val downloadedDefaultPresetEntities = downloadedDefaultPresets
            .map(Preset::toPresetEntity)
            .map {
                it.copy(isAutoUpdate = false)
            }
        val insertRowIds = fakePresetDao.insertOrIgnorePresets(downloadedDefaultPresetEntities)

        Assert.assertEquals(
            downloadedDefaultPresets.size,
            insertRowIds.count { it != -1L },
        )
        Assert.assertEquals(
            0,
            presetRepository.updateDefaultPresetsInDb(downloadedDefaultPresets).getOrThrow(),
        )
    }

    @Test
    fun test_updateDefaultPresetsInDb_upsertPresets() = runTest(testDispatcher) {
        val downloadedDefaultPresets = presetRepository
            .downloadDefaultPresetData()
            .getOrThrow()
            .presets
        val initialAutoUpdateFalsePresets = downloadedDefaultPresets
            .dropLast(10)
            .map {
                it.copy(isAutoUpdate = false)
            }
        val initialAutoUpdateTruePresets = downloadedDefaultPresets
            .takeLast(10)
            .dropLast(5)

        fakePresetDao.insertOrIgnorePresets(
            initialAutoUpdateFalsePresets.map(Preset::toPresetEntity),
        )
        fakePresetDao.insertOrIgnorePresets(
            initialAutoUpdateTruePresets.map(Preset::toPresetEntity),
        )
        val updateDefaultPresetsCount = presetRepository
            .updateDefaultPresetsInDb(
                downloadedDefaultPresets.map {
                    it.copy(relicSetIds = emptyList())
                },
            )
            .getOrThrow()

        Assert.assertEquals(
            10,
            updateDefaultPresetsCount,
        )
        Assert.assertEquals(
            10,
            fakePresetDao.getPresets().first().count { it.relicSetIds.isEmpty() },
        )
    }
}
