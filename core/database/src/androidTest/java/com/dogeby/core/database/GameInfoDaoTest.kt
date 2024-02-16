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
    fun test_insertOrIgnorePathsInfoEntity_success() = runTest {
        val size = 3
        val result = gameInfoDao.insertOrIgnorePathsInfoEntity(
            List(size) { samplePathInfoEntity.copy(id = "test$it") }.toSet(),
        )

        Assert.assertEquals(List(size) { it + 1L }, result)
    }

    @Test
    fun test_updatePathsInfoEntity_success() = runTest {
        gameInfoDao.insertOrIgnorePathsInfoEntity(
            listOf(samplePathInfoEntity).toSet(),
        )
        val result = gameInfoDao.updatePathsInfoEntity(
            listOf(samplePathInfoEntity.copy(name = "newName")).toSet(),
        )

        Assert.assertEquals(1, result)
    }

    @Test
    fun test_deletePathsInfoEntity_success() = runTest {
        gameInfoDao.insertOrIgnorePathsInfoEntity(
            listOf(samplePathInfoEntity).toSet(),
        )
        val result = gameInfoDao.deletePathsInfoEntity(listOf(samplePathInfoEntity).toSet())

        Assert.assertEquals(1, result)
    }

    @Test
    fun test_getPathsInfoEntity_success() = runTest {
        val elementsInfo = List(3) { samplePathInfoEntity.copy(id = "test$it") }
        gameInfoDao.insertOrIgnorePathsInfoEntity(elementsInfo.toSet())
        val result = gameInfoDao.getPathsInfoEntity().first()

        Assert.assertEquals(elementsInfo, result)
    }

    @Test
    fun test_insertOrIgnoreCharacterInfoEntity_success() = runTest {
        val size = 3
        val result = gameInfoDao.insertOrIgnoreCharactersInfoEntity(
            List(size) { sampleCharacterInfoEntity.copy(id = "test$it") }.toSet(),
        )

        Assert.assertEquals(List(size) { it + 1L }, result)
    }

    @Test
    fun test_updateCharactersInfoEntity_success() = runTest {
        gameInfoDao.insertOrIgnoreCharactersInfoEntity(
            listOf(sampleCharacterInfoEntity).toSet(),
        )
        val result = gameInfoDao.updateCharactersInfoEntity(
            listOf(sampleCharacterInfoEntity.copy(name = "newName")).toSet(),
        )

        Assert.assertEquals(1, result)
    }

    @Test
    fun test_deleteCharactersInfoEntity_success() = runTest {
        gameInfoDao.insertOrIgnoreCharactersInfoEntity(
            listOf(sampleCharacterInfoEntity).toSet(),
        )
        val result = gameInfoDao.deleteCharactersInfoEntity(
            listOf(sampleCharacterInfoEntity).toSet(),
        )

        Assert.assertEquals(1, result)
    }

    @Test
    fun test_getCharactersInfoEntity_success() = runTest {
        val charactersInfo = List(3) { sampleCharacterInfoEntity.copy(id = "test$it") }
        gameInfoDao.insertOrIgnoreCharactersInfoEntity(charactersInfo.toSet())
        val result = gameInfoDao.getCharactersInfoEntity().first()

        Assert.assertEquals(charactersInfo, result)
    }

    @Test
    fun test_getCharactersInfoEntityByIds_success() = runTest {
        val charactersInfo = List(3) { sampleCharacterInfoEntity.copy(id = "test$it") }
        val takenCharactersInfo = charactersInfo.take(2)
        gameInfoDao.insertOrIgnoreCharactersInfoEntity(charactersInfo.toSet())
        val result = gameInfoDao.getCharactersInfoEntity(
            takenCharactersInfo.map { it.id }
                .toSet(),
        )
            .first()

        Assert.assertEquals(takenCharactersInfo, result)
    }

    @Test
    fun test_insertOrIgnoreLightConesInfoEntity_success() = runTest {
        val size = 3
        val result = gameInfoDao.insertOrIgnoreLightConesInfoEntity(
            List(size) { sampleLightConeInfoEntity.copy(id = "test$it") }.toSet(),
        )

        Assert.assertEquals(List(size) { it + 1L }, result)
    }

    @Test
    fun test_updateLightConesInfoEntity_success() = runTest {
        gameInfoDao.insertOrIgnoreLightConesInfoEntity(
            listOf(sampleLightConeInfoEntity).toSet(),
        )
        val result = gameInfoDao.updateLightConesInfoEntity(
            listOf(sampleLightConeInfoEntity.copy(name = "newName")).toSet(),
        )

        Assert.assertEquals(1, result)
    }

    @Test
    fun test_deleteLightConesInfoEntity_success() = runTest {
        gameInfoDao.insertOrIgnoreLightConesInfoEntity(
            listOf(sampleLightConeInfoEntity).toSet(),
        )
        val result = gameInfoDao.deleteLightConesInfoEntity(
            listOf(sampleLightConeInfoEntity).toSet(),
        )

        Assert.assertEquals(1, result)
    }

    @Test
    fun test_getLightConesInfoEntity_success() = runTest {
        val lightConesInfo = List(3) { sampleLightConeInfoEntity.copy(id = "test$it") }
        gameInfoDao.insertOrIgnoreLightConesInfoEntity(lightConesInfo.toSet())
        val result = gameInfoDao.getLightConesInfoEntity().first()

        Assert.assertEquals(lightConesInfo, result)
    }

    @Test
    fun test_insertOrIgnorePropertiesInfoEntity_success() = runTest {
        val size = 3
        val result = gameInfoDao.insertOrIgnorePropertiesInfoEntity(
            List(size) { samplePropertyInfoEntity.copy(type = "test$it") }.toSet(),
        )

        Assert.assertEquals(List(size) { it + 1L }, result)
    }

    @Test
    fun test_updatePropertiesInfoEntity_success() = runTest {
        gameInfoDao.insertOrIgnorePropertiesInfoEntity(
            listOf(samplePropertyInfoEntity).toSet(),
        )
        val result = gameInfoDao.updatePropertiesInfoEntity(
            listOf(samplePropertyInfoEntity.copy(name = "newName")).toSet(),
        )

        Assert.assertEquals(1, result)
    }

    @Test
    fun test_deletePropertiesInfoEntity_success() = runTest {
        gameInfoDao.insertOrIgnorePropertiesInfoEntity(
            listOf(samplePropertyInfoEntity).toSet(),
        )
        val result = gameInfoDao.deletePropertiesInfoEntity(
            listOf(samplePropertyInfoEntity).toSet(),
        )

        Assert.assertEquals(1, result)
    }

    @Test
    fun test_getPropertiesInfoEntity_success() = runTest {
        val propertiesInfo = List(3) { samplePropertyInfoEntity.copy(type = "test$it") }
        gameInfoDao.insertOrIgnorePropertiesInfoEntity(propertiesInfo.toSet())
        val result = gameInfoDao.getPropertiesInfoEntity().first()

        Assert.assertEquals(propertiesInfo, result)
    }

    @Test
    fun test_insertOrIgnoreRelicsInfoEntity_success() = runTest {
        val size = 3
        val result = gameInfoDao.insertOrIgnoreRelicsInfoEntity(
            List(size) { sampleRelicInfoEntity.copy(id = "test$it") }.toSet(),
        )

        Assert.assertEquals(List(size) { it + 1L }, result)
    }

    @Test
    fun test_updateRelicsInfoEntity_success() = runTest {
        gameInfoDao.insertOrIgnoreRelicsInfoEntity(
            listOf(sampleRelicInfoEntity).toSet(),
        )
        val result = gameInfoDao.updateRelicsInfoEntity(
            listOf(sampleRelicInfoEntity.copy(name = "newName")).toSet(),
        )

        Assert.assertEquals(1, result)
    }

    @Test
    fun test_deleteRelicsInfoEntity_success() = runTest {
        gameInfoDao.insertOrIgnoreRelicsInfoEntity(
            listOf(sampleRelicInfoEntity).toSet(),
        )
        val result = gameInfoDao.deleteRelicsInfoEntity(
            listOf(sampleRelicInfoEntity).toSet(),
        )

        Assert.assertEquals(1, result)
    }

    @Test
    fun test_getRelicsInfoEntity_success() = runTest {
        val relicsInfo = List(3) { sampleRelicInfoEntity.copy(id = "test$it") }
        gameInfoDao.insertOrIgnoreRelicsInfoEntity(relicsInfo.toSet())
        val result = gameInfoDao.getRelicsInfoEntity().first()

        Assert.assertEquals(relicsInfo, result)
    }

    @Test
    fun test_insertOrIgnoreRelicSetsInfoEntity_success() = runTest {
        val size = 3
        val result = gameInfoDao.insertOrIgnoreRelicSetsInfoEntity(
            List(size) { sampleRelicSetInfoEntity.copy(id = "test$it") }.toSet(),
        )

        Assert.assertEquals(List(size) { it + 1L }, result)
    }

    @Test
    fun test_updateRelicSetsInfoEntity_success() = runTest {
        gameInfoDao.insertOrIgnoreRelicSetsInfoEntity(
            listOf(sampleRelicSetInfoEntity).toSet(),
        )
        val result = gameInfoDao.updateRelicSetsInfoEntity(
            listOf(sampleRelicSetInfoEntity.copy(name = "newName")).toSet(),
        )

        Assert.assertEquals(1, result)
    }

    @Test
    fun test_deleteRelicSetsInfoEntity_success() = runTest {
        gameInfoDao.insertOrIgnoreRelicSetsInfoEntity(
            listOf(sampleRelicSetInfoEntity).toSet(),
        )
        val result = gameInfoDao.deleteRelicSetsInfoEntity(
            listOf(sampleRelicSetInfoEntity).toSet(),
        )

        Assert.assertEquals(1, result)
    }

    @Test
    fun test_getRelicSetsInfoEntity_success() = runTest {
        val relicSetsInfo = List(3) { sampleRelicSetInfoEntity.copy(id = "test$it") }
        gameInfoDao.insertOrIgnoreRelicSetsInfoEntity(relicSetsInfo.toSet())
        val result = gameInfoDao.getRelicSetsInfoEntity().first()

        Assert.assertEquals(relicSetsInfo, result)
    }

    @Test
    fun test_insertOrIgnoreAffixesDataEntity_success() = runTest {
        val size = 3
        val result = gameInfoDao.insertOrIgnoreAffixesDataEntity(
            List(size) { sampleSubAffixDataEntity.copy(id = "test$it") }.toSet(),
        )

        Assert.assertEquals(List(size) { it + 1L }, result)
    }

    @Test
    fun test_updateAffixesDataEntity_success() = runTest {
        gameInfoDao.insertOrIgnoreAffixesDataEntity(
            listOf(sampleSubAffixDataEntity).toSet(),
        )
        val result = gameInfoDao.updateAffixesDataEntity(
            listOf(sampleSubAffixDataEntity.copy(affixes = emptyMap())).toSet(),
        )

        Assert.assertEquals(1, result)
    }

    @Test
    fun test_deleteAffixesDataEntity_success() = runTest {
        gameInfoDao.insertOrIgnoreAffixesDataEntity(
            listOf(sampleSubAffixDataEntity).toSet(),
        )
        val result = gameInfoDao.deleteAffixesDataEntity(
            listOf(sampleSubAffixDataEntity).toSet(),
        )

        Assert.assertEquals(1, result)
    }

    @Test
    fun test_getAffixesDataEntity_success() = runTest {
        val subAffixesData = List(3) { sampleSubAffixDataEntity.copy(id = "test$it") }
        gameInfoDao.insertOrIgnoreAffixesDataEntity(subAffixesData.toSet())
        val result = gameInfoDao.getAffixesDataEntity().first()

        Assert.assertEquals(subAffixesData, result)
    }

    @Test
    fun test_getCharactersInfoWithDetails_success() = runTest {
        gameInfoDao.insertOrIgnoreCharactersInfoEntity(setOf(sampleCharacterInfoEntity))
        gameInfoDao.insertOrIgnorePathsInfoEntity(setOf(samplePathInfoEntity))
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
        gameInfoDao.insertOrIgnoreCharactersInfoEntity(
            List(3) { sampleCharacterInfoEntity.copy(id = "$it") }.toSet(),
        )
        gameInfoDao.insertOrIgnorePathsInfoEntity(setOf(samplePathInfoEntity))
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
