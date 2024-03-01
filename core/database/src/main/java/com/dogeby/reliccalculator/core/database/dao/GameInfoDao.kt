package com.dogeby.reliccalculator.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import androidx.room.Upsert
import com.dogeby.reliccalculator.core.database.model.hoyo.index.AffixDataEntity
import com.dogeby.reliccalculator.core.database.model.hoyo.index.CharacterInfoEntity
import com.dogeby.reliccalculator.core.database.model.hoyo.index.DatabaseCharacterInfoWithDetails
import com.dogeby.reliccalculator.core.database.model.hoyo.index.ElementInfoEntity
import com.dogeby.reliccalculator.core.database.model.hoyo.index.LightConeInfoEntity
import com.dogeby.reliccalculator.core.database.model.hoyo.index.PathInfoEntity
import com.dogeby.reliccalculator.core.database.model.hoyo.index.PropertyInfoEntity
import com.dogeby.reliccalculator.core.database.model.hoyo.index.RelicInfoEntity
import com.dogeby.reliccalculator.core.database.model.hoyo.index.RelicSetInfoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GameInfoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreElementInfoSet(elementInfoSet: Set<ElementInfoEntity>): List<Long>

    @Update
    suspend fun updateElementInfoSet(elementInfoSet: Set<ElementInfoEntity>): Int

    @Upsert
    suspend fun upsertElementInfoSet(elementInfoSet: Set<ElementInfoEntity>): List<Long>

    @Delete
    suspend fun deleteElementInfoSet(elementInfoSet: Set<ElementInfoEntity>): Int

    @Query(value = "SELECT * FROM elementInfoTable")
    fun getElementInfoList(): Flow<List<ElementInfoEntity>>

    @Query(value = "SELECT * FROM elementInfoTable WHERE id in (:ids)")
    fun getElementInfoList(ids: Set<String>): Flow<List<ElementInfoEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnorePathInfoSet(pathInfoSet: Set<PathInfoEntity>): List<Long>

    @Update
    suspend fun updatePathInfoSet(pathInfoSet: Set<PathInfoEntity>): Int

    @Upsert
    suspend fun upsertPathInfoSet(pathInfoSet: Set<PathInfoEntity>): List<Long>

    @Delete
    suspend fun deletePathInfoSet(pathInfoSet: Set<PathInfoEntity>): Int

    @Query(value = "SELECT * FROM pathInfoTable")
    fun getPathInfoList(): Flow<List<PathInfoEntity>>

    @Query(value = "SELECT * FROM pathInfoTable WHERE id in (:ids)")
    fun getPathInfoList(ids: Set<String>): Flow<List<PathInfoEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreCharacterInfoSet(
        characterInfoSet: Set<CharacterInfoEntity>,
    ): List<Long>

    @Update
    suspend fun updateCharacterInfoSet(characterInfoSet: Set<CharacterInfoEntity>): Int

    @Upsert
    suspend fun upsertCharacterInfoSet(characterInfoSet: Set<CharacterInfoEntity>): List<Long>

    @Delete
    suspend fun deleteCharacterInfoSet(characterInfoSet: Set<CharacterInfoEntity>): Int

    @Query(value = "SELECT * FROM characterInfoTable")
    fun getCharacterInfoList(): Flow<List<CharacterInfoEntity>>

    @Query(
        value = """
        SELECT * FROM characterInfoTable
        WHERE id in (:ids)
    """,
    )
    fun getCharacterInfoList(ids: Set<String>): Flow<List<CharacterInfoEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreLightConeInfoSet(
        lightConeInfoSet: Set<LightConeInfoEntity>,
    ): List<Long>

    @Update
    suspend fun updateLightConeInfoSet(lightConeInfoSet: Set<LightConeInfoEntity>): Int

    @Upsert
    suspend fun upsertLightConeInfoSet(lightConeInfoSet: Set<LightConeInfoEntity>): List<Long>

    @Delete
    suspend fun deleteLightConeInfoSet(lightConeInfoSet: Set<LightConeInfoEntity>): Int

    @Query(value = "SELECT * FROM lightConeInfoTable")
    fun getLightConeInfoList(): Flow<List<LightConeInfoEntity>>

    @Query(value = "SELECT * FROM lightConeInfoTable WHERE id in (:ids)")
    fun getLightConeInfoList(ids: Set<String>): Flow<List<LightConeInfoEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnorePropertyInfoSet(propertyInfoSet: Set<PropertyInfoEntity>): List<Long>

    @Update
    suspend fun updatePropertyInfoSet(propertyInfoSet: Set<PropertyInfoEntity>): Int

    @Upsert
    suspend fun upsertPropertyInfoSet(propertyInfoSet: Set<PropertyInfoEntity>): List<Long>

    @Delete
    suspend fun deletePropertyInfoSet(propertyInfoSet: Set<PropertyInfoEntity>): Int

    @Query(value = "SELECT * FROM propertyInfoTable")
    fun getPropertyInfoList(): Flow<List<PropertyInfoEntity>>

    @Query(value = "SELECT * FROM propertyInfoTable WHERE type in (:ids)")
    fun getPropertyInfoListByIds(ids: Set<String>): Flow<List<PropertyInfoEntity>>

    @Query(value = "SELECT * FROM propertyInfoTable WHERE field in (:fields)")
    fun getPropertyInfoListByFields(fields: Set<String>): Flow<List<PropertyInfoEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreRelicInfoSet(relicInfoSet: Set<RelicInfoEntity>): List<Long>

    @Update
    suspend fun updateRelicInfoSet(relicInfoSet: Set<RelicInfoEntity>): Int

    @Upsert
    suspend fun upsertRelicInfoSet(relicInfoSet: Set<RelicInfoEntity>): List<Long>

    @Delete
    suspend fun deleteRelicInfoSet(relicInfoSet: Set<RelicInfoEntity>): Int

    @Query(value = "SELECT * FROM relicInfoTable")
    fun getRelicInfoList(): Flow<List<RelicInfoEntity>>

    @Query(value = "SELECT * FROM relicInfoTable WHERE id in (:ids)")
    fun getRelicInfoList(ids: Set<String>): Flow<List<RelicInfoEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreRelicSetInfoSet(relicSetInfoSet: Set<RelicSetInfoEntity>): List<Long>

    @Update
    suspend fun updateRelicSetInfoSet(relicSetInfoSet: Set<RelicSetInfoEntity>): Int

    @Upsert
    suspend fun upsertRelicSetInfoSet(relicSetInfoSet: Set<RelicSetInfoEntity>): List<Long>

    @Delete
    suspend fun deleteRelicSetInfoSet(relicSetInfoSet: Set<RelicSetInfoEntity>): Int

    @Query(value = "SELECT * FROM relicSetInfoTable")
    fun getRelicSetInfoList(): Flow<List<RelicSetInfoEntity>>

    @Query(value = "SELECT * FROM relicSetInfoTable WHERE id in (:ids)")
    fun getRelicSetInfoList(ids: Set<String>): Flow<List<RelicSetInfoEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreAffixDataSet(affixesDataEntity: Set<AffixDataEntity>): List<Long>

    @Update
    suspend fun updateAffixDataSet(affixesDataEntity: Set<AffixDataEntity>): Int

    @Upsert
    suspend fun upsertAffixDataSet(affixesDataEntity: Set<AffixDataEntity>): List<Long>

    @Delete
    suspend fun deleteAffixDataSet(affixesDataEntity: Set<AffixDataEntity>): Int

    @Query(value = "SELECT * FROM affixDataTable")
    fun getAffixDataList(): Flow<List<AffixDataEntity>>

    @Query(value = "SELECT * FROM affixDataTable WHERE id in (:ids)")
    fun getAffixDataList(ids: Set<String>): Flow<List<AffixDataEntity>>

    @Transaction
    @Query("SELECT * FROM characterInfoTable")
    fun getCharacterInfoWithDetailsList(): Flow<List<DatabaseCharacterInfoWithDetails>>

    @Transaction
    @Query(
        """
        SELECT * FROM characterInfoTable
        WHERE id in (:ids)
    """,
    )
    fun getCharacterInfoWithDetailsList(
        ids: Set<String>,
    ): Flow<List<DatabaseCharacterInfoWithDetails>>
}
