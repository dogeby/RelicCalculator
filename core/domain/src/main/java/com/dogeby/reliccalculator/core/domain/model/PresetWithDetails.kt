package com.dogeby.reliccalculator.core.domain.model

import com.dogeby.reliccalculator.core.model.mihomo.index.CharacterInfo
import com.dogeby.reliccalculator.core.model.mihomo.index.ElementInfo
import com.dogeby.reliccalculator.core.model.mihomo.index.PathInfo
import com.dogeby.reliccalculator.core.model.mihomo.index.RelicPiece
import com.dogeby.reliccalculator.core.model.mihomo.index.RelicSetInfo
import com.dogeby.reliccalculator.core.model.preset.Preset

data class PresetWithDetails(
    val characterId: String,
    val characterInfo: CharacterInfo,
    val pathInfo: PathInfo,
    val elementInfo: ElementInfo,
    val relicSets: List<RelicSetInfo>,
    val pieceMainAffixWeightsWithInfo: Map<RelicPiece, List<AffixWeightWithInfo>>,
    val subAffixWeightsWithInfo: List<AffixWeightWithInfo>,
    val isAutoUpdate: Boolean = true,
    val attrComparisons: List<AttrComparisonWithInfo>,
)

fun Preset.toPresetWithDetails(
    characterInfo: CharacterInfo,
    pathInfo: PathInfo,
    elementInfo: ElementInfo,
    relicSets: List<RelicSetInfo>,
    pieceMainAffixWeightsWithInfo: Map<RelicPiece, List<AffixWeightWithInfo>>,
    subAffixWeightsWithInfo: List<AffixWeightWithInfo>,
    attrComparisons: List<AttrComparisonWithInfo>,
) = PresetWithDetails(
    characterId = characterId,
    characterInfo = characterInfo,
    pathInfo = pathInfo,
    elementInfo = elementInfo,
    relicSets = relicSets,
    pieceMainAffixWeightsWithInfo = pieceMainAffixWeightsWithInfo,
    subAffixWeightsWithInfo = subAffixWeightsWithInfo,
    isAutoUpdate = isAutoUpdate,
    attrComparisons = attrComparisons,
)
