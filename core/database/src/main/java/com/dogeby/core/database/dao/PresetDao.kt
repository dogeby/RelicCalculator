package com.dogeby.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.dogeby.core.database.model.preset.PresetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PresetDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnorePresets(presets: List<PresetEntity>): List<Long>

    @Update
    suspend fun updatePresets(presets: List<PresetEntity>): Int

    @Upsert
    suspend fun upsertPresets(presets: List<PresetEntity>): List<Long>

    @Delete
    suspend fun deletePresets(presets: List<PresetEntity>): Int

    @Query(value = "SELECT * FROM presets")
    fun getPresets(): Flow<List<PresetEntity>>

    @Query(
        value = """
        SELECT * FROM presets
        WHERE character_id IN (:ids)
    """,
    )
    fun getPresets(ids: Set<String>): Flow<List<PresetEntity>>
}
