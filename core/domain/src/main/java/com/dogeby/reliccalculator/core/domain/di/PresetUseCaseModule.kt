package com.dogeby.reliccalculator.core.domain.di

import com.dogeby.reliccalculator.core.domain.preset.GetAttrComparisonWithInfoListUseCase
import com.dogeby.reliccalculator.core.domain.preset.GetAttrComparisonWithInfoListUseCaseImpl
import com.dogeby.reliccalculator.core.domain.preset.GetMainAffixWeightWithInfoUseCase
import com.dogeby.reliccalculator.core.domain.preset.GetMainAffixWeightWithInfoUseCaseImpl
import com.dogeby.reliccalculator.core.domain.preset.GetPresetWithDetailsByIdUseCase
import com.dogeby.reliccalculator.core.domain.preset.GetPresetWithDetailsByIdUseCaseImpl
import com.dogeby.reliccalculator.core.domain.preset.GetSubAffixWeightWithInfoUseCase
import com.dogeby.reliccalculator.core.domain.preset.GetSubAffixWeightWithInfoUseCaseImpl
import com.dogeby.reliccalculator.core.domain.preset.UpdatePresetUseCase
import com.dogeby.reliccalculator.core.domain.preset.UpdatePresetUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class PresetUseCaseModule {

    @Binds
    @ViewModelScoped
    abstract fun bindsGetPresetWithDetailsByIdUseCase(
        getPresetWithDetailsByIdUseCaseImpl: GetPresetWithDetailsByIdUseCaseImpl,
    ): GetPresetWithDetailsByIdUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindsGetAttrComparisonWithInfoListUseCase(
        getAttrComparisonWithInfoListUseCaseImpl: GetAttrComparisonWithInfoListUseCaseImpl,
    ): GetAttrComparisonWithInfoListUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindsGetMainAffixWeightWithInfoUseCase(
        getMainAffixWeightWithInfoUseCaseImpl: GetMainAffixWeightWithInfoUseCaseImpl,
    ): GetMainAffixWeightWithInfoUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindsGetSubAffixWeightWithInfoUseCase(
        getSubAffixWeightWithInfoUseCaseImpl: GetSubAffixWeightWithInfoUseCaseImpl,
    ): GetSubAffixWeightWithInfoUseCase

    @Binds
    @ViewModelScoped
    abstract fun UpdatePresetUseCase(
        updatePresetUseCaseImpl: UpdatePresetUseCaseImpl,
    ): UpdatePresetUseCase
}
