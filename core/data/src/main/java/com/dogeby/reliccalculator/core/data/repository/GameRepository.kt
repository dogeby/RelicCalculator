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

    val charactersInfo: Flow<Map<String, CharacterInfo>>

    val elementsInfo: Flow<Map<String, ElementInfo>>

    val pathsInfo: Flow<Map<String, PathInfo>>

    val lightConesInfo: Flow<Map<String, LightConeInfo>>

    val propertiesInfo: Flow<Map<String, PropertyInfo>>

    val relicSetsInfo: Flow<Map<String, RelicSetInfo>>

    val relicsInfo: Flow<Map<String, RelicInfo>>

    val relicAffixesInfo: Flow<Map<String, AffixData>>

    val charactersInfoWithDetails: Flow<List<CharacterInfoWithDetails>>

    suspend fun calculateCharacterScore(
        character: Character,
        preset: Preset,
    ): Result<CharacterReport>

    suspend fun updateGameInfoInDb(lang: GameTextLanguage): Result<Unit>
}
