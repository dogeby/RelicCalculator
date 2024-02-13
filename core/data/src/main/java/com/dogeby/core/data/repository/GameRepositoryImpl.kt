package com.dogeby.core.data.repository

import com.dogeby.game.resource.GameResDataSource
import com.dogeby.reliccalculator.core.model.GameTextLanguage
import com.dogeby.reliccalculator.core.model.hoyo.index.AffixData
import com.dogeby.reliccalculator.core.model.hoyo.index.CharacterInfo
import com.dogeby.reliccalculator.core.model.hoyo.index.ElementInfo
import com.dogeby.reliccalculator.core.model.hoyo.index.LightConeInfo
import com.dogeby.reliccalculator.core.model.hoyo.index.PathInfo
import com.dogeby.reliccalculator.core.model.hoyo.index.PropertyInfo
import com.dogeby.reliccalculator.core.model.hoyo.index.RelicInfo
import com.dogeby.reliccalculator.core.model.hoyo.index.RelicSetInfo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameRepositoryImpl @Inject constructor(
    private val gameResDataSource: GameResDataSource,
) : GameRepository {

    override suspend fun getCharacters(lang: GameTextLanguage): Result<Map<String, CharacterInfo>> =
        gameResDataSource.getCharacters(lang)

    override suspend fun getElements(lang: GameTextLanguage): Result<Map<String, ElementInfo>> =
        gameResDataSource.getElements(lang)

    override suspend fun getLightCones(lang: GameTextLanguage): Result<Map<String, LightConeInfo>> =
        gameResDataSource.getLightCones(lang)

    override suspend fun getPaths(lang: GameTextLanguage): Result<Map<String, PathInfo>> =
        gameResDataSource.getPaths(lang)

    override suspend fun getProperties(lang: GameTextLanguage): Result<Map<String, PropertyInfo>> =
        gameResDataSource.getProperties(lang)

    override suspend fun getRelicSets(lang: GameTextLanguage): Result<Map<String, RelicSetInfo>> =
        gameResDataSource.getRelicSets(lang)

    override suspend fun getRelics(lang: GameTextLanguage): Result<Map<String, RelicInfo>> =
        gameResDataSource.getRelics(lang)

    override suspend fun getRelicMainAffixes(
        lang: GameTextLanguage,
    ): Result<Map<String, AffixData>> = gameResDataSource.getRelicMainAffixes(lang)

    override suspend fun getRelicSubAffixes(
        lang: GameTextLanguage,
    ): Result<Map<String, AffixData>> = gameResDataSource.getRelicSubAffixes(lang)
}
