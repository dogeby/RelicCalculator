package com.dogeby.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.dogeby.core.database.model.hoyo.CharacterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreCharacters(characters: List<CharacterEntity>): List<Long>

    @Update
    suspend fun updateCharacters(characters: List<CharacterEntity>): Int

    @Upsert
    suspend fun upsertCharacters(characters: List<CharacterEntity>): List<Long>

    @Delete
    suspend fun deleteCharacters(characters: List<CharacterEntity>): Int

    @Query(value = "SELECT * FROM characters")
    fun getCharacterEntities(): Flow<List<CharacterEntity>>

    @Query(
        value = """
        SELECT * FROM characters
        WHERE character_id IN (:ids)
    """,
    )
    fun getCharacterEntities(ids: Set<String>): Flow<List<CharacterEntity>>
}
