package com.dogeby.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.dogeby.core.database.model.hoyo.index.AffixDataEntity
import com.dogeby.core.database.model.hoyo.index.CharacterInfoEntity
import com.dogeby.core.database.model.hoyo.index.DatabaseCharacterInfoWithDetails
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
    suspend fun insertOrIgnorePathsInfo(pathsInfo: Set<PathInfoEntity>): List<Long>

    @Update
    suspend fun updatePathsInfo(pathsInfo: Set<PathInfoEntity>): Int

    @Delete
    suspend fun deletePathsInfo(pathsInfo: Set<PathInfoEntity>): Int

    @Query(value = "SELECT * FROM pathsInfo")
    fun getPathsInfo(): Flow<List<PathInfoEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreCharactersInfo(
        charactersInfo: Set<CharacterInfoEntity>,
    ): List<Long>

    @Update
    suspend fun updateCharactersInfo(charactersInfo: Set<CharacterInfoEntity>): Int

    @Delete
    suspend fun deleteCharactersInfo(charactersInfo: Set<CharacterInfoEntity>): Int

    @Query(value = "SELECT * FROM charactersInfo")
    fun getCharactersInfo(): Flow<List<CharacterInfoEntity>>

    @Query(
        value = """
        SELECT * FROM charactersInfo
        WHERE id in (:ids)
    """,
    )
    fun getCharactersInfo(ids: Set<String>): Flow<List<CharacterInfoEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreLightConesInfo(
        lightConesInfoEntity: Set<LightConeInfoEntity>,
    ): List<Long>

    @Update
    suspend fun updateLightConesInfo(lightConesInfoEntity: Set<LightConeInfoEntity>): Int

    @Delete
    suspend fun deleteLightConesInfo(lightConesInfoEntity: Set<LightConeInfoEntity>): Int

    @Query(value = "SELECT * FROM lightConesInfo")
    fun getLightConesInfo(): Flow<List<LightConeInfoEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnorePropertiesInfo(
        propertiesInfoEntity: Set<PropertyInfoEntity>,
    ): List<Long>

    @Update
    suspend fun updatePropertiesInfo(propertiesInfoEntity: Set<PropertyInfoEntity>): Int

    @Delete
    suspend fun deletePropertiesInfo(propertiesInfoEntity: Set<PropertyInfoEntity>): Int

    @Query(value = "SELECT * FROM propertiesInfo")
    fun getPropertiesInfo(): Flow<List<PropertyInfoEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreRelicsInfo(relicsInfoEntity: Set<RelicInfoEntity>): List<Long>

    @Update
    suspend fun updateRelicsInfo(relicsInfoEntity: Set<RelicInfoEntity>): Int

    @Delete
    suspend fun deleteRelicsInfo(relicsInfoEntity: Set<RelicInfoEntity>): Int

    @Query(value = "SELECT * FROM relicsInfo")
    fun getRelicsInfo(): Flow<List<RelicInfoEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreRelicSetsInfo(
        relicSetsInfoEntity: Set<RelicSetInfoEntity>,
    ): List<Long>

    @Update
    suspend fun updateRelicSetsInfo(relicSetsInfoEntity: Set<RelicSetInfoEntity>): Int

    @Delete
    suspend fun deleteRelicSetsInfo(relicSetsInfoEntity: Set<RelicSetInfoEntity>): Int

    @Query(value = "SELECT * FROM relicSetsInfo")
    fun getRelicSetsInfo(): Flow<List<RelicSetInfoEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreAffixesData(
        affixesDataEntity: Set<AffixDataEntity>,
    ): List<Long>

    @Update
    suspend fun updateAffixesData(affixesDataEntity: Set<AffixDataEntity>): Int

    @Delete
    suspend fun deleteAffixesData(affixesDataEntity: Set<AffixDataEntity>): Int

    @Query(value = "SELECT * FROM affixesData")
    fun getAffixesData(): Flow<List<AffixDataEntity>>

    @Transaction
    @Query("SELECT * FROM charactersInfo")
    fun getCharactersInfoWithDetails(): List<DatabaseCharacterInfoWithDetails>

    @Transaction
    @Query(
        """
        SELECT * FROM charactersInfo
        WHERE id in (:ids)
    """,
    )
    fun getCharactersInfoWithDetails(ids: Set<String>): List<DatabaseCharacterInfoWithDetails>
}
