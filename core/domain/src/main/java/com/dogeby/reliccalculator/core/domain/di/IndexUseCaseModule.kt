package com.dogeby.reliccalculator.core.domain.di

import com.dogeby.reliccalculator.core.domain.index.GetRelicSetInfoListUseCase
import com.dogeby.reliccalculator.core.domain.index.GetRelicSetInfoListUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class IndexUseCaseModule {

    @Binds
    @ViewModelScoped
    abstract fun bindsGetRelicSetInfoListUseCase(
        getRelicSetInfoListUseCaseImpl: GetRelicSetInfoListUseCaseImpl,
    ): GetRelicSetInfoListUseCase
}
