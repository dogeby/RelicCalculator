package com.dogeby.core.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.dogeby.core.database.dao.CharacterDao
import com.dogeby.core.database.dao.CharacterReportDao
import com.dogeby.core.database.model.hoyo.sampleCharacterEntity
import com.dogeby.core.database.model.report.sampleCharacterReportEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CharacterReportDaoTest {

    private lateinit var characterReportDao: CharacterReportDao
    private lateinit var characterDao: CharacterDao
    private lateinit var db: RelicCalculatorDatabase

    private val characterReport = sampleCharacterReportEntity
    private val character = sampleCharacterEntity

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            RelicCalculatorDatabase::class.java,
        ).build()
        characterReportDao = db.characterReportDao()
        characterDao = db.characterDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun test_characterReportDao_insert_succeed() = runTest {
        val size = 3
        val result = characterReportDao.insertOrIgnoreCharacterReports(
            List(size) { characterReport },
        )

        Assert.assertEquals(List(size) { it + 1L }, result)
    }

    @Test
    fun test_characterReportDao_update_succeed() = runTest {
        val id = characterReportDao.insertOrIgnoreCharacterReports(
            listOf(characterReport.copy(characterId = "oldId")),
        )
            .first().toInt()
        val result = characterReportDao.updateCharacterReports(
            listOf(characterReport.copy(id = id, characterId = "newId")),
        )

        Assert.assertEquals(1, result)
    }

    @Test
    fun test_characterReportDao_upsert_succeed() = runTest {
        val size = 3
        val characterReports = List(size) { characterReport }
        val insertResult = characterReportDao.upsertCharacterReports(characterReports)

        Assert.assertEquals(List(size) { it + 1L }, insertResult)

        val newCharacterReports = characterReports.mapIndexed { index, characterEntity ->
            characterEntity.copy(id = insertResult[index].toInt(), characterId = "newId")
        }
        val updateResult = characterReportDao.updateCharacterReports(newCharacterReports)
        val updatedCharacterReports = characterReportDao.getCharacterReports().first()

        Assert.assertEquals(size, updateResult)
        Assert.assertEquals(newCharacterReports, updatedCharacterReports)
    }

    @Test
    fun test_characterReportDao_delete_succeed() = runTest {
        val id = characterReportDao.insertOrIgnoreCharacterReports(
            listOf(characterReport),
        ).first().toInt()
        val result = characterReportDao.deleteCharacterReports(
            listOf(characterReport.copy(id = id)),
        )

        Assert.assertEquals(1, result)
    }

    @Test
    fun test_characterReportDao_getAllCharacterReports_succeed() = runTest {
        val characterReports =
            List(3) { characterReport }.run {
                val ids = characterReportDao.insertOrIgnoreCharacterReports(this)
                mapIndexed { index, characterReportEntity ->
                    characterReportEntity.copy(id = ids[index].toInt())
                }
            }
        val result = characterReportDao.getCharacterReports().first()

        Assert.assertEquals(characterReports, result)
    }

    @Test
    fun test_characterReportDao_getCharacterReports_succeed() = runTest {
        val characterReports =
            List(3) { characterReport }.run {
                val ids = characterReportDao.insertOrIgnoreCharacterReports(this)
                mapIndexed { index, characterReportEntity ->
                    characterReportEntity.copy(id = ids[index].toInt())
                }
            }
        val ids = characterReports.map { it.id }
        characterReportDao.insertOrIgnoreCharacterReports(characterReports)
        val result = characterReportDao.getCharacterReports(ids.toSet()).first()

        Assert.assertEquals(characterReports, result)
    }

    @Test
    fun test_characterReportDao_getCharacterWithReports_succeed() = runTest {
        characterDao.insertOrIgnoreCharacters(listOf(character))
        val characterReports = List(3) {
            characterReport.copy(characterId = character.id)
        }.run {
            val ids = characterReportDao.insertOrIgnoreCharacterReports(this)
            mapIndexed { index, characterReportEntity ->
                characterReportEntity.copy(id = ids[index].toInt())
            }
        }

        val result = characterReportDao.getCharactersWithReports().first().first()

        Assert.assertEquals(character, result.characterEntity)
        Assert.assertEquals(characterReports, result.reports)
    }

    @Test
    fun test_characterReportDao_getCharacterWithReportsById_succeed() = runTest {
        val characters = List(3) {
            character.copy(id = "$it")
        }
        characterDao.insertOrIgnoreCharacters(characters)

        val characterReports = List(3) {
            characterReport.copy(characterId = characters.first().id)
        }.run {
            val ids = characterReportDao.insertOrIgnoreCharacterReports(this)
            mapIndexed { index, characterReportEntity ->
                characterReportEntity.copy(id = ids[index].toInt())
            }
        }
        characters.drop(1).forEach { character ->
            characterReportDao.insertOrIgnoreCharacterReports(
                List(3) {
                    characterReport.copy(characterId = character.id)
                },
            )
        }

        val result = characterReportDao.getCharactersWithReports(
            setOf(characters.first().id),
        ).first().first()

        Assert.assertEquals(characters.first(), result.characterEntity)
        Assert.assertEquals(characterReports, result.reports)
    }
}
