package com.dogeby.reliccalculator.core.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.dogeby.reliccalculator.core.database.dao.GameInfoDao
import com.dogeby.reliccalculator.core.database.model.hoyo.index.sampleCharacterInfoEntity
import com.dogeby.reliccalculator.core.database.model.hoyo.index.sampleElementInfoEntity
import com.dogeby.reliccalculator.core.database.model.hoyo.index.sampleLightConeInfoEntity
import com.dogeby.reliccalculator.core.database.model.hoyo.index.samplePathInfoEntity
import com.dogeby.reliccalculator.core.database.model.hoyo.index.samplePropertyInfoEntity
import com.dogeby.reliccalculator.core.database.model.hoyo.index.sampleRelicInfoEntity
import com.dogeby.reliccalculator.core.database.model.hoyo.index.sampleRelicSetInfoEntity
import com.dogeby.reliccalculator.core.database.model.hoyo.index.sampleSubAffixDataEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GameInfoDaoTest {

    private lateinit var gameInfoDao: GameInfoDao
    private lateinit var db: RelicCalculatorDatabase

    @Before
    fun createDb() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RelicCalculatorDatabase::class.java,
        ).build()
        gameInfoDao = db.gameInfoDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun test_insertOrIgnoreElementsInfo_success() = runTest {
        val size = 3
        val result = gameInfoDao.insertOrIgnoreElementsInfo(
            List(size) { sampleElementInfoEntity.copy(id = "test$it") }.toSet(),
        )

        Assert.assertEquals(List(size) { it + 1L }, result)
    }

    @Test
    fun test_updateElementsInfo_success() = runTest {
        gameInfoDao.insertOrIgnoreElementsInfo(
            listOf(sampleElementInfoEntity).toSet(),
        )
        val result = gameInfoDao.updateElementsInfo(
            listOf(sampleElementInfoEntity.copy(name = "newName")).toSet(),
        )

        Assert.assertEquals(1, result)
    }

    @Test
    fun test_upsertElementsInfo_success() = runTest {
        val initialElements = List(3) { sampleElementInfoEntity.copy(id = "test$it") }
        val insertRowIds = gameInfoDao.insertOrIgnoreElementsInfo(initialElements.toSet())

        Assert.assertEquals(List(3) { it + 1L }, insertRowIds)

        val updatedElement = initialElements.last().copy(name = "newName")
        val newElement = sampleElementInfoEntity.copy(id = "newId")
        val elementsToUpsert = initialElements.dropLast(1) +
            updatedElement + newElement
        gameInfoDao.upsertElementsInfo(elementsToUpsert.toSet())
        val upsertedElements = gameInfoDao.getElementsInfo().first()

        Assert.assertEquals(elementsToUpsert, upsertedElements)
    }

    @Test
    fun test_deleteElementsInfo_success() = runTest {
        gameInfoDao.insertOrIgnoreElementsInfo(
            listOf(sampleElementInfoEntity).toSet(),
        )
        val result = gameInfoDao.deleteElementsInfo(listOf(sampleElementInfoEntity).toSet())

        Assert.assertEquals(1, result)
    }

    @Test
    fun test_getElementsInfo_success() = runTest {
        val elementsInfo = List(3) { sampleElementInfoEntity.copy(id = "test$it") }
        gameInfoDao.insertOrIgnoreElementsInfo(elementsInfo.toSet())
        val result = gameInfoDao.getElementsInfo().first()

        Assert.assertEquals(elementsInfo, result)
    }

    @Test
    fun test_getElementsInfoByIds_success() = runTest {
        val elementsInfo = List(3) { sampleElementInfoEntity.copy(id = "test$it") }
        val takenElementsInfo = elementsInfo.take(2)
        gameInfoDao.insertOrIgnoreElementsInfo(elementsInfo.toSet())
        val result = gameInfoDao.getElementsInfo(
            takenElementsInfo.map { it.id }
                .toSet(),
        )
            .first()

        Assert.assertEquals(takenElementsInfo, result)
    }

    @Test
    fun test_insertOrIgnorePathsInfo_success() = runTest {
        val size = 3
        val result = gameInfoDao.insertOrIgnorePathsInfo(
            List(size) { samplePathInfoEntity.copy(id = "test$it") }.toSet(),
        )

        Assert.assertEquals(List(size) { it + 1L }, result)
    }

    @Test
    fun test_updatePathsInfo_success() = runTest {
        gameInfoDao.insertOrIgnorePathsInfo(
            listOf(samplePathInfoEntity).toSet(),
        )
        val result = gameInfoDao.updatePathsInfo(
            listOf(samplePathInfoEntity.copy(name = "newName")).toSet(),
        )

        Assert.assertEquals(1, result)
    }

    @Test
    fun test_upsertPathsInfo_success() = runTest {
        val initialPaths = List(3) { samplePathInfoEntity.copy(id = "test$it") }
        val insertRowIds = gameInfoDao.insertOrIgnorePathsInfo(initialPaths.toSet())

        Assert.assertEquals(List(3) { it + 1L }, insertRowIds)

        val updatedPath = initialPaths.last().copy(name = "newName")
        val newPath = samplePathInfoEntity.copy(id = "newId")
        val pathsToUpsert = initialPaths.dropLast(1) +
            updatedPath + newPath
        gameInfoDao.upsertPathsInfo(pathsToUpsert.toSet())
        val upsertedPaths = gameInfoDao.getPathsInfo().first()

        Assert.assertEquals(pathsToUpsert, upsertedPaths)
    }

    @Test
    fun test_deletePathsInfo_success() = runTest {
        gameInfoDao.insertOrIgnorePathsInfo(
            listOf(samplePathInfoEntity).toSet(),
        )
        val result = gameInfoDao.deletePathsInfo(listOf(samplePathInfoEntity).toSet())

        Assert.assertEquals(1, result)
    }

    @Test
    fun test_getPathsInfo_success() = runTest {
        val pathsInfo = List(3) { samplePathInfoEntity.copy(id = "test$it") }
        gameInfoDao.insertOrIgnorePathsInfo(pathsInfo.toSet())
        val result = gameInfoDao.getPathsInfo().first()

        Assert.assertEquals(pathsInfo, result)
    }

    @Test
    fun test_getPathsInfoByIds_success() = runTest {
        val pathsInfo = List(3) { samplePathInfoEntity.copy(id = "test$it") }
        val takenPathsInfo = pathsInfo.take(2)
        gameInfoDao.insertOrIgnorePathsInfo(pathsInfo.toSet())
        val result = gameInfoDao.getPathsInfo(
            takenPathsInfo.map { it.id }
                .toSet(),
        )
            .first()

        Assert.assertEquals(takenPathsInfo, result)
    }

    @Test
    fun test_insertOrIgnoreCharacterInfo_success() = runTest {
        val size = 3
        val result = gameInfoDao.insertOrIgnoreCharactersInfo(
            List(size) { sampleCharacterInfoEntity.copy(id = "test$it") }.toSet(),
        )

        Assert.assertEquals(List(size) { it + 1L }, result)
    }

    @Test
    fun test_updateCharactersInfo_success() = runTest {
        gameInfoDao.insertOrIgnoreCharactersInfo(
            listOf(sampleCharacterInfoEntity).toSet(),
        )
        val result = gameInfoDao.updateCharactersInfo(
            listOf(sampleCharacterInfoEntity.copy(name = "newName")).toSet(),
        )

        Assert.assertEquals(1, result)
    }

    @Test
    fun test_upsertCharactersInfo_success() = runTest {
        val initialCharacters = List(3) { sampleCharacterInfoEntity.copy(id = "test$it") }
        val insertRowIds = gameInfoDao.insertOrIgnoreCharactersInfo(initialCharacters.toSet())

        Assert.assertEquals(List(3) { it + 1L }, insertRowIds)

        val updatedCharacters = initialCharacters.last().copy(name = "newName")
        val newCharacters = sampleCharacterInfoEntity.copy(id = "newId")
        val charactersToUpsert = initialCharacters.dropLast(1) +
            updatedCharacters + newCharacters
        gameInfoDao.upsertCharactersInfo(charactersToUpsert.toSet())
        val upsertedCharacters = gameInfoDao.getCharactersInfo().first()

        Assert.assertEquals(charactersToUpsert, upsertedCharacters)
    }

    @Test
    fun test_deleteCharactersInfo_success() = runTest {
        gameInfoDao.insertOrIgnoreCharactersInfo(
            listOf(sampleCharacterInfoEntity).toSet(),
        )
        val result = gameInfoDao.deleteCharactersInfo(
            listOf(sampleCharacterInfoEntity).toSet(),
        )

        Assert.assertEquals(1, result)
    }

    @Test
    fun test_getCharactersInfo_success() = runTest {
        val charactersInfo = List(3) { sampleCharacterInfoEntity.copy(id = "test$it") }
        gameInfoDao.insertOrIgnoreCharactersInfo(charactersInfo.toSet())
        val result = gameInfoDao.getCharactersInfo().first()

        Assert.assertEquals(charactersInfo, result)
    }

    @Test
    fun test_getCharactersInfoByIds_success() = runTest {
        val charactersInfo = List(3) { sampleCharacterInfoEntity.copy(id = "test$it") }
        val takenCharactersInfo = charactersInfo.take(2)
        gameInfoDao.insertOrIgnoreCharactersInfo(charactersInfo.toSet())
        val result = gameInfoDao.getCharactersInfo(
            takenCharactersInfo.map { it.id }
                .toSet(),
        )
            .first()

        Assert.assertEquals(takenCharactersInfo, result)
    }

    @Test
    fun test_insertOrIgnoreLightConesInfo_success() = runTest {
        val size = 3
        val result = gameInfoDao.insertOrIgnoreLightConesInfo(
            List(size) { sampleLightConeInfoEntity.copy(id = "test$it") }.toSet(),
        )

        Assert.assertEquals(List(size) { it + 1L }, result)
    }

    @Test
    fun test_updateLightConesInfo_success() = runTest {
        gameInfoDao.insertOrIgnoreLightConesInfo(
            listOf(sampleLightConeInfoEntity).toSet(),
        )
        val result = gameInfoDao.updateLightConesInfo(
            listOf(sampleLightConeInfoEntity.copy(name = "newName")).toSet(),
        )

        Assert.assertEquals(1, result)
    }

    @Test
    fun test_upsertLightConesInfo_success() = runTest {
        val initialLightCones = List(3) { sampleLightConeInfoEntity.copy(id = "test$it") }
        val insertRowIds = gameInfoDao.insertOrIgnoreLightConesInfo(initialLightCones.toSet())

        Assert.assertEquals(List(3) { it + 1L }, insertRowIds)

        val updatedLightCones = initialLightCones.last().copy(name = "newName")
        val newLightCones = sampleLightConeInfoEntity.copy(id = "newId")
        val lightConesToUpsert = initialLightCones.dropLast(1) +
            updatedLightCones + newLightCones
        gameInfoDao.upsertLightConesInfo(lightConesToUpsert.toSet())
        val upsertedLightCones = gameInfoDao.getLightConesInfo().first()

        Assert.assertEquals(lightConesToUpsert, upsertedLightCones)
    }

    @Test
    fun test_deleteLightConesInfo_success() = runTest {
        gameInfoDao.insertOrIgnoreLightConesInfo(
            listOf(sampleLightConeInfoEntity).toSet(),
        )
        val result = gameInfoDao.deleteLightConesInfo(
            listOf(sampleLightConeInfoEntity).toSet(),
        )

        Assert.assertEquals(1, result)
    }

    @Test
    fun test_getLightConesInfo_success() = runTest {
        val lightConesInfo = List(3) { sampleLightConeInfoEntity.copy(id = "test$it") }
        gameInfoDao.insertOrIgnoreLightConesInfo(lightConesInfo.toSet())
        val result = gameInfoDao.getLightConesInfo().first()

        Assert.assertEquals(lightConesInfo, result)
    }

    @Test
    fun test_getLightConesInfoByIds_success() = runTest {
        val lightConesInfo = List(3) { sampleLightConeInfoEntity.copy(id = "test$it") }
        val takenLightConesInfo = lightConesInfo.take(2)
        gameInfoDao.insertOrIgnoreLightConesInfo(lightConesInfo.toSet())
        val result = gameInfoDao.getLightConesInfo(
            takenLightConesInfo.map { it.id }
                .toSet(),
        )
            .first()

        Assert.assertEquals(takenLightConesInfo, result)
    }

    @Test
    fun test_insertOrIgnorePropertiesInfo_success() = runTest {
        val size = 3
        val result = gameInfoDao.insertOrIgnorePropertiesInfo(
            List(size) { samplePropertyInfoEntity.copy(type = "test$it") }.toSet(),
        )

        Assert.assertEquals(List(size) { it + 1L }, result)
    }

    @Test
    fun test_updatePropertiesInfo_success() = runTest {
        gameInfoDao.insertOrIgnorePropertiesInfo(
            listOf(samplePropertyInfoEntity).toSet(),
        )
        val result = gameInfoDao.updatePropertiesInfo(
            listOf(samplePropertyInfoEntity.copy(name = "newName")).toSet(),
        )

        Assert.assertEquals(1, result)
    }

    @Test
    fun test_upsertPropertiesInfo_success() = runTest {
        val initialProperties = List(3) { samplePropertyInfoEntity.copy(type = "test$it") }
        val insertRowIds = gameInfoDao.insertOrIgnorePropertiesInfo(initialProperties.toSet())

        Assert.assertEquals(List(3) { it + 1L }, insertRowIds)

        val updatedProperties = initialProperties.last().copy(name = "newName")
        val newProperties = samplePropertyInfoEntity.copy(type = "newId")
        val propertiesToUpsert = initialProperties.dropLast(1) +
            updatedProperties + newProperties
        gameInfoDao.upsertPropertiesInfo(propertiesToUpsert.toSet())
        val upsertedProperties = gameInfoDao.getPropertiesInfo().first()

        Assert.assertEquals(propertiesToUpsert, upsertedProperties)
    }

    @Test
    fun test_deletePropertiesInfo_success() = runTest {
        gameInfoDao.insertOrIgnorePropertiesInfo(
            listOf(samplePropertyInfoEntity).toSet(),
        )
        val result = gameInfoDao.deletePropertiesInfo(
            listOf(samplePropertyInfoEntity).toSet(),
        )

        Assert.assertEquals(1, result)
    }

    @Test
    fun test_getPropertiesInfo_success() = runTest {
        val propertiesInfo = List(3) { samplePropertyInfoEntity.copy(type = "test$it") }
        gameInfoDao.insertOrIgnorePropertiesInfo(propertiesInfo.toSet())
        val result = gameInfoDao.getPropertiesInfo().first()

        Assert.assertEquals(propertiesInfo, result)
    }

    @Test
    fun test_getPropertiesInfoByIds_success() = runTest {
        val propertiesInfo = List(3) { samplePropertyInfoEntity.copy(type = "test$it") }
        val takenPropertiesInfo = propertiesInfo.take(2)
        gameInfoDao.insertOrIgnorePropertiesInfo(propertiesInfo.toSet())
        val result = gameInfoDao.getPropertiesInfoByIds(
            takenPropertiesInfo.map { it.type }
                .toSet(),
        )
            .first()

        Assert.assertEquals(takenPropertiesInfo, result)
    }

    @Test
    fun test_getPropertiesInfoByFields_success() = runTest {
        val propertiesInfo = List(3) {
            samplePropertyInfoEntity.copy(
                type = "test$it",
                field = "test$it",
            )
        }
        val takenPropertiesInfo = propertiesInfo.take(2)
        gameInfoDao.insertOrIgnorePropertiesInfo(propertiesInfo.toSet())
        val result = gameInfoDao.getPropertiesInfoByFields(
            takenPropertiesInfo.map { it.field }
                .toSet(),
        )
            .first()

        Assert.assertEquals(takenPropertiesInfo, result)
    }

    @Test
    fun test_insertOrIgnoreRelicsInfo_success() = runTest {
        val size = 3
        val result = gameInfoDao.insertOrIgnoreRelicsInfo(
            List(size) { sampleRelicInfoEntity.copy(id = "test$it") }.toSet(),
        )

        Assert.assertEquals(List(size) { it + 1L }, result)
    }

    @Test
    fun test_updateRelicsInfo_success() = runTest {
        gameInfoDao.insertOrIgnoreRelicsInfo(
            listOf(sampleRelicInfoEntity).toSet(),
        )
        val result = gameInfoDao.updateRelicsInfo(
            listOf(sampleRelicInfoEntity.copy(name = "newName")).toSet(),
        )

        Assert.assertEquals(1, result)
    }

    @Test
    fun test_upsertRelicsInfo_success() = runTest {
        val initialRelics = List(3) { sampleRelicInfoEntity.copy(id = "test$it") }
        val insertRowIds = gameInfoDao.insertOrIgnoreRelicsInfo(initialRelics.toSet())

        Assert.assertEquals(List(3) { it + 1L }, insertRowIds)

        val updatedRelics = initialRelics.last().copy(name = "newName")
        val newRelics = sampleRelicInfoEntity.copy(id = "newId")
        val relicsToUpsert = initialRelics.dropLast(1) +
            updatedRelics + newRelics
        gameInfoDao.upsertRelicsInfo(relicsToUpsert.toSet())
        val upsertedRelics = gameInfoDao.getRelicsInfo().first()

        Assert.assertEquals(relicsToUpsert, upsertedRelics)
    }

    @Test
    fun test_deleteRelicsInfo_success() = runTest {
        gameInfoDao.insertOrIgnoreRelicsInfo(
            listOf(sampleRelicInfoEntity).toSet(),
        )
        val result = gameInfoDao.deleteRelicsInfo(
            listOf(sampleRelicInfoEntity).toSet(),
        )

        Assert.assertEquals(1, result)
    }

    @Test
    fun test_getRelicsInfo_success() = runTest {
        val relicsInfo = List(3) { sampleRelicInfoEntity.copy(id = "test$it") }
        gameInfoDao.insertOrIgnoreRelicsInfo(relicsInfo.toSet())
        val result = gameInfoDao.getRelicsInfo().first()

        Assert.assertEquals(relicsInfo, result)
    }

    @Test
    fun test_getRelicsInfoByIds_success() = runTest {
        val relicsInfo = List(3) { sampleRelicInfoEntity.copy(id = "test$it") }
        val takenRelicsInfo = relicsInfo.take(2)
        gameInfoDao.insertOrIgnoreRelicsInfo(relicsInfo.toSet())
        val result = gameInfoDao.getRelicsInfo(
            takenRelicsInfo.map { it.id }
                .toSet(),
        )
            .first()

        Assert.assertEquals(takenRelicsInfo, result)
    }

    @Test
    fun test_insertOrIgnoreRelicSetsInfo_success() = runTest {
        val size = 3
        val result = gameInfoDao.insertOrIgnoreRelicSetsInfo(
            List(size) { sampleRelicSetInfoEntity.copy(id = "test$it") }.toSet(),
        )

        Assert.assertEquals(List(size) { it + 1L }, result)
    }

    @Test
    fun test_updateRelicSetsInfo_success() = runTest {
        gameInfoDao.insertOrIgnoreRelicSetsInfo(
            listOf(sampleRelicSetInfoEntity).toSet(),
        )
        val result = gameInfoDao.updateRelicSetsInfo(
            listOf(sampleRelicSetInfoEntity.copy(name = "newName")).toSet(),
        )

        Assert.assertEquals(1, result)
    }

    @Test
    fun test_upsertRelicSetsInfo_success() = runTest {
        val initialRelicSets = List(3) { sampleRelicSetInfoEntity.copy(id = "test$it") }
        val insertRowIds = gameInfoDao.insertOrIgnoreRelicSetsInfo(initialRelicSets.toSet())

        Assert.assertEquals(List(3) { it + 1L }, insertRowIds)

        val updatedRelicSets = initialRelicSets.last().copy(name = "newName")
        val newRelicSets = sampleRelicSetInfoEntity.copy(id = "newId")
        val relicSetsToUpsert = initialRelicSets.dropLast(1) +
            updatedRelicSets + newRelicSets
        gameInfoDao.upsertRelicSetsInfo(relicSetsToUpsert.toSet())
        val upsertedRelicSets = gameInfoDao.getRelicSetsInfo().first()

        Assert.assertEquals(relicSetsToUpsert, upsertedRelicSets)
    }

    @Test
    fun test_deleteRelicSetsInfo_success() = runTest {
        gameInfoDao.insertOrIgnoreRelicSetsInfo(
            listOf(sampleRelicSetInfoEntity).toSet(),
        )
        val result = gameInfoDao.deleteRelicSetsInfo(
            listOf(sampleRelicSetInfoEntity).toSet(),
        )

        Assert.assertEquals(1, result)
    }

    @Test
    fun test_getRelicSetsInfo_success() = runTest {
        val relicSetsInfo = List(3) { sampleRelicSetInfoEntity.copy(id = "test$it") }
        gameInfoDao.insertOrIgnoreRelicSetsInfo(relicSetsInfo.toSet())
        val result = gameInfoDao.getRelicSetsInfo().first()

        Assert.assertEquals(relicSetsInfo, result)
    }

    @Test
    fun test_getRelicSetsInfoByIds_success() = runTest {
        val relicSetsInfo = List(3) { sampleRelicSetInfoEntity.copy(id = "test$it") }
        val takenRelicSetsInfo = relicSetsInfo.take(2)
        gameInfoDao.insertOrIgnoreRelicSetsInfo(relicSetsInfo.toSet())
        val result = gameInfoDao.getRelicSetsInfo(
            takenRelicSetsInfo.map { it.id }
                .toSet(),
        )
            .first()

        Assert.assertEquals(takenRelicSetsInfo, result)
    }

    @Test
    fun test_insertOrIgnoreAffixesDataEntity_success() = runTest {
        val size = 3
        val result = gameInfoDao.insertOrIgnoreAffixesData(
            List(size) { sampleSubAffixDataEntity.copy(id = "test$it") }.toSet(),
        )

        Assert.assertEquals(List(size) { it + 1L }, result)
    }

    @Test
    fun test_updateAffixesData_success() = runTest {
        gameInfoDao.insertOrIgnoreAffixesData(
            listOf(sampleSubAffixDataEntity).toSet(),
        )
        val result = gameInfoDao.updateAffixesData(
            listOf(sampleSubAffixDataEntity.copy(affixes = emptyMap())).toSet(),
        )

        Assert.assertEquals(1, result)
    }

    @Test
    fun test_upsertAffixesData_success() = runTest {
        val initialSubAffixData = List(3) { sampleSubAffixDataEntity.copy(id = "test$it") }
        val insertRowIds = gameInfoDao.insertOrIgnoreAffixesData(initialSubAffixData.toSet())

        Assert.assertEquals(List(3) { it + 1L }, insertRowIds)

        val updatedSubAffixData = initialSubAffixData.last().copy(affixes = emptyMap())
        val newSubAffixData = sampleSubAffixDataEntity.copy(id = "newId")
        val subAffixDataToUpsert = initialSubAffixData.dropLast(1) +
            updatedSubAffixData + newSubAffixData
        gameInfoDao.upsertAffixesData(subAffixDataToUpsert.toSet())
        val upsertedSubAffixData = gameInfoDao.getAffixesData().first()

        Assert.assertEquals(subAffixDataToUpsert, upsertedSubAffixData)
    }

    @Test
    fun test_deleteAffixesData_success() = runTest {
        gameInfoDao.insertOrIgnoreAffixesData(
            listOf(sampleSubAffixDataEntity).toSet(),
        )
        val result = gameInfoDao.deleteAffixesData(
            listOf(sampleSubAffixDataEntity).toSet(),
        )

        Assert.assertEquals(1, result)
    }

    @Test
    fun test_getAffixesData_success() = runTest {
        val subAffixesData = List(3) { sampleSubAffixDataEntity.copy(id = "test$it") }
        gameInfoDao.insertOrIgnoreAffixesData(subAffixesData.toSet())
        val result = gameInfoDao.getAffixesData().first()

        Assert.assertEquals(subAffixesData, result)
    }

    @Test
    fun test_getAffixesDataByIds_success() = runTest {
        val subAffixesData = List(3) { sampleSubAffixDataEntity.copy(id = "test$it") }
        val takenSubAffixesData = subAffixesData.take(2)
        gameInfoDao.insertOrIgnoreAffixesData(subAffixesData.toSet())
        val result = gameInfoDao.getAffixesData(
            takenSubAffixesData.map { it.id }
                .toSet(),
        )
            .first()

        Assert.assertEquals(takenSubAffixesData, result)
    }

    @Test
    fun test_getCharactersInfoWithDetails_success() = runTest {
        gameInfoDao.insertOrIgnoreCharactersInfo(setOf(sampleCharacterInfoEntity))
        gameInfoDao.insertOrIgnorePathsInfo(setOf(samplePathInfoEntity))
        gameInfoDao.insertOrIgnoreElementsInfo(setOf(sampleElementInfoEntity))
        val result = gameInfoDao.getCharactersInfoWithDetails().first().first()

        Assert.assertEquals(
            sampleCharacterInfoEntity.id,
            result.characterInfo.id,
        )
        Assert.assertEquals(
            sampleCharacterInfoEntity.path,
            result.pathInfo.id,
        )
        Assert.assertEquals(
            sampleCharacterInfoEntity.element,
            result.elementInfo.id,
        )
    }

    @Test
    fun test_getCharactersInfoWithDetailsByIds_success() = runTest {
        gameInfoDao.insertOrIgnoreCharactersInfo(
            List(3) { sampleCharacterInfoEntity.copy(id = "$it") }.toSet(),
        )
        gameInfoDao.insertOrIgnorePathsInfo(setOf(samplePathInfoEntity))
        gameInfoDao.insertOrIgnoreElementsInfo(setOf(sampleElementInfoEntity))
        val result = gameInfoDao.getCharactersInfoWithDetails(setOf("0")).first().first()

        Assert.assertEquals(
            "0",
            result.characterInfo.id,
        )
        Assert.assertEquals(
            sampleCharacterInfoEntity.path,
            result.pathInfo.id,
        )
        Assert.assertEquals(
            sampleCharacterInfoEntity.element,
            result.elementInfo.id,
        )
    }
}
