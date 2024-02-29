package com.dogeby.reliccalculator.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dogeby.reliccalculator.core.database.dao.CharacterDao
import com.dogeby.reliccalculator.core.database.dao.CharacterReportDao
import com.dogeby.reliccalculator.core.database.dao.GameInfoDao
import com.dogeby.reliccalculator.core.database.dao.PresetDao
import com.dogeby.reliccalculator.core.database.model.hoyo.CharacterEntity
import com.dogeby.reliccalculator.core.database.model.hoyo.index.AffixDataEntity
import com.dogeby.reliccalculator.core.database.model.hoyo.index.CharacterInfoEntity
import com.dogeby.reliccalculator.core.database.model.hoyo.index.ElementInfoEntity
import com.dogeby.reliccalculator.core.database.model.hoyo.index.LightConeInfoEntity
import com.dogeby.reliccalculator.core.database.model.hoyo.index.PathInfoEntity
import com.dogeby.reliccalculator.core.database.model.hoyo.index.PropertyInfoEntity
import com.dogeby.reliccalculator.core.database.model.hoyo.index.RelicInfoEntity
import com.dogeby.reliccalculator.core.database.model.hoyo.index.RelicSetInfoEntity
import com.dogeby.reliccalculator.core.database.model.preset.PresetEntity
import com.dogeby.reliccalculator.core.database.model.report.CharacterReportEntity

@Database(
    entities = [
        CharacterEntity::class,
        PresetEntity::class,
        CharacterReportEntity::class,
        ElementInfoEntity::class,
        PathInfoEntity::class,
        CharacterInfoEntity::class,
        LightConeInfoEntity::class,
        PropertyInfoEntity::class,
        RelicInfoEntity::class,
        RelicSetInfoEntity::class,
        AffixDataEntity::class,
    ],
    version = 1,
)
abstract class RelicCalculatorDatabase : RoomDatabase() {

    abstract fun characterDao(): CharacterDao

    abstract fun presetDao(): PresetDao

    abstract fun characterReportDao(): CharacterReportDao

    abstract fun gameInfoDao(): GameInfoDao
}
