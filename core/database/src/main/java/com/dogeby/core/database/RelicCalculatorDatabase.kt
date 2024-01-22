package com.dogeby.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dogeby.core.database.dao.CharacterDao
import com.dogeby.core.database.dao.CharacterPresetDao
import com.dogeby.core.database.dao.CharacterReportDao
import com.dogeby.core.database.model.hoyo.CharacterEntity
import com.dogeby.core.database.model.preset.CharacterPresetEntity
import com.dogeby.core.database.model.report.CharacterReportEntity

@Database(
    entities = [
        CharacterEntity::class,
        CharacterPresetEntity::class,
        CharacterReportEntity::class,
    ],
    version = 1,
)
abstract class RelicCalculatorDatabase : RoomDatabase() {

    abstract fun CharacterDao(): CharacterDao

    abstract fun CharacterPresetDao(): CharacterPresetDao

    abstract fun CharacterReportDao(): CharacterReportDao
}
