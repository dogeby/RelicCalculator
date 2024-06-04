package com.dogeby.reliccalculator.core.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.dogeby.reliccalculator.core.database.dao.CharacterDao
import com.dogeby.reliccalculator.core.database.model.hoyo.sampleCharacterEntity
import com.dogeby.reliccalculator.core.model.mihomo.Attribute
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
        characterDao.insertOrIgnoreCharacters(listOf(character))
        val result = characterDao.updateCharacters(
            listOf(
                character.copy(
                    attributes = listOf(
                        Attribute(
                            field = "",
                            value = 1000.0,
                            display = "1000",
                            percent = false,
                        ),
                    ),
                ),
            ),
        )

        Assert.assertEquals(1, result)
    }

    @Test
    fun test_characterDao_upsert_success() = runTest {
        val initialCharactersSize = 3
        val initialCharacters = List(initialCharactersSize) { character.copy(id = "test$it") }
        val insertRowIds = characterDao.insertOrIgnoreCharacters(initialCharacters)

        Assert.assertEquals(List(initialCharactersSize) { it + 1L }, insertRowIds)

        val updatedCharacter = initialCharacters.last().copy(
            attributes = listOf(
                Attribute(
                    field = "",
                    value = 1000.0,
                    display = "1000",
                    percent = false,
                ),
            ),
        )
        val newCharacter = character.copy(id = "newCharacterId")
        val charactersToUpsert = initialCharacters.dropLast(1) +
            updatedCharacter + newCharacter
        characterDao.upsertCharacters(charactersToUpsert)
        val upsertedCharacters = characterDao.getCharacters().first()

        Assert.assertEquals(charactersToUpsert, upsertedCharacters)
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
