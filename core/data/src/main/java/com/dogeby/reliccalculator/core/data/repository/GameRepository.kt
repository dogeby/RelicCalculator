package com.dogeby.reliccalculator.core.data.repository

import com.dogeby.reliccalculator.core.model.GameTextLanguage
import com.dogeby.reliccalculator.core.model.mihomo.Character
import com.dogeby.reliccalculator.core.model.mihomo.index.AffixData
import com.dogeby.reliccalculator.core.model.mihomo.index.CharacterInfo
import com.dogeby.reliccalculator.core.model.mihomo.index.CharacterInfoWithDetails
import com.dogeby.reliccalculator.core.model.mihomo.index.ElementInfo
import com.dogeby.reliccalculator.core.model.mihomo.index.LightConeInfo
import com.dogeby.reliccalculator.core.model.mihomo.index.PathInfo
import com.dogeby.reliccalculator.core.model.mihomo.index.PropertyInfo
import com.dogeby.reliccalculator.core.model.mihomo.index.RelicInfo
import com.dogeby.reliccalculator.core.model.mihomo.index.RelicSetInfo
import com.dogeby.reliccalculator.core.model.preset.Preset
import com.dogeby.reliccalculator.core.model.report.CharacterReport
import kotlinx.coroutines.flow.Flow

interface GameRepository {

    val characterInfoMap: Flow<Map<String, CharacterInfo>>

    val elementInfoMap: Flow<Map<String, ElementInfo>>

    val pathInfoMap: Flow<Map<String, PathInfo>>

    val lightConeInfoMap: Flow<Map<String, LightConeInfo>>

    val propertyInfoMap: Flow<Map<String, PropertyInfo>>

    val relicSetInfoMap: Flow<Map<String, RelicSetInfo>>

    val relicInfoMap: Flow<Map<String, RelicInfo>>

    val relicAffixInfoMap: Flow<Map<String, AffixData>>

    val characterInfoWithDetailsList: Flow<List<CharacterInfoWithDetails>>

    suspend fun calculateCharacterScore(
        character: Character,
        preset: Preset,
    ): Result<CharacterReport>

    suspend fun updateGameInfoInDb(lang: GameTextLanguage): Result<Unit>
}
