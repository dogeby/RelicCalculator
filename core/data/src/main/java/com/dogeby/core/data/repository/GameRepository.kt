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

    fun getCharacters(): Flow<Map<String, CharacterInfo>>

    fun getElements(): Flow<Map<String, ElementInfo>>

    fun getLightCones(): Flow<Map<String, LightConeInfo>>

    fun getPaths(): Flow<Map<String, PathInfo>>

    fun getProperties(): Flow<Map<String, PropertyInfo>>

    fun getRelicSets(): Flow<Map<String, RelicSetInfo>>

    fun getRelics(): Flow<Map<String, RelicInfo>>

    fun getRelicAffixes(): Flow<Map<String, AffixData>>

    suspend fun calculateCharacterScore(
        character: Character,
        preset: Preset,
    ): Result<CharacterReport>

    suspend fun updateGameInfoInDb(lang: GameTextLanguage): Result<Unit>
}
