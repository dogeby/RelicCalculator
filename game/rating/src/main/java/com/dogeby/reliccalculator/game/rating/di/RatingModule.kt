package com.dogeby.reliccalculator.game.rating.di

import com.dogeby.reliccalculator.game.rating.CharacterRelicCalculator
import com.dogeby.reliccalculator.game.rating.CharacterRelicCalculatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RatingModule {

    @Binds
    @Singleton
    abstract fun bindsCharacterRelicCalculator(
        characterRelicCalculatorImpl: CharacterRelicCalculatorImpl,
    ): CharacterRelicCalculator
}
