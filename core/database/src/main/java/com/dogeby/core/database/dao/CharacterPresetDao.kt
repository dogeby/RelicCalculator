package com.dogeby.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.dogeby.core.database.model.preset.CharacterPresetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterPresetDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreCharacterPresets(presets: List<CharacterPresetEntity>): List<Long>

    @Update
    suspend fun updateCharacterPresets(presets: List<CharacterPresetEntity>): Int

    @Upsert
    suspend fun upsertCharacterPresets(characters: List<CharacterPresetEntity>): List<Long>

    @Delete
    suspend fun deleteCharacterPresets(characters: List<CharacterPresetEntity>): Int

    @Query(value = "SELECT * FROM character_presets")
    fun getCharacterPresets(): Flow<List<CharacterPresetEntity>>

    @Query(
        value = """
        SELECT * FROM character_presets
        WHERE character_id IN (:ids)
    """,
    )
    fun getCharacterPresets(ids: Set<String>): Flow<List<CharacterPresetEntity>>
}
