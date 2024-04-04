package com.dogeby.reliccalculator.core.domain.fake

import com.dogeby.reliccalculator.core.domain.model.AffixWeightWithInfo
import com.dogeby.reliccalculator.core.domain.preset.GetSubAffixWeightWithInfoUseCase
import com.dogeby.reliccalculator.core.model.mihomo.index.samplePropertyInfo
import com.dogeby.reliccalculator.core.model.preset.sampleSubAffixWeight
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.jetbrains.annotations.TestOnly

@TestOnly
class FakeGetSubAffixWeightWithInfoUseCase : GetSubAffixWeightWithInfoUseCase {

    override operator fun invoke(): Flow<List<AffixWeightWithInfo>> {
        return flow {
            emit(
                listOf(
                    AffixWeightWithInfo(
                        affixWeight = sampleSubAffixWeight,
                        propertyInfo = samplePropertyInfo,
                    ),
                ),
            )
        }
    }
}
