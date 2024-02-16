package com.dogeby.core.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.dogeby.core.database.dao.GameInfoDao
import com.dogeby.core.database.model.hoyo.index.sampleElementInfoEntity
import com.dogeby.core.database.model.hoyo.index.samplePathInfoEntity
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
}
