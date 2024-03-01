package com.dogeby.reliccalculator.game.resource

import com.dogeby.reliccalculator.core.model.GameTextLanguage
import com.dogeby.reliccalculator.core.model.mihomo.index.AffixData
import com.dogeby.reliccalculator.core.model.mihomo.index.CharacterInfo
import com.dogeby.reliccalculator.core.model.mihomo.index.ElementInfo
import com.dogeby.reliccalculator.core.model.mihomo.index.LightConeInfo
import com.dogeby.reliccalculator.core.model.mihomo.index.PathInfo
import com.dogeby.reliccalculator.core.model.mihomo.index.PropertyInfo
import com.dogeby.reliccalculator.core.model.mihomo.index.RelicInfo
import com.dogeby.reliccalculator.core.model.mihomo.index.RelicSetInfo

interface GameResDataSource {

    suspend fun getCharacters(lang: GameTextLanguage): Result<Map<String, CharacterInfo>>

    suspend fun getElements(lang: GameTextLanguage): Result<Map<String, ElementInfo>>

    suspend fun getLightCones(lang: GameTextLanguage): Result<Map<String, LightConeInfo>>

    suspend fun getPaths(lang: GameTextLanguage): Result<Map<String, PathInfo>>

    suspend fun getProperties(lang: GameTextLanguage): Result<Map<String, PropertyInfo>>

    suspend fun getRelicSets(lang: GameTextLanguage): Result<Map<String, RelicSetInfo>>

    suspend fun getRelics(lang: GameTextLanguage): Result<Map<String, RelicInfo>>

    suspend fun getRelicMainAffixes(lang: GameTextLanguage): Result<Map<String, AffixData>>

    suspend fun getRelicSubAffixes(lang: GameTextLanguage): Result<Map<String, AffixData>>
}
