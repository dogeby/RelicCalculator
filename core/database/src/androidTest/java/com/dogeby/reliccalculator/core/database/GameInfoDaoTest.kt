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
    fun test_insertOrIgnoreElementInfoSet_success() = runTest {
        val size = 3
        val result = gameInfoDao.insertOrIgnoreElementInfoSet(
            List(size) { sampleElementInfoEntity.copy(id = "test$it") }.toSet(),
        )

        Assert.assertEquals(List(size) { it + 1L }, result)
    }

    @Test
    fun test_updateElementInfoSet_success() = runTest {
        gameInfoDao.insertOrIgnoreElementInfoSet(
            listOf(sampleElementInfoEntity).toSet(),
        )
        val result = gameInfoDao.updateElementInfoSet(
            listOf(sampleElementInfoEntity.copy(name = "newName")).toSet(),
        )

        Assert.assertEquals(1, result)
    }

    @Test
    fun test_upsertElementInfoSet_success() = runTest {
        val initialElements = List(3) { sampleElementInfoEntity.copy(id = "test$it") }
        val insertRowIds = gameInfoDao.insertOrIgnoreElementInfoSet(initialElements.toSet())

        Assert.assertEquals(List(3) { it + 1L }, insertRowIds)

        val updatedElement = initialElements.last().copy(name = "newName")
        val newElement = sampleElementInfoEntity.copy(id = "newId")
        val elementsToUpsert = initialElements.dropLast(1) +
            updatedElement + newElement
        gameInfoDao.upsertElementInfoSet(elementsToUpsert.toSet())
        val upsertedElements = gameInfoDao.getElementInfoList().first()

        Assert.assertEquals(elementsToUpsert, upsertedElements)
    }

    @Test
    fun test_deleteElementInfoSet_success() = runTest {
        gameInfoDao.insertOrIgnoreElementInfoSet(
            listOf(sampleElementInfoEntity).toSet(),
        )
        val result = gameInfoDao.deleteElementInfoSet(listOf(sampleElementInfoEntity).toSet())

        Assert.assertEquals(1, result)
    }

    @Test
    fun test_getElementInfoList_success() = runTest {
        val elementInfo = List(3) { sampleElementInfoEntity.copy(id = "test$it") }
        gameInfoDao.insertOrIgnoreElementInfoSet(elementInfo.toSet())
        val result = gameInfoDao.getElementInfoList().first()

        Assert.assertEquals(elementInfo, result)
    }

    @Test
    fun test_getElementInfoListByIds_success() = runTest {
        val elementInfo = List(3) { sampleElementInfoEntity.copy(id = "test$it") }
        val takenElementInfo = elementInfo.take(2)
        gameInfoDao.insertOrIgnoreElementInfoSet(elementInfo.toSet())
        val result = gameInfoDao.getElementInfoList(
            takenElementInfo.map { it.id }
                .toSet(),
        )
            .first()

        Assert.assertEquals(takenElementInfo, result)
    }

    @Test
    fun test_insertOrIgnorePathInfoSet_success() = runTest {
        val size = 3
        val result = gameInfoDao.insertOrIgnorePathInfoSet(
            List(size) { samplePathInfoEntity.copy(id = "test$it") }.toSet(),
        )

        Assert.assertEquals(List(size) { it + 1L }, result)
    }

    @Test
    fun test_updatePathInfoSet_success() = runTest {
        gameInfoDao.insertOrIgnorePathInfoSet(
            listOf(samplePathInfoEntity).toSet(),
        )
        val result = gameInfoDao.updatePathInfoSet(
            listOf(samplePathInfoEntity.copy(name = "newName")).toSet(),
        )

        Assert.assertEquals(1, result)
    }

    @Test
    fun test_upsertPathInfoSet_success() = runTest {
        val initialPaths = List(3) { samplePathInfoEntity.copy(id = "test$it") }
        val insertRowIds = gameInfoDao.insertOrIgnorePathInfoSet(initialPaths.toSet())

        Assert.assertEquals(List(3) { it + 1L }, insertRowIds)

        val updatedPath = initialPaths.last().copy(name = "newName")
        val newPath = samplePathInfoEntity.copy(id = "newId")
        val pathsToUpsert = initialPaths.dropLast(1) +
            updatedPath + newPath
        gameInfoDao.upsertPathInfoSet(pathsToUpsert.toSet())
        val upsertedPaths = gameInfoDao.getPathInfoList().first()

        Assert.assertEquals(pathsToUpsert, upsertedPaths)
    }

    @Test
    fun test_deletePathInfoSet_success() = runTest {
        gameInfoDao.insertOrIgnorePathInfoSet(
            listOf(samplePathInfoEntity).toSet(),
        )
        val result = gameInfoDao.deletePathInfoSet(listOf(samplePathInfoEntity).toSet())

        Assert.assertEquals(1, result)
    }

    @Test
    fun test_getPathInfoList_success() = runTest {
        val pathInfo = List(3) { samplePathInfoEntity.copy(id = "test$it") }
        gameInfoDao.insertOrIgnorePathInfoSet(pathInfo.toSet())
        val result = gameInfoDao.getPathInfoList().first()

        Assert.assertEquals(pathInfo, result)
    }

    @Test
    fun test_getPathInfoListByIds_success() = runTest {
        val pathInfo = List(3) { samplePathInfoEntity.copy(id = "test$it") }
        val takenPathInfo = pathInfo.take(2)
        gameInfoDao.insertOrIgnorePathInfoSet(pathInfo.toSet())
        val result = gameInfoDao.getPathInfoList(
            takenPathInfo.map { it.id }
                .toSet(),
        )
            .first()

        Assert.assertEquals(takenPathInfo, result)
    }

    @Test
    fun test_insertOrIgnoreCharacterInfoSet_success() = runTest {
        val size = 3
        val result = gameInfoDao.insertOrIgnoreCharacterInfoSet(
            List(size) { sampleCharacterInfoEntity.copy(id = "test$it") }.toSet(),
        )

        Assert.assertEquals(List(size) { it + 1L }, result)
    }

    @Test
    fun test_updateCharacterInfoSet_success() = runTest {
        gameInfoDao.insertOrIgnoreCharacterInfoSet(
            listOf(sampleCharacterInfoEntity).toSet(),
        )
        val result = gameInfoDao.updateCharacterInfoSet(
            listOf(sampleCharacterInfoEntity.copy(name = "newName")).toSet(),
        )

        Assert.assertEquals(1, result)
    }

    @Test
    fun test_upsertCharacterInfoSet_success() = runTest {
        val initialCharacters = List(3) { sampleCharacterInfoEntity.copy(id = "test$it") }
        val insertRowIds = gameInfoDao.insertOrIgnoreCharacterInfoSet(initialCharacters.toSet())

        Assert.assertEquals(List(3) { it + 1L }, insertRowIds)

        val updatedCharacters = initialCharacters.last().copy(name = "newName")
        val newCharacters = sampleCharacterInfoEntity.copy(id = "newId")
        val charactersToUpsert = initialCharacters.dropLast(1) +
            updatedCharacters + newCharacters
        gameInfoDao.upsertCharacterInfoSet(charactersToUpsert.toSet())
        val upsertedCharacters = gameInfoDao.getCharacterInfoList().first()

        Assert.assertEquals(charactersToUpsert, upsertedCharacters)
    }

    @Test
    fun test_deleteCharacterInfoSet_success() = runTest {
        gameInfoDao.insertOrIgnoreCharacterInfoSet(
            listOf(sampleCharacterInfoEntity).toSet(),
        )
        val result = gameInfoDao.deleteCharacterInfoSet(
            listOf(sampleCharacterInfoEntity).toSet(),
        )

        Assert.assertEquals(1, result)
    }

    @Test
    fun test_getCharacterInfoList_success() = runTest {
        val characterInfo = List(3) { sampleCharacterInfoEntity.copy(id = "test$it") }
        gameInfoDao.insertOrIgnoreCharacterInfoSet(characterInfo.toSet())
        val result = gameInfoDao.getCharacterInfoList().first()

        Assert.assertEquals(characterInfo, result)
    }

    @Test
    fun test_getCharacterInfoListByIds_success() = runTest {
        val characterInfo = List(3) { sampleCharacterInfoEntity.copy(id = "test$it") }
        val takenCharacterInfo = characterInfo.take(2)
        gameInfoDao.insertOrIgnoreCharacterInfoSet(characterInfo.toSet())
        val result = gameInfoDao.getCharacterInfoList(
            takenCharacterInfo.map { it.id }
                .toSet(),
        )
            .first()

        Assert.assertEquals(takenCharacterInfo, result)
    }

    @Test
    fun test_insertOrIgnoreLightConeInfoSet_success() = runTest {
        val size = 3
        val result = gameInfoDao.insertOrIgnoreLightConeInfoSet(
            List(size) { sampleLightConeInfoEntity.copy(id = "test$it") }.toSet(),
        )

        Assert.assertEquals(List(size) { it + 1L }, result)
    }

    @Test
    fun test_updateLightConeInfoSet_success() = runTest {
        gameInfoDao.insertOrIgnoreLightConeInfoSet(
            listOf(sampleLightConeInfoEntity).toSet(),
        )
        val result = gameInfoDao.updateLightConeInfoSet(
            listOf(sampleLightConeInfoEntity.copy(name = "newName")).toSet(),
        )

        Assert.assertEquals(1, result)
    }

    @Test
    fun test_upsertLightConeInfoSet_success() = runTest {
        val initialLightCones = List(3) { sampleLightConeInfoEntity.copy(id = "test$it") }
        val insertRowIds = gameInfoDao.insertOrIgnoreLightConeInfoSet(initialLightCones.toSet())

        Assert.assertEquals(List(3) { it + 1L }, insertRowIds)

        val updatedLightCones = initialLightCones.last().copy(name = "newName")
        val newLightCones = sampleLightConeInfoEntity.copy(id = "newId")
        val lightConesToUpsert = initialLightCones.dropLast(1) +
            updatedLightCones + newLightCones
        gameInfoDao.upsertLightConeInfoSet(lightConesToUpsert.toSet())
        val upsertedLightCones = gameInfoDao.getLightConeInfoList().first()

        Assert.assertEquals(lightConesToUpsert, upsertedLightCones)
    }

    @Test
    fun test_deleteLightConeInfoSet_success() = runTest {
        gameInfoDao.insertOrIgnoreLightConeInfoSet(
            listOf(sampleLightConeInfoEntity).toSet(),
        )
        val result = gameInfoDao.deleteLightConeInfoSet(
            listOf(sampleLightConeInfoEntity).toSet(),
        )

        Assert.assertEquals(1, result)
    }

    @Test
    fun test_getLightConeInfoList_success() = runTest {
        val lightConeInfo = List(3) { sampleLightConeInfoEntity.copy(id = "test$it") }
        gameInfoDao.insertOrIgnoreLightConeInfoSet(lightConeInfo.toSet())
        val result = gameInfoDao.getLightConeInfoList().first()

        Assert.assertEquals(lightConeInfo, result)
    }

    @Test
    fun test_getLightConeInfoListByIds_success() = runTest {
        val lightConeInfo = List(3) { sampleLightConeInfoEntity.copy(id = "test$it") }
        val takenLightConeInfo = lightConeInfo.take(2)
        gameInfoDao.insertOrIgnoreLightConeInfoSet(lightConeInfo.toSet())
        val result = gameInfoDao.getLightConeInfoList(
            takenLightConeInfo.map { it.id }
                .toSet(),
        )
            .first()

        Assert.assertEquals(takenLightConeInfo, result)
    }

    @Test
    fun test_insertOrIgnorePropertyInfoSet_success() = runTest {
        val size = 3
        val result = gameInfoDao.insertOrIgnorePropertyInfoSet(
            List(size) { samplePropertyInfoEntity.copy(type = "test$it") }.toSet(),
        )

        Assert.assertEquals(List(size) { it + 1L }, result)
    }

    @Test
    fun test_updatePropertyInfoSet_success() = runTest {
        gameInfoDao.insertOrIgnorePropertyInfoSet(
            listOf(samplePropertyInfoEntity).toSet(),
        )
        val result = gameInfoDao.updatePropertyInfoSet(
            listOf(samplePropertyInfoEntity.copy(name = "newName")).toSet(),
        )

        Assert.assertEquals(1, result)
    }

    @Test
    fun test_upsertPropertyInfoSet_success() = runTest {
        val initialProperties = List(3) { samplePropertyInfoEntity.copy(type = "test$it") }
        val insertRowIds = gameInfoDao.insertOrIgnorePropertyInfoSet(initialProperties.toSet())

        Assert.assertEquals(List(3) { it + 1L }, insertRowIds)

        val updatedProperties = initialProperties.last().copy(name = "newName")
        val newProperties = samplePropertyInfoEntity.copy(type = "newId")
        val propertyToUpsert = initialProperties.dropLast(1) +
            updatedProperties + newProperties
        gameInfoDao.upsertPropertyInfoSet(propertyToUpsert.toSet())
        val upsertedProperties = gameInfoDao.getPropertyInfoList().first()

        Assert.assertEquals(propertyToUpsert, upsertedProperties)
    }

    @Test
    fun test_deletePropertyInfoSet_success() = runTest {
        gameInfoDao.insertOrIgnorePropertyInfoSet(
            listOf(samplePropertyInfoEntity).toSet(),
        )
        val result = gameInfoDao.deletePropertyInfoSet(
            listOf(samplePropertyInfoEntity).toSet(),
        )

        Assert.assertEquals(1, result)
    }

    @Test
    fun test_getPropertyInfoList_success() = runTest {
        val properties = List(3) { samplePropertyInfoEntity.copy(type = "test$it") }
        gameInfoDao.insertOrIgnorePropertyInfoSet(properties.toSet())
        val result = gameInfoDao.getPropertyInfoList().first()

        Assert.assertEquals(properties, result)
    }

    @Test
    fun test_getPropertyInfoListByIds_success() = runTest {
        val properties = List(3) { samplePropertyInfoEntity.copy(type = "test$it") }
        val takenProperties = properties.take(2)
        gameInfoDao.insertOrIgnorePropertyInfoSet(properties.toSet())
        val result = gameInfoDao.getPropertyInfoListByIds(
            takenProperties.map { it.type }
                .toSet(),
        )
            .first()

        Assert.assertEquals(takenProperties, result)
    }

    @Test
    fun test_getPropertyInfoListByFields_success() = runTest {
        val properties = List(3) {
            samplePropertyInfoEntity.copy(
                type = "test$it",
                field = "test$it",
            )
        }
        val takenProperties = properties.take(2)
        gameInfoDao.insertOrIgnorePropertyInfoSet(properties.toSet())
        val result = gameInfoDao.getPropertyInfoListByFields(
            takenProperties.map { it.field }
                .toSet(),
        )
            .first()

        Assert.assertEquals(takenProperties, result)
    }

    @Test
    fun test_insertOrIgnoreRelicInfoSet_success() = runTest {
        val size = 3
        val result = gameInfoDao.insertOrIgnoreRelicInfoSet(
            List(size) { sampleRelicInfoEntity.copy(id = "test$it") }.toSet(),
        )

        Assert.assertEquals(List(size) { it + 1L }, result)
    }

    @Test
    fun test_updateRelicInfoSet_success() = runTest {
        gameInfoDao.insertOrIgnoreRelicInfoSet(
            listOf(sampleRelicInfoEntity).toSet(),
        )
        val result = gameInfoDao.updateRelicInfoSet(
            listOf(sampleRelicInfoEntity.copy(name = "newName")).toSet(),
        )

        Assert.assertEquals(1, result)
    }

    @Test
    fun test_upsertRelicInfoSet_success() = runTest {
        val initialRelics = List(3) { sampleRelicInfoEntity.copy(id = "test$it") }
        val insertRowIds = gameInfoDao.insertOrIgnoreRelicInfoSet(initialRelics.toSet())

        Assert.assertEquals(List(3) { it + 1L }, insertRowIds)

        val updatedRelics = initialRelics.last().copy(name = "newName")
        val newRelics = sampleRelicInfoEntity.copy(id = "newId")
        val relicsToUpsert = initialRelics.dropLast(1) +
            updatedRelics + newRelics
        gameInfoDao.upsertRelicInfoSet(relicsToUpsert.toSet())
        val upsertedRelics = gameInfoDao.getRelicInfoList().first()

        Assert.assertEquals(relicsToUpsert, upsertedRelics)
    }

    @Test
    fun test_deleteRelicInfoSet_success() = runTest {
        gameInfoDao.insertOrIgnoreRelicInfoSet(
            listOf(sampleRelicInfoEntity).toSet(),
        )
        val result = gameInfoDao.deleteRelicInfoSet(
            listOf(sampleRelicInfoEntity).toSet(),
        )

        Assert.assertEquals(1, result)
    }

    @Test
    fun test_getRelicInfoList_success() = runTest {
        val relicInfo = List(3) { sampleRelicInfoEntity.copy(id = "test$it") }
        gameInfoDao.insertOrIgnoreRelicInfoSet(relicInfo.toSet())
        val result = gameInfoDao.getRelicInfoList().first()

        Assert.assertEquals(relicInfo, result)
    }

    @Test
    fun test_getRelicInfoListByIds_success() = runTest {
        val relicInfo = List(3) { sampleRelicInfoEntity.copy(id = "test$it") }
        val takenRelicInfo = relicInfo.take(2)
        gameInfoDao.insertOrIgnoreRelicInfoSet(relicInfo.toSet())
        val result = gameInfoDao.getRelicInfoList(
            takenRelicInfo.map { it.id }
                .toSet(),
        )
            .first()

        Assert.assertEquals(takenRelicInfo, result)
    }

    @Test
    fun test_insertOrIgnoreRelicSetInfoSet_success() = runTest {
        val size = 3
        val result = gameInfoDao.insertOrIgnoreRelicSetInfoSet(
            List(size) { sampleRelicSetInfoEntity.copy(id = "test$it") }.toSet(),
        )

        Assert.assertEquals(List(size) { it + 1L }, result)
    }

    @Test
    fun test_updateRelicSetInfoSet_success() = runTest {
        gameInfoDao.insertOrIgnoreRelicSetInfoSet(
            listOf(sampleRelicSetInfoEntity).toSet(),
        )
        val result = gameInfoDao.updateRelicSetInfoSet(
            listOf(sampleRelicSetInfoEntity.copy(name = "newName")).toSet(),
        )

        Assert.assertEquals(1, result)
    }

    @Test
    fun test_upsertRelicSetInfoSet_success() = runTest {
        val initialRelicSets = List(3) { sampleRelicSetInfoEntity.copy(id = "test$it") }
        val insertRowIds = gameInfoDao.insertOrIgnoreRelicSetInfoSet(initialRelicSets.toSet())

        Assert.assertEquals(List(3) { it + 1L }, insertRowIds)

        val updatedRelicSets = initialRelicSets.last().copy(name = "newName")
        val newRelicSets = sampleRelicSetInfoEntity.copy(id = "newId")
        val relicSetsToUpsert = initialRelicSets.dropLast(1) +
            updatedRelicSets + newRelicSets
        gameInfoDao.upsertRelicSetInfoSet(relicSetsToUpsert.toSet())
        val upsertedRelicSets = gameInfoDao.getRelicSetInfoList().first()

        Assert.assertEquals(relicSetsToUpsert, upsertedRelicSets)
    }

    @Test
    fun test_deleteRelicSetInfoSet_success() = runTest {
        gameInfoDao.insertOrIgnoreRelicSetInfoSet(
            listOf(sampleRelicSetInfoEntity).toSet(),
        )
        val result = gameInfoDao.deleteRelicSetInfoSet(
            listOf(sampleRelicSetInfoEntity).toSet(),
        )

        Assert.assertEquals(1, result)
    }

    @Test
    fun test_getRelicSetInfoList_success() = runTest {
        val relicSetInfo = List(3) { sampleRelicSetInfoEntity.copy(id = "test$it") }
        gameInfoDao.insertOrIgnoreRelicSetInfoSet(relicSetInfo.toSet())
        val result = gameInfoDao.getRelicSetInfoList().first()

        Assert.assertEquals(relicSetInfo, result)
    }

    @Test
    fun test_getRelicSetInfoListByIds_success() = runTest {
        val relicSetInfo = List(3) { sampleRelicSetInfoEntity.copy(id = "test$it") }
        val takenRelicSetInfo = relicSetInfo.take(2)
        gameInfoDao.insertOrIgnoreRelicSetInfoSet(relicSetInfo.toSet())
        val result = gameInfoDao.getRelicSetInfoList(
            takenRelicSetInfo.map { it.id }
                .toSet(),
        )
            .first()

        Assert.assertEquals(takenRelicSetInfo, result)
    }

    @Test
    fun test_insertOrIgnoreAffixDataSet_success() = runTest {
        val size = 3
        val result = gameInfoDao.insertOrIgnoreAffixDataSet(
            List(size) { sampleSubAffixDataEntity.copy(id = "test$it") }.toSet(),
        )

        Assert.assertEquals(List(size) { it + 1L }, result)
    }

    @Test
    fun test_updateAffixDataSet_success() = runTest {
        gameInfoDao.insertOrIgnoreAffixDataSet(
            listOf(sampleSubAffixDataEntity).toSet(),
        )
        val result = gameInfoDao.updateAffixDataSet(
            listOf(sampleSubAffixDataEntity.copy(affixes = emptyMap())).toSet(),
        )

        Assert.assertEquals(1, result)
    }

    @Test
    fun test_upsertAffixDataSet_success() = runTest {
        val initialSubAffixes = List(3) { sampleSubAffixDataEntity.copy(id = "test$it") }
        val insertRowIds = gameInfoDao.insertOrIgnoreAffixDataSet(initialSubAffixes.toSet())

        Assert.assertEquals(List(3) { it + 1L }, insertRowIds)

        val updatedSubAffixes = initialSubAffixes.last().copy(affixes = emptyMap())
        val newSubAffixes = sampleSubAffixDataEntity.copy(id = "newId")
        val subAffixesToUpsert = initialSubAffixes.dropLast(1) +
            updatedSubAffixes + newSubAffixes
        gameInfoDao.upsertAffixDataSet(subAffixesToUpsert.toSet())
        val upsertedSubAffixes = gameInfoDao.getAffixDataList().first()

        Assert.assertEquals(subAffixesToUpsert, upsertedSubAffixes)
    }

    @Test
    fun test_deleteAffixDataSet_success() = runTest {
        gameInfoDao.insertOrIgnoreAffixDataSet(
            listOf(sampleSubAffixDataEntity).toSet(),
        )
        val result = gameInfoDao.deleteAffixDataSet(
            listOf(sampleSubAffixDataEntity).toSet(),
        )

        Assert.assertEquals(1, result)
    }

    @Test
    fun test_getAffixDataList_success() = runTest {
        val subAffixes = List(3) { sampleSubAffixDataEntity.copy(id = "test$it") }
        gameInfoDao.insertOrIgnoreAffixDataSet(subAffixes.toSet())
        val result = gameInfoDao.getAffixDataList().first()

        Assert.assertEquals(subAffixes, result)
    }

    @Test
    fun test_getAffixDataListByIds_success() = runTest {
        val subAffixes = List(3) { sampleSubAffixDataEntity.copy(id = "test$it") }
        val takenSubAffixes = subAffixes.take(2)
        gameInfoDao.insertOrIgnoreAffixDataSet(subAffixes.toSet())
        val result = gameInfoDao.getAffixDataList(
            takenSubAffixes.map { it.id }
                .toSet(),
        )
            .first()

        Assert.assertEquals(takenSubAffixes, result)
    }

    @Test
    fun test_getCharacterInfoWithDetailsList_success() = runTest {
        gameInfoDao.insertOrIgnoreCharacterInfoSet(setOf(sampleCharacterInfoEntity))
        gameInfoDao.insertOrIgnorePathInfoSet(setOf(samplePathInfoEntity))
        gameInfoDao.insertOrIgnoreElementInfoSet(setOf(sampleElementInfoEntity))
        val result = gameInfoDao.getCharacterInfoWithDetailsList().first().first()

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
    fun test_getCharacterInfoWithDetailsListByIds_success() = runTest {
        gameInfoDao.insertOrIgnoreCharacterInfoSet(
            List(3) { sampleCharacterInfoEntity.copy(id = "$it") }.toSet(),
        )
        gameInfoDao.insertOrIgnorePathInfoSet(setOf(samplePathInfoEntity))
        gameInfoDao.insertOrIgnoreElementInfoSet(setOf(sampleElementInfoEntity))
        val result = gameInfoDao.getCharacterInfoWithDetailsList(setOf("0")).first().first()

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
