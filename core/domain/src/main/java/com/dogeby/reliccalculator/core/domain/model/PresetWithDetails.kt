package com.dogeby.reliccalculator.core.domain.model

import com.dogeby.reliccalculator.core.model.hoyo.index.CharacterInfo
import com.dogeby.reliccalculator.core.model.hoyo.index.ElementInfo
import com.dogeby.reliccalculator.core.model.hoyo.index.PathInfo
import com.dogeby.reliccalculator.core.model.hoyo.index.RelicPiece
import com.dogeby.reliccalculator.core.model.hoyo.index.RelicSetInfo

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
