package com.dogeby.reliccalculator.core.domain.di

import com.dogeby.reliccalculator.core.domain.index.GetRelicSetInfoListUseCase
import com.dogeby.reliccalculator.core.domain.index.GetRelicSetInfoListUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
abstract class IndexUseCaseModule {

    @Binds
    @Singleton
    abstract fun bindsGetRelicSetInfoListUseCase(
        getRelicSetInfoListUseCaseImpl: GetRelicSetInfoListUseCaseImpl,
    ): GetRelicSetInfoListUseCase
}
