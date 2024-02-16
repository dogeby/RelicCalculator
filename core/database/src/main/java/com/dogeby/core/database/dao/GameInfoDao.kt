package com.dogeby.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.dogeby.core.database.model.hoyo.index.AffixDataEntity
import com.dogeby.core.database.model.hoyo.index.CharacterInfoEntity
import com.dogeby.core.database.model.hoyo.index.ElementInfoEntity
import com.dogeby.core.database.model.hoyo.index.LightConeInfoEntity
import com.dogeby.core.database.model.hoyo.index.PathInfoEntity
import com.dogeby.core.database.model.hoyo.index.PropertyInfoEntity
import com.dogeby.core.database.model.hoyo.index.RelicInfoEntity
import com.dogeby.core.database.model.hoyo.index.RelicSetInfoEntity
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

    @Query(
        value = """
        SELECT * FROM charactersInfo
        WHERE id in (:ids)
    """,
    )
    fun getCharactersInfoEntity(ids: Set<String>): Flow<List<CharacterInfoEntity>>

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

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreRelicsInfoEntity(relicsInfoEntity: Set<RelicInfoEntity>): List<Long>

    @Update
    suspend fun updateRelicsInfoEntity(relicsInfoEntity: Set<RelicInfoEntity>): Int

    @Delete
    suspend fun deleteRelicsInfoEntity(relicsInfoEntity: Set<RelicInfoEntity>): Int

    @Query(value = "SELECT * FROM relicsInfo")
    fun getRelicsInfoEntity(): Flow<List<RelicInfoEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreRelicSetsInfoEntity(
        relicSetsInfoEntity: Set<RelicSetInfoEntity>,
    ): List<Long>

    @Update
    suspend fun updateRelicSetsInfoEntity(relicSetsInfoEntity: Set<RelicSetInfoEntity>): Int

    @Delete
    suspend fun deleteRelicSetsInfoEntity(relicSetsInfoEntity: Set<RelicSetInfoEntity>): Int

    @Query(value = "SELECT * FROM relicSetsInfo")
    fun getRelicSetsInfoEntity(): Flow<List<RelicSetInfoEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreAffixesDataEntity(
        affixesDataEntity: Set<AffixDataEntity>,
    ): List<Long>

    @Update
    suspend fun updateAffixesDataEntity(affixesDataEntity: Set<AffixDataEntity>): Int

    @Delete
    suspend fun deleteAffixesDataEntity(affixesDataEntity: Set<AffixDataEntity>): Int

    @Query(value = "SELECT * FROM affixesData")
    fun getAffixesDataEntity(): Flow<List<AffixDataEntity>>
}
