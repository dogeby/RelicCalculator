package com.dogeby.reliccalculator.core.domain.preset

import com.dogeby.reliccalculator.core.data.repository.PresetRepository
import com.dogeby.reliccalculator.core.model.mihomo.index.RelicPiece
import com.dogeby.reliccalculator.core.model.preset.AffixWeight
import com.dogeby.reliccalculator.core.model.preset.AttrComparison
import com.dogeby.reliccalculator.core.model.preset.Preset
import javax.inject.Inject

typealias UpdatedPresetCount = Int

interface UpdatePresetUseCase {

    suspend operator fun invoke(
        characterId: String,
        relicSetIds: List<String>,
        pieceMainAffixWeights: Map<RelicPiece, List<AffixWeight>>,
        subAffixWeights: List<AffixWeight>,
        attrComparisons: List<AttrComparison>,
        isAutoUpdate: Boolean = false,
    ): Result<Int>
}

class UpdatePresetUseCaseImpl @Inject constructor(
    private val presetRepository: PresetRepository,
) : UpdatePresetUseCase {

    override suspend operator fun invoke(
        characterId: String,
        relicSetIds: List<String>,
        pieceMainAffixWeights: Map<RelicPiece, List<AffixWeight>>,
        subAffixWeights: List<AffixWeight>,
        attrComparisons: List<AttrComparison>,
        isAutoUpdate: Boolean,
    ): Result<UpdatedPresetCount> {
        val preset = Preset(
            characterId = characterId,
            relicSetIds = relicSetIds,
            pieceMainAffixWeights = pieceMainAffixWeights,
            subAffixWeights = subAffixWeights,
            isAutoUpdate = isAutoUpdate,
            attrComparisons = attrComparisons,
        )

        return presetRepository.updatePresets(listOf(preset))
    }
}
