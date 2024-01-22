package com.dogeby.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import androidx.room.Upsert
import com.dogeby.core.database.model.report.CharacterReportEntity
import com.dogeby.core.database.model.report.CharacterWithReports
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterReportDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreCharacterReports(reports: List<CharacterReportEntity>): List<Long>

    @Update
    suspend fun updateCharacterReports(reports: List<CharacterReportEntity>): Int

    @Upsert
    suspend fun upsertCharacterReports(reports: List<CharacterReportEntity>): List<Long>

    @Delete
    suspend fun deleteCharacterReports(reports: List<CharacterReportEntity>): Int

    @Query(value = "SELECT * FROM character_reports")
    fun getCharacterReports(): Flow<List<CharacterReportEntity>>

    @Query(
        value = """
            SELECT * FROM character_reports
            WHERE report_id IN (:ids)
        """,
    )
    fun getCharacterReports(ids: Set<Int>): Flow<List<CharacterReportEntity>>

    @Transaction
    @Query("SELECT * FROM characters")
    fun getCharactersWithReports(): Flow<List<CharacterWithReports>>

    @Transaction
    @Query(
        value = """
            SELECT * FROM characters
            WHERE character_id IN (:ids)
        """,
    )
    fun getCharactersWithReports(ids: Set<String>): Flow<List<CharacterWithReports>>
}
