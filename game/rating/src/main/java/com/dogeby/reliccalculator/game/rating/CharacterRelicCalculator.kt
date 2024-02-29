package com.dogeby.reliccalculator.game.rating

import com.dogeby.reliccalculator.core.model.mihomo.Character
import com.dogeby.reliccalculator.core.model.mihomo.Relic
import com.dogeby.reliccalculator.core.model.mihomo.SubAffix
import com.dogeby.reliccalculator.core.model.mihomo.index.AffixData
import com.dogeby.reliccalculator.core.model.mihomo.index.AffixInfo
import com.dogeby.reliccalculator.core.model.mihomo.index.RelicInfo
import com.dogeby.reliccalculator.core.model.mihomo.index.RelicPiece
import com.dogeby.reliccalculator.core.model.preset.AttrComparison
import com.dogeby.reliccalculator.core.model.preset.Preset
import com.dogeby.reliccalculator.core.model.report.AffixCount
import com.dogeby.reliccalculator.core.model.report.AffixReport
import com.dogeby.reliccalculator.core.model.report.AttrComparisonReport
import com.dogeby.reliccalculator.core.model.report.CharacterReport
import com.dogeby.reliccalculator.core.model.report.RelicReport

interface CharacterRelicCalculator {

    fun calculateSubAffixScore(
        subAffix: SubAffix,
        subAffixInfoMap: Map<String, AffixInfo>,
    ): Float

    fun calculateMainAffixReport(
        piece: RelicPiece,
        affixType: String,
        preset: Preset,
    ): AffixReport

    fun calculateRelicScore(
        relic: Relic,
        preset: Preset,
        relicInfo: RelicInfo,
        subAffixDataMap: Map<String, AffixData>,
    ): RelicReport

    fun calculateAttrComparison(
        character: Character,
        attrComparison: AttrComparison,
    ): AttrComparisonReport?

    fun countValidAffixes(
        character: Character,
        preset: Preset,
    ): List<AffixCount>

    fun calculateCharacterScore(
        character: Character,
        preset: Preset,
        relicInfoMap: Map<String, RelicInfo>,
        subAffixDataMap: Map<String, AffixData>,
    ): CharacterReport
}
