package com.dogeby.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.dogeby.core.database.model.hoyo.index.CharacterInfoEntity
import com.dogeby.core.database.model.hoyo.index.ElementInfoEntity
import com.dogeby.core.database.model.hoyo.index.LightConeInfoEntity
import com.dogeby.core.database.model.hoyo.index.PathInfoEntity
import com.dogeby.core.database.model.hoyo.index.PropertyInfoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GameInfoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreElementsInfo(elementsInfo: Set<ElementInfoEntity>): List<Long>

    @Update
    suspend fun updateElementsInfo(elementsInfo: Set<ElementInfoEntity>): Int

    @Delete
    suspend fun deleteElementsInfo(elementsInfo: Set<ElementInfoEntity>): Int

    @Query(value = "SELECT * FROM elementsInfo")
    fun getElementsInfo(): Flow<List<ElementInfoEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnorePathsInfoEntity(pathsInfo: Set<PathInfoEntity>): List<Long>

    @Update
    suspend fun updatePathsInfoEntity(pathsInfo: Set<PathInfoEntity>): Int

    @Delete
    suspend fun deletePathsInfoEntity(pathsInfo: Set<PathInfoEntity>): Int

    @Query(value = "SELECT * FROM pathsInfo")
    fun getPathsInfoEntity(): Flow<List<PathInfoEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreCharactersInfoEntity(
        charactersInfo: Set<CharacterInfoEntity>,
    ): List<Long>

    @Update
    suspend fun updateCharactersInfoEntity(charactersInfo: Set<CharacterInfoEntity>): Int

    @Delete
    suspend fun deleteCharactersInfoEntity(charactersInfo: Set<CharacterInfoEntity>): Int

    @Query(value = "SELECT * FROM charactersInfo")
    fun getCharactersInfoEntity(): Flow<List<CharacterInfoEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreLightConesInfoEntity(
        lightConesInfoEntity: Set<LightConeInfoEntity>,
    ): List<Long>

    @Update
    suspend fun updateLightConesInfoEntity(lightConesInfoEntity: Set<LightConeInfoEntity>): Int

    @Delete
    suspend fun deleteLightConesInfoEntity(lightConesInfoEntity: Set<LightConeInfoEntity>): Int

    @Query(value = "SELECT * FROM lightConesInfo")
    fun getLightConesInfoEntity(): Flow<List<LightConeInfoEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnorePropertiesInfoEntity(
        propertiesInfoEntity: Set<PropertyInfoEntity>,
    ): List<Long>

    @Update
    suspend fun updatePropertiesInfoEntity(propertiesInfoEntity: Set<PropertyInfoEntity>): Int

    @Delete
    suspend fun deletePropertiesInfoEntity(propertiesInfoEntity: Set<PropertyInfoEntity>): Int

    @Query(value = "SELECT * FROM propertiesInfo")
    fun getPropertiesInfoEntity(): Flow<List<PropertyInfoEntity>>
}
