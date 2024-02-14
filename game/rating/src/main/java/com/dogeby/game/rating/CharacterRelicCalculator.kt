package com.dogeby.game.rating

import com.dogeby.reliccalculator.core.model.hoyo.Character
import com.dogeby.reliccalculator.core.model.hoyo.Relic
import com.dogeby.reliccalculator.core.model.hoyo.SubAffix
import com.dogeby.reliccalculator.core.model.hoyo.index.AffixData
import com.dogeby.reliccalculator.core.model.hoyo.index.AffixInfo
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
        subAffixesInfo: Map<String, AffixInfo>,
    ): Float

    fun calculateMainAffixReport(
        relic: Relic,
        preset: Preset,
    ): AffixReport

    fun calculateRelicScore(
        relic: Relic,
        preset: Preset,
        subAffixesData: Map<String, AffixData>,
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
        subAffixesData: Map<String, AffixData>,
    ): CharacterReport
}
