package com.dogeby.reliccalculator.game.resource.di

import com.dogeby.reliccalculator.game.resource.GameResDataSource
import com.dogeby.reliccalculator.game.resource.GameResDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class GameResourceModule {

    @Binds
    @Singleton
    abstract fun bindsGameResDataSource(
        gameResDataSourceImpl: GameResDataSourceImpl,
    ): GameResDataSource
}
