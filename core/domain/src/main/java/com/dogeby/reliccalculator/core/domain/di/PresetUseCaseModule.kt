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
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
abstract class PresetUseCaseModule {

    @Binds
    @Singleton
    abstract fun bindsGetPresetWithDetailsByIdUseCase(
        getPresetWithDetailsByIdUseCaseImpl: GetPresetWithDetailsByIdUseCaseImpl,
    ): GetPresetWithDetailsByIdUseCase

    @Binds
    @Singleton
    abstract fun bindsGetAttrComparisonWithInfoListUseCase(
        getAttrComparisonWithInfoListUseCaseImpl: GetAttrComparisonWithInfoListUseCaseImpl,
    ): GetAttrComparisonWithInfoListUseCase

    @Binds
    @Singleton
    abstract fun bindsGetMainAffixWeightWithInfoUseCase(
        getMainAffixWeightWithInfoUseCaseImpl: GetMainAffixWeightWithInfoUseCaseImpl,
    ): GetMainAffixWeightWithInfoUseCase

    @Binds
    @Singleton
    abstract fun bindsGetSubAffixWeightWithInfoUseCase(
        getSubAffixWeightWithInfoUseCaseImpl: GetSubAffixWeightWithInfoUseCaseImpl,
    ): GetSubAffixWeightWithInfoUseCase

    @Binds
    @Singleton
    abstract fun UpdatePresetUseCase(
        updatePresetUseCaseImpl: UpdatePresetUseCaseImpl,
    ): UpdatePresetUseCase
}
