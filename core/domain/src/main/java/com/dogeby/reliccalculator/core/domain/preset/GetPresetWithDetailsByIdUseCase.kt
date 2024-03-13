package com.dogeby.reliccalculator.core.domain.preset

import com.dogeby.reliccalculator.core.data.repository.GameRepository
import com.dogeby.reliccalculator.core.data.repository.PresetRepository
import com.dogeby.reliccalculator.core.domain.model.AttrComparisonWithInfo
import com.dogeby.reliccalculator.core.domain.model.PresetWithDetails
import com.dogeby.reliccalculator.core.domain.model.mapNotNullToAffixWeightsWithInfo
import com.dogeby.reliccalculator.core.domain.model.toPresetWithDetails
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetPresetWithDetailsByIdUseCase @Inject constructor(
    private val presetRepository: PresetRepository,
    private val gameRepository: GameRepository,
) {

    operator fun invoke(id: String): Flow<Result<PresetWithDetails>> {
        return combine(
            presetRepository.getPresets(setOf(id)),
            gameRepository.characterInfoWithDetailsList,
            gameRepository.relicSetInfoMap,
            gameRepository.propertyInfoMap,
        ) { presets, characterInfoWithDetailsList, relicSetInfoMap, propertyInfoMap ->
            runCatching {
                val characterInfoWithDetails = characterInfoWithDetailsList.find {
                    it.characterInfo.id == id
                } ?: throw NullPointerException()
                val preset = presets.first()
                preset.toPresetWithDetails(
                    characterInfo = characterInfoWithDetails.characterInfo,
                    pathInfo = characterInfoWithDetails.pathInfo,
                    elementInfo = characterInfoWithDetails.elementInfo,
                    relicSets = preset.relicSetIds.mapNotNull { relicSetInfoMap[it] },
                    pieceMainAffixWeightsWithInfo = preset
                        .pieceMainAffixWeights
                        .mapValues { pieceMainAffixWeight ->
                            pieceMainAffixWeight.value.mapNotNullToAffixWeightsWithInfo(
                                propertyInfoMap,
                            )
                        },
                    subAffixWeightsWithInfo = preset
                        .subAffixWeights
                        .mapNotNullToAffixWeightsWithInfo(propertyInfoMap),
                    attrComparisons = preset.attrComparisons.mapNotNull { attrComparison ->
                        propertyInfoMap[attrComparison.type]?.let {
                            AttrComparisonWithInfo(
                                attrComparison = attrComparison,
                                propertyInfo = it,
                            )
                        }
                    },
                )
            }
        }
    }
}
