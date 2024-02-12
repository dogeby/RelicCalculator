package com.dogeby.game.resource.di

import com.dogeby.game.resource.GameResDataSource
import com.dogeby.game.resource.GameResDataSourceImpl
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
