package com.dogeby.reliccalculator.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.dogeby.reliccalculator.core.database.model.preset.PresetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PresetDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnorePresets(presets: List<PresetEntity>): List<Long>

    @Update
    suspend fun updatePresets(presets: List<PresetEntity>): Int

    @Query("UPDATE presets SET is_auto_update = (:isAutoUpdate) WHERE character_id in (:ids)")
    suspend fun updatePresetsAutoUpdate(
        ids: Set<String>,
        isAutoUpdate: Boolean,
    ): Int

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
