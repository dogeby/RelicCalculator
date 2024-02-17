package com.dogeby.core.data.repository

import com.dogeby.core.data.model.toAffixDataEntity
import com.dogeby.core.data.model.toCharacterInfoEntity
import com.dogeby.core.data.model.toElementInfoEntity
import com.dogeby.core.data.model.toLightConeInfoEntity
import com.dogeby.core.data.model.toPathInfoEntity
import com.dogeby.core.data.model.toPropertyInfoEntity
import com.dogeby.core.data.model.toRelicInfoEntity
import com.dogeby.core.data.model.toRelicSetInfoEntity
import com.dogeby.core.database.dao.GameInfoDao
import com.dogeby.game.rating.CharacterRelicCalculator
import com.dogeby.game.resource.GameResDataSource
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
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameRepositoryImpl @Inject constructor(
    private val gameResDataSource: GameResDataSource,
    private val characterRelicCalculator: CharacterRelicCalculator,
    private val gameInfoDao: GameInfoDao,
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

    override suspend fun calculateCharacterScore(
        character: Character,
        preset: Preset,
        lang: GameTextLanguage,
    ): Result<CharacterReport> = runCatching {
        characterRelicCalculator.calculateCharacterScore(
            character = character,
            preset = preset,
            relicsInfo = getRelics(lang).getOrThrow(),
            subAffixesData = getRelicSubAffixes(lang).getOrThrow(),
        )
    }

    override suspend fun updateGameInfoInDb(lang: GameTextLanguage): Result<Unit> = runCatching {
        val failInfoNames = mutableListOf<String>()

        getElements(lang).getOrNull()?.values?.let { elements ->
            gameInfoDao.upsertElementsInfo(elements.map { it.toElementInfoEntity() }.toSet())
        } ?: { failInfoNames.add("elements") }

        getPaths(lang).getOrNull()?.values?.let { paths ->
            gameInfoDao.upsertPathsInfo(paths.map { it.toPathInfoEntity() }.toSet())
        } ?: { failInfoNames.add("paths") }

        getCharacters(lang).getOrNull()?.values?.let { characters ->
            gameInfoDao.upsertCharactersInfo(characters.map { it.toCharacterInfoEntity() }.toSet())
        } ?: { failInfoNames.add("characters") }

        getLightCones(lang).getOrNull()?.values?.let { lightCones ->
            gameInfoDao.upsertLightConesInfo(lightCones.map { it.toLightConeInfoEntity() }.toSet())
        } ?: { failInfoNames.add("lightCones") }

        getProperties(lang).getOrNull()?.values?.let { properties ->
            gameInfoDao.upsertPropertiesInfo(properties.map { it.toPropertyInfoEntity() }.toSet())
        } ?: { failInfoNames.add("properties") }

        getRelics(lang).getOrNull()?.values?.let { relics ->
            gameInfoDao.upsertRelicsInfo(relics.map { it.toRelicInfoEntity() }.toSet())
        } ?: { failInfoNames.add("relics") }

        getRelicSets(lang).getOrNull()?.values?.let { relicSets ->
            gameInfoDao.upsertRelicSetsInfo(relicSets.map { it.toRelicSetInfoEntity() }.toSet())
        } ?: { failInfoNames.add("relicSets") }

        getRelicMainAffixes(lang).getOrNull()?.values?.let { mainAffixData ->
            gameInfoDao.upsertAffixesData(mainAffixData.map { it.toAffixDataEntity() }.toSet())
        } ?: { failInfoNames.add("mainAffixData") }

        getRelicSubAffixes(lang).getOrNull()?.values?.let { subAffixData ->
            gameInfoDao.upsertAffixesData(subAffixData.map { it.toAffixDataEntity() }.toSet())
        } ?: { failInfoNames.add("subAffixData") }

        if (failInfoNames.isNotEmpty()) {
            failInfoNames.joinTo(StringBuilder("update failed: "))
            throw IOException()
        }
    }
}
