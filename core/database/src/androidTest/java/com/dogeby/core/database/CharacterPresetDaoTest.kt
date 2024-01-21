package com.dogeby.core.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.dogeby.core.database.dao.CharacterPresetDao
import com.dogeby.core.database.model.preset.sampleCharacterPresetEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CharacterPresetDaoTest {

    private lateinit var characterPresetDao: CharacterPresetDao
    private lateinit var db: RelicCalculatorDatabase

    private val characterPreset = sampleCharacterPresetEntity

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            RelicCalculatorDatabase::class.java,
        ).build()
        characterPresetDao = db.CharacterPresetDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun test_characterPresetDao_insert_succeed() = runTest {
        val size = 3
        val result = characterPresetDao.insertOrIgnoreCharacterPresets(
            List(size) { characterPreset.copy(id = "test$it") },
        )

        Assert.assertEquals(List(size) { it + 1L }, result)
    }

    @Test
    fun test_characterPresetDao_update_succeed() = runTest {
        characterPresetDao.insertOrIgnoreCharacterPresets(
            listOf(characterPreset.copy(characterId = "oldId")),
        )
        val result = characterPresetDao.updateCharacterPresets(
            listOf(characterPreset.copy(characterId = "oldId")),
        )

        Assert.assertEquals(1, result)
    }

    @Test
    fun test_characterPresetDao_upsert_succeed() = runTest {
        val size = 3
        val characters = List(size) { characterPreset.copy(id = "test$it") }
        val insertResult = characterPresetDao.upsertCharacterPresets(characters)

        Assert.assertEquals(List(size) { it + 1L }, insertResult)

        val newCharacters = characters.map { characterEntity ->
            characterEntity.copy(characterId = "newId")
        }
        val updateResult = characterPresetDao.updateCharacterPresets(newCharacters)
        val updatedCharacters = characterPresetDao.getCharacterPresets().first()

        Assert.assertEquals(size, updateResult)
        Assert.assertEquals(newCharacters, updatedCharacters)
    }

    @Test
    fun test_characterPresetDao_delete_succeed() = runTest {
        characterPresetDao.insertOrIgnoreCharacterPresets(listOf(characterPreset))
        val result = characterPresetDao.deleteCharacterPresets(listOf(characterPreset))

        Assert.assertEquals(1, result)
    }

    @Test
    fun test_characterPresetDao_getAllCharacters_succeed() = runTest {
        val characters = List(3) { characterPreset.copy(id = "test$it") }
        characterPresetDao.insertOrIgnoreCharacterPresets(characters)
        val result = characterPresetDao.getCharacterPresets().first()

        Assert.assertEquals(characters, result)
    }

    @Test
    fun test_characterPresetDao_getCharacters_succeed() = runTest {
        val ids = mutableListOf<String>()
        val characters = List(3) { index ->
            val id = "test$index".also { ids.add(it) }
            characterPreset.copy(id = id)
        }
        characterPresetDao.insertOrIgnoreCharacterPresets(characters)
        val result = characterPresetDao.getCharacterPresets(ids.toSet()).first()

        Assert.assertEquals(characters, result)
    }
}
