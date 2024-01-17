package com.dogeby.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dogeby.core.database.dao.CharacterDao
import com.dogeby.core.database.model.hoyo.CharacterEntity

@Database(entities = [CharacterEntity::class], version = 1)
abstract class RelicCalculatorDatabase : RoomDatabase() {
    abstract fun CharacterDao(): CharacterDao
}
