package com.dogeby.core.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.dogeby.core.database.dao.PresetDao
import com.dogeby.core.database.model.preset.samplePresetEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class PresetDaoTest {

    private lateinit var presetDao: PresetDao
    private lateinit var db: RelicCalculatorDatabase

    private val samplePreset = samplePresetEntity

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            RelicCalculatorDatabase::class.java,
        ).build()
        presetDao = db.presetDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun test_presetDao_insert_succeed() = runTest {
        val size = 3
        val result = presetDao.insertOrIgnorePresets(
            List(size) { samplePreset.copy(characterId = "test$it") },
        )

        Assert.assertEquals(List(size) { it + 1L }, result)
    }

    @Test
    fun test_presetDao_update_succeed() = runTest {
        presetDao.insertOrIgnorePresets(
            listOf(samplePreset),
        )
        val result = presetDao.updatePresets(
            listOf(samplePreset.copy(relicSetIds = emptyList())),
        )

        Assert.assertEquals(1, result)
    }

    @Test
    fun test_presetDao_upsert_succeed() = runTest {
        val size = 3
        val presets = List(size) { samplePreset.copy(characterId = "test$it") }
        val insertResult = presetDao.upsertPresets(presets)

        Assert.assertEquals(List(size) { it + 1L }, insertResult)

        val newPresets = presets.map { presetEntity ->
            presetEntity.copy(relicSetIds = emptyList())
        }
        val updateResult = presetDao.updatePresets(newPresets)
        val updatedPresets = presetDao.getPresets().first()

        Assert.assertEquals(size, updateResult)
        Assert.assertEquals(newPresets, updatedPresets)
    }

    @Test
    fun test_presetDao_delete_succeed() = runTest {
        presetDao.insertOrIgnorePresets(listOf(samplePreset))
        val result = presetDao.deletePresets(listOf(samplePreset))

        Assert.assertEquals(1, result)
    }

    @Test
    fun test_presetDao_getAllPresets_succeed() = runTest {
        val presets = List(3) { samplePreset.copy(characterId = "test$it") }
        presetDao.insertOrIgnorePresets(presets)
        val result = presetDao.getPresets().first()

        Assert.assertEquals(presets, result)
    }

    @Test
    fun test_presetDao_getPresets_succeed() = runTest {
        val ids = mutableListOf<String>()
        val presets = List(3) { index ->
            val id = "test$index".also { ids.add(it) }
            samplePreset.copy(characterId = id)
        }
        presetDao.insertOrIgnorePresets(presets)
        val result = presetDao.getPresets(ids.toSet()).first()

        Assert.assertEquals(presets, result)
    }
}
