package com.dogeby.core.data.repository

import com.dogeby.reliccalculator.core.model.GameTextLanguage
import com.dogeby.reliccalculator.core.model.hoyo.Character
import com.dogeby.reliccalculator.core.model.hoyo.index.AffixData
import com.dogeby.reliccalculator.core.model.hoyo.index.CharacterInfo
import com.dogeby.reliccalculator.core.model.hoyo.index.ElementInfo
import com.dogeby.reliccalculator.core.model.hoyo.index.LightConeInfo
import com.dogeby.reliccalculator.core.model.hoyo.index.PathInfo
import com.dogeby.reliccalculator.core.model.hoyo.index.PropertyInfo
import com.dogeby.reliccalculator.core.model.hoyo.index.RelicInfo
import com.dogeby.reliccalculator.core.model.hoyo.index.RelicSetInfo
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

    suspend fun calculateCharacterScore(
        character: Character,
        preset: Preset,
    ): Result<CharacterReport>

    suspend fun updateGameInfoInDb(lang: GameTextLanguage): Result<Unit>
}
