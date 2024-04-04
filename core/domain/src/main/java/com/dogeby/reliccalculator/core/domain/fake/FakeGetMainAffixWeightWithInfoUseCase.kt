package com.dogeby.reliccalculator.core.domain.fake

import com.dogeby.reliccalculator.core.domain.model.AffixWeightWithInfo
import com.dogeby.reliccalculator.core.domain.preset.GetMainAffixWeightWithInfoUseCase
import com.dogeby.reliccalculator.core.model.mihomo.index.RelicPiece
import com.dogeby.reliccalculator.core.model.mihomo.index.samplePropertyInfo
import com.dogeby.reliccalculator.core.model.preset.sampleMainAffixWeight
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.jetbrains.annotations.TestOnly

@TestOnly
class FakeGetMainAffixWeightWithInfoUseCase : GetMainAffixWeightWithInfoUseCase {

    override operator fun invoke(): Flow<Map<RelicPiece, List<AffixWeightWithInfo>>> {
        return flow {
            emit(
                mapOf(
                    RelicPiece.HEAD to listOf(
                        AffixWeightWithInfo(
                            affixWeight = sampleMainAffixWeight,
                            propertyInfo = samplePropertyInfo,
                        ),
                    ),
                ),
            )
        }
    }
}
