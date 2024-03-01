package com.dogeby.reliccalculator.core.database.di

import com.dogeby.reliccalculator.core.database.RelicCalculatorDatabase
import com.dogeby.reliccalculator.core.database.dao.CharacterDao
import com.dogeby.reliccalculator.core.database.dao.CharacterReportDao
import com.dogeby.reliccalculator.core.database.dao.GameInfoDao
import com.dogeby.reliccalculator.core.database.dao.PresetDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    fun providesCharacterDao(database: RelicCalculatorDatabase): CharacterDao =
        database.characterDao()

    @Provides
    fun providesPresetDao(database: RelicCalculatorDatabase): PresetDao = database.presetDao()

    @Provides
    fun providesCharacterReportDao(database: RelicCalculatorDatabase): CharacterReportDao =
        database.characterReportDao()

    @Provides
    fun providesGameInfoDao(database: RelicCalculatorDatabase): GameInfoDao = database.gameInfoDao()
}
