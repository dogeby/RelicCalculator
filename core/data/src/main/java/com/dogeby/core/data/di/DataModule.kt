package com.dogeby.core.data.di

import com.dogeby.core.data.repository.PreferencesRepository
import com.dogeby.core.data.repository.PreferencesRepositoryImpl
import com.dogeby.core.data.repository.PresetRepository
import com.dogeby.core.data.repository.PresetRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindsPresetRepository(
        presetRepositoryImpl: PresetRepositoryImpl,
    ): PresetRepository

    @Binds
    @Singleton
    abstract fun bindsPreferencesRepository(
        preferencesRepositoryImpl: PreferencesRepositoryImpl,
    ): PreferencesRepository
}