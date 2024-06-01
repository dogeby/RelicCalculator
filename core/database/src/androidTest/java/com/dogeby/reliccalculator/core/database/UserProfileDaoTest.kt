package com.dogeby.reliccalculator.core.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.dogeby.reliccalculator.core.database.dao.UserProfileDao
import com.dogeby.reliccalculator.core.database.model.user.sampleUserProfileEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class UserProfileDaoTest {

    private lateinit var userProfileDao: UserProfileDao
    private lateinit var db: RelicCalculatorDatabase

    private val userProfileEntity = sampleUserProfileEntity

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            RelicCalculatorDatabase::class.java,
        ).build()
        userProfileDao = db.userProfileDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun test_userProfileDao_insert_success() = runTest {
        val size = 3
        val result = userProfileDao.insertOrIgnoreUserProfiles(
            List(size) { userProfileEntity.copy(id = "$it") },
        )

        Assert.assertEquals(List(size) { it + 1L }, result)
    }

    @Test
    fun test_userProfileDao_update_success() = runTest {
        userProfileDao.insertOrIgnoreUserProfiles(
            listOf(userProfileEntity),
        )
        userProfileDao.insertOrIgnoreUserProfiles(
            listOf(userProfileEntity.copy(characterIds = listOf("1"))),
        )
        val result = userProfileDao.updateUserProfiles(
            listOf(userProfileEntity.copy(characterIds = listOf("2"))),
        )

        Assert.assertEquals(1, result)
    }

    @Test
    fun test_userProfileDao_upsert_success() = runTest {
        val initialUserProfilesSize = 3
        val initialUserProfiles =
            List(initialUserProfilesSize) { userProfileEntity.copy(id = "$it") }
        val insertRowIds = userProfileDao.insertOrIgnoreUserProfiles(initialUserProfiles)

        Assert.assertEquals(List(initialUserProfilesSize) { it + 1L }, insertRowIds)

        val updatedUserProfile = initialUserProfiles.last().copy(characterIds = listOf("1"))
        val newUserProfile = userProfileEntity.copy(id = "newId")
        val userProfilesToUpsert = initialUserProfiles.dropLast(1) +
            updatedUserProfile + newUserProfile
        userProfileDao.upsertUserProfiles(userProfilesToUpsert)
        val upsertedUserProfiles = userProfileDao.getUserProfiles().first()

        Assert.assertEquals(userProfilesToUpsert, upsertedUserProfiles)
    }

    @Test
    fun test_userProfileDao_delete_success() = runTest {
        userProfileDao.insertOrIgnoreUserProfiles(listOf(userProfileEntity))
        val result = userProfileDao.deleteUserProfiles(listOf(userProfileEntity))

        Assert.assertEquals(1, result)
    }

    @Test
    fun test_userProfileDao_getAllCharacters_success() = runTest {
        val userProfiles = List(3) { userProfileEntity.copy(id = "$it") }
        userProfileDao.insertOrIgnoreUserProfiles(userProfiles)
        val result = userProfileDao.getUserProfiles().first()

        Assert.assertEquals(userProfiles, result)
    }

    @Test
    fun test_userProfileDao_getCharacters_success() = runTest {
        val ids = mutableListOf<String>()
        val userProfiles = List(3) { index ->
            val id = "test$index".also { ids.add(it) }
            userProfileEntity.copy(id = id)
        }
        userProfileDao.insertOrIgnoreUserProfiles(userProfiles)
        val result = userProfileDao.getUserProfiles(ids.toSet()).first()

        Assert.assertEquals(userProfiles, result)
    }
}
