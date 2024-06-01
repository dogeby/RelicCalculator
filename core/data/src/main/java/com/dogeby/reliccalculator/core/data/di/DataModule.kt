package com.dogeby.reliccalculator.core.data.di

import com.dogeby.reliccalculator.core.data.repository.CharacterReportRepository
import com.dogeby.reliccalculator.core.data.repository.CharacterReportRepositoryImpl
import com.dogeby.reliccalculator.core.data.repository.GameRepository
import com.dogeby.reliccalculator.core.data.repository.GameRepositoryImpl
import com.dogeby.reliccalculator.core.data.repository.PreferencesRepository
import com.dogeby.reliccalculator.core.data.repository.PreferencesRepositoryImpl
import com.dogeby.reliccalculator.core.data.repository.PresetRepository
import com.dogeby.reliccalculator.core.data.repository.PresetRepositoryImpl
import com.dogeby.reliccalculator.core.data.repository.UserProfileRepository
import com.dogeby.reliccalculator.core.data.repository.UserProfileRepositoryImpl
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

    @Binds
    @Singleton
    abstract fun bindsGameRepository(gameRepositoryImpl: GameRepositoryImpl): GameRepository

    @Binds
    @Singleton
    abstract fun bindsCharacterReportRepository(
        characterReportRepositoryImpl: CharacterReportRepositoryImpl,
    ): CharacterReportRepository

    @Binds
    @Singleton
    abstract fun bindsUserProfileRepository(
        userProfileRepositoryImpl: UserProfileRepositoryImpl,
    ): UserProfileRepository
}
