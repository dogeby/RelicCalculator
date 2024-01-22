package com.dogeby.core.database.di

import com.dogeby.core.database.RelicCalculatorDatabase
import com.dogeby.core.database.dao.CharacterDao
import com.dogeby.core.database.dao.CharacterPresetDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    fun providesCharacterDao(database: RelicCalculatorDatabase): CharacterDao =
        database.CharacterDao()

    @Provides
    fun providesCharacterPresetDao(database: RelicCalculatorDatabase): CharacterPresetDao =
        database.CharacterPresetDao()
}
