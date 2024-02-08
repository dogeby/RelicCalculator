package com.dogeby.core.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.dogeby.core.database.dao.CharacterDao
import com.dogeby.core.database.model.hoyo.sampleCharacterEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CharacterDaoTest {

    private lateinit var characterDao: CharacterDao
    private lateinit var db: RelicCalculatorDatabase

    private val character = sampleCharacterEntity

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            RelicCalculatorDatabase::class.java,
        ).build()
        characterDao = db.characterDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun test_characterDao_insert_success() = runTest {
        val size = 3
        val result = characterDao.insertOrIgnoreCharacters(
            List(size) { character.copy(id = "test$it") },
        )

        Assert.assertEquals(List(size) { it + 1L }, result)
    }

    @Test
    fun test_characterDao_update_success() = runTest {
        characterDao.insertOrIgnoreCharacters(listOf(character.copy(name = "oldName")))
        val result = characterDao.updateCharacters(listOf(character.copy(name = "newName")))

        Assert.assertEquals(1, result)
    }

    @Test
    fun test_characterDao_upsert_success() = runTest {
        val size = 3
        val characters = List(size) { character.copy(id = "test$it") }
        val insertResult = characterDao.upsertCharacters(characters)

        Assert.assertEquals(List(size) { it + 1L }, insertResult)

        val newCharacters = characters.map { characterEntity ->
            characterEntity.copy(name = "newName")
        }
        val updateResult = characterDao.updateCharacters(newCharacters)
        val updatedCharacters = characterDao.getCharacters().first()

        Assert.assertEquals(size, updateResult)
        Assert.assertEquals(newCharacters, updatedCharacters)
    }

    @Test
    fun test_characterDao_delete_success() = runTest {
        characterDao.insertOrIgnoreCharacters(listOf(character))
        val result = characterDao.deleteCharacters(listOf(character))

        Assert.assertEquals(1, result)
    }

    @Test
    fun test_characterDao_getAllCharacters_success() = runTest {
        val characters = List(3) { character.copy(id = "test$it") }
        characterDao.insertOrIgnoreCharacters(characters)
        val result = characterDao.getCharacters().first()

        Assert.assertEquals(characters, result)
    }

    @Test
    fun test_characterDao_getCharacters_success() = runTest {
        val ids = mutableListOf<String>()
        val characters = List(3) { index ->
            val id = "test$index".also { ids.add(it) }
            character.copy(id = id)
        }
        characterDao.insertOrIgnoreCharacters(characters)
        val result = characterDao.getCharacters(ids.toSet()).first()

        Assert.assertEquals(characters, result)
    }
}
