package com.dogeby.reliccalculator.core.domain.fake

import com.dogeby.reliccalculator.core.domain.model.AffixWeightWithInfo
import com.dogeby.reliccalculator.core.domain.model.AttrComparisonWithInfo
import com.dogeby.reliccalculator.core.domain.model.PresetWithDetails
import com.dogeby.reliccalculator.core.domain.preset.GetPresetWithDetailsByIdUseCase
import com.dogeby.reliccalculator.core.model.mihomo.index.RelicPiece
import com.dogeby.reliccalculator.core.model.mihomo.index.sampleCharacterInfo
import com.dogeby.reliccalculator.core.model.mihomo.index.sampleElementInfo
import com.dogeby.reliccalculator.core.model.mihomo.index.samplePathInfo
import com.dogeby.reliccalculator.core.model.mihomo.index.samplePropertyInfo
import com.dogeby.reliccalculator.core.model.mihomo.index.sampleRelicSetInfo
import com.dogeby.reliccalculator.core.model.preset.sampleAttrComparison
import com.dogeby.reliccalculator.core.model.preset.sampleMainAffixWeight
import com.dogeby.reliccalculator.core.model.preset.sampleSubAffixWeight
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.jetbrains.annotations.TestOnly

@TestOnly
class FakeGetPresetWithDetailsByIdUseCase : GetPresetWithDetailsByIdUseCase {

    override operator fun invoke(id: String): Flow<Result<PresetWithDetails>> {
        return flow {
            val result = Result.success(
                PresetWithDetails(
                    characterId = id,
                    characterInfo = sampleCharacterInfo,
                    pathInfo = samplePathInfo,
                    elementInfo = sampleElementInfo,
                    relicSets = listOf(sampleRelicSetInfo),
                    pieceMainAffixWeightsWithInfo = mapOf(
                        RelicPiece.HEAD to listOf(
                            AffixWeightWithInfo(
                                affixWeight = sampleMainAffixWeight,
                                propertyInfo = samplePropertyInfo,
                            ),
                        ),
                    ),
                    subAffixWeightsWithInfo = listOf(
                        AffixWeightWithInfo(
                            affixWeight = sampleSubAffixWeight,
                            propertyInfo = samplePropertyInfo,
                        ),
                    ),
                    isAutoUpdate = true,
                    attrComparisons = listOf(
                        AttrComparisonWithInfo(
                            attrComparison = sampleAttrComparison,
                            propertyInfo = samplePropertyInfo,
                        ),
                    ),
                ),
            )
            emit(result)
        }
    }
}
