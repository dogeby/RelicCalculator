package com.dogeby.core.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.dogeby.core.database.dao.GameInfoDao
import com.dogeby.core.database.model.hoyo.index.sampleElementInfoEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GameInfoDaoTest {

    private lateinit var gameInfoDao: GameInfoDao
    private lateinit var db: RelicCalculatorDatabase

    private val sampleElementInfo = sampleElementInfoEntity

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
            List(size) { sampleElementInfo.copy(id = "test$it") }.toSet(),
        )

        Assert.assertEquals(List(size) { it + 1L }, result)
    }

    @Test
    fun test_updateElementsInfo_success() = runTest {
        gameInfoDao.insertOrIgnoreElementsInfo(
            listOf(sampleElementInfo).toSet(),
        )
        val result = gameInfoDao.updateElementsInfo(
            listOf(sampleElementInfo.copy(name = "newName")).toSet(),
        )

        Assert.assertEquals(1, result)
    }

    @Test
    fun test_deleteElementsInfo_success() = runTest {
        gameInfoDao.insertOrIgnoreElementsInfo(
            listOf(sampleElementInfo).toSet(),
        )
        val result = gameInfoDao.deleteElementsInfo(listOf(sampleElementInfo).toSet())

        Assert.assertEquals(1, result)
    }

    @Test
    fun test_getElementsInfo_success() = runTest {
        val elementsInfo = List(3) { sampleElementInfo.copy(id = "test$it") }
        gameInfoDao.insertOrIgnoreElementsInfo(elementsInfo.toSet())
        val result = gameInfoDao.getElementsInfo().first()

        Assert.assertEquals(elementsInfo, result)
    }
}
