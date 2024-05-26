package com.dogeby.reliccalculator.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.dogeby.reliccalculator.core.database.model.user.UserProfileEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserProfileDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreUserProfiles(userProfiles: List<UserProfileEntity>): List<Long>

    @Update
    suspend fun updateUserProfiles(userProfiles: List<UserProfileEntity>): Int

    @Upsert
    suspend fun upsertUserProfiles(userProfiles: List<UserProfileEntity>): List<Long>

    @Delete
    suspend fun deleteUserProfiles(userProfiles: List<UserProfileEntity>): Int

    @Query(value = "SELECT * FROM userProfiles")
    fun getUserProfiles(): Flow<List<UserProfileEntity>>

    @Query(
        value = """
            SELECT * FROM userProfiles
            WHERE id IN (:ids)
        """,
    )
    fun getUserProfiles(ids: Set<String>): Flow<List<UserProfileEntity>>
}
