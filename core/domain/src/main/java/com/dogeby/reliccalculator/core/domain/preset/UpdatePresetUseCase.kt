package com.dogeby.reliccalculator.core.domain.preset

import com.dogeby.reliccalculator.core.data.repository.PresetRepository
import com.dogeby.reliccalculator.core.domain.model.AffixWeightWithInfo
import com.dogeby.reliccalculator.core.domain.model.AttrComparisonWithInfo
import com.dogeby.reliccalculator.core.model.mihomo.index.RelicPiece
import com.dogeby.reliccalculator.core.model.mihomo.index.RelicSetInfo
import com.dogeby.reliccalculator.core.model.preset.Preset
import javax.inject.Inject

interface UpdatePresetUseCase {

    suspend operator fun invoke(
        characterId: String,
        relicSets: List<RelicSetInfo>,
        pieceMainAffixWeights: Map<RelicPiece, List<AffixWeightWithInfo>>,
        subAffixWeights: List<AffixWeightWithInfo>,
        attrComparisons: List<AttrComparisonWithInfo>,
        isAutoUpdate: Boolean = false,
    ): Result<Int>
}

class UpdatePresetUseCaseImpl @Inject constructor(
    private val presetRepository: PresetRepository,
) : UpdatePresetUseCase {

    override suspend operator fun invoke(
        characterId: String,
        relicSets: List<RelicSetInfo>,
        pieceMainAffixWeights: Map<RelicPiece, List<AffixWeightWithInfo>>,
        subAffixWeights: List<AffixWeightWithInfo>,
        attrComparisons: List<AttrComparisonWithInfo>,
        isAutoUpdate: Boolean,
    ): Result<Int> {
        val preset = Preset(
            characterId = characterId,
            relicSetIds = relicSets.map { it.id },
            pieceMainAffixWeights = pieceMainAffixWeights.mapValues { (_, affixWeight) ->
                affixWeight.map { it.affixWeight }
            },
            subAffixWeights = subAffixWeights.map { it.affixWeight },
            isAutoUpdate = isAutoUpdate,
            attrComparisons = attrComparisons.map { it.attrComparison },
        )

        return presetRepository.updatePresets(listOf(preset))
    }
}
