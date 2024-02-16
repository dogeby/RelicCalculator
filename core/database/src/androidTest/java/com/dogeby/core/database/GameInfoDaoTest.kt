package com.dogeby.core.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.dogeby.core.database.dao.GameInfoDao
import com.dogeby.core.database.model.hoyo.index.sampleCharacterInfoEntity
import com.dogeby.core.database.model.hoyo.index.sampleElementInfoEntity
import com.dogeby.core.database.model.hoyo.index.sampleLightConeInfoEntity
import com.dogeby.core.database.model.hoyo.index.samplePathInfoEntity
import com.dogeby.core.database.model.hoyo.index.samplePropertyInfoEntity
import com.dogeby.core.database.model.hoyo.index.sampleRelicInfoEntity
import com.dogeby.core.database.model.hoyo.index.sampleRelicSetInfoEntity
import com.dogeby.core.database.model.hoyo.index.sampleSubAffixDataEntity
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
    fun test_deletePathsInfo_success() = runTest {
        gameInfoDao.insertOrIgnorePathsInfo(
            listOf(samplePathInfoEntity).toSet(),
        )
        val result = gameInfoDao.deletePathsInfo(listOf(samplePathInfoEntity).toSet())

        Assert.assertEquals(1, result)
    }

    @Test
    fun test_getPathsInfo_success() = runTest {
        val elementsInfo = List(3) { samplePathInfoEntity.copy(id = "test$it") }
        gameInfoDao.insertOrIgnorePathsInfo(elementsInfo.toSet())
        val result = gameInfoDao.getPathsInfo().first()

        Assert.assertEquals(elementsInfo, result)
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
    fun test_getCharactersInfoWithDetails_success() = runTest {
        gameInfoDao.insertOrIgnoreCharactersInfo(setOf(sampleCharacterInfoEntity))
        gameInfoDao.insertOrIgnorePathsInfo(setOf(samplePathInfoEntity))
        gameInfoDao.insertOrIgnoreElementsInfo(setOf(sampleElementInfoEntity))
        val result = gameInfoDao.getCharactersInfoWithDetails().first()

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
        val result = gameInfoDao.getCharactersInfoWithDetails(setOf("0")).first()

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
