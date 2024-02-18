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
import com.dogeby.core.database.model.hoyo.index.toAffixData
import com.dogeby.core.database.model.hoyo.index.toCharacterInfo
import com.dogeby.core.database.model.hoyo.index.toCharacterInfoWithDetails
import com.dogeby.core.database.model.hoyo.index.toElementInfo
import com.dogeby.core.database.model.hoyo.index.toLightConeInfo
import com.dogeby.core.database.model.hoyo.index.toPathInfo
import com.dogeby.core.database.model.hoyo.index.toPropertyInfo
import com.dogeby.core.database.model.hoyo.index.toRelicInfo
import com.dogeby.core.database.model.hoyo.index.toRelicSetInfo
import com.dogeby.game.rating.CharacterRelicCalculator
import com.dogeby.game.resource.GameResDataSource
import com.dogeby.reliccalculator.core.model.GameTextLanguage
import com.dogeby.reliccalculator.core.model.hoyo.Character
import com.dogeby.reliccalculator.core.model.hoyo.index.AffixData
import com.dogeby.reliccalculator.core.model.hoyo.index.CharacterInfo
import com.dogeby.reliccalculator.core.model.hoyo.index.CharacterInfoWithDetails
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

@Singleton
class GameRepositoryImpl @Inject constructor(
    private val gameResDataSource: GameResDataSource,
    private val characterRelicCalculator: CharacterRelicCalculator,
    private val gameInfoDao: GameInfoDao,
) : GameRepository {

    override val charactersInfo: Flow<Map<String, CharacterInfo>> =
        gameInfoDao.getCharactersInfo().map { charactersInfo ->
            charactersInfo.associateBy(
                keySelector = { it.id },
                valueTransform = { it.toCharacterInfo() },
            )
        }

    override val elementsInfo: Flow<Map<String, ElementInfo>> =
        gameInfoDao.getElementsInfo().map { elementsInfo ->
            elementsInfo.associateBy(
                keySelector = { it.id },
                valueTransform = { it.toElementInfo() },
            )
        }

    override val pathsInfo: Flow<Map<String, PathInfo>> =
        gameInfoDao.getPathsInfo().map { pathsInfo ->
            pathsInfo.associateBy(
                keySelector = { it.id },
                valueTransform = { it.toPathInfo() },
            )
        }

    override val lightConesInfo: Flow<Map<String, LightConeInfo>> =
        gameInfoDao.getLightConesInfo().map { lightConesInfo ->
            lightConesInfo.associateBy(
                keySelector = { it.id },
                valueTransform = { it.toLightConeInfo() },
            )
        }

    override val propertiesInfo: Flow<Map<String, PropertyInfo>> =
        gameInfoDao.getPropertiesInfo().map { propertiesInfo ->
            propertiesInfo.associateBy(
                keySelector = { it.type },
                valueTransform = { it.toPropertyInfo() },
            )
        }

    override val relicSetsInfo: Flow<Map<String, RelicSetInfo>> =
        gameInfoDao.getRelicSetsInfo().map { relicSetsInfo ->
            relicSetsInfo.associateBy(
                keySelector = { it.id },
                valueTransform = { it.toRelicSetInfo() },
            )
        }

    override val relicsInfo: Flow<Map<String, RelicInfo>> =
        gameInfoDao.getRelicsInfo().map { relicsInfo ->
            relicsInfo.associateBy(
                keySelector = { it.id },
                valueTransform = { it.toRelicInfo() },
            )
        }

    override val relicAffixesInfo: Flow<Map<String, AffixData>> =
        gameInfoDao.getAffixesData().map { affixesData ->
            affixesData.associateBy(
                keySelector = { it.id },
                valueTransform = { it.toAffixData() },
            )
        }

    override val charactersInfoWithDetails: Flow<List<CharacterInfoWithDetails>> =
        gameInfoDao.getCharactersInfoWithDetails().map { charactersInfoWithDetails ->
            charactersInfoWithDetails.map { it.toCharacterInfoWithDetails() }
        }

    override suspend fun calculateCharacterScore(
        character: Character,
        preset: Preset,
    ): Result<CharacterReport> = runCatching {
        characterRelicCalculator.calculateCharacterScore(
            character = character,
            preset = preset,
            relicsInfo = relicsInfo.first(),
            subAffixesData = relicAffixesInfo.first(),
        )
    }

    override suspend fun updateGameInfoInDb(lang: GameTextLanguage): Result<Unit> = runCatching {
        val failInfoNames = mutableListOf<String>()

        gameResDataSource.getElements(lang).getOrNull()?.values?.let { elements ->
            gameInfoDao.upsertElementsInfo(elements.map { it.toElementInfoEntity() }.toSet())
        } ?: { failInfoNames.add("elements") }

        gameResDataSource.getPaths(lang).getOrNull()?.values?.let { paths ->
            gameInfoDao.upsertPathsInfo(paths.map { it.toPathInfoEntity() }.toSet())
        } ?: { failInfoNames.add("paths") }

        gameResDataSource.getCharacters(lang).getOrNull()?.values?.let { characters ->
            gameInfoDao.upsertCharactersInfo(characters.map { it.toCharacterInfoEntity() }.toSet())
        } ?: { failInfoNames.add("characters") }

        gameResDataSource.getLightCones(lang).getOrNull()?.values?.let { lightCones ->
            gameInfoDao.upsertLightConesInfo(lightCones.map { it.toLightConeInfoEntity() }.toSet())
        } ?: { failInfoNames.add("lightCones") }

        gameResDataSource.getProperties(lang).getOrNull()?.values?.let { properties ->
            gameInfoDao.upsertPropertiesInfo(properties.map { it.toPropertyInfoEntity() }.toSet())
        } ?: { failInfoNames.add("properties") }

        gameResDataSource.getRelics(lang).getOrNull()?.values?.let { relics ->
            gameInfoDao.upsertRelicsInfo(relics.map { it.toRelicInfoEntity() }.toSet())
        } ?: { failInfoNames.add("relics") }

        gameResDataSource.getRelicSets(lang).getOrNull()?.values?.let { relicSets ->
            gameInfoDao.upsertRelicSetsInfo(relicSets.map { it.toRelicSetInfoEntity() }.toSet())
        } ?: { failInfoNames.add("relicSets") }

        gameResDataSource.getRelicMainAffixes(lang).getOrNull()?.values?.let { mainAffixData ->
            gameInfoDao.upsertAffixesData(mainAffixData.map { it.toAffixDataEntity() }.toSet())
        } ?: { failInfoNames.add("mainAffixData") }

        gameResDataSource.getRelicSubAffixes(lang).getOrNull()?.values?.let { subAffixData ->
            gameInfoDao.upsertAffixesData(subAffixData.map { it.toAffixDataEntity() }.toSet())
        } ?: { failInfoNames.add("subAffixData") }

        if (failInfoNames.isNotEmpty()) {
            failInfoNames.joinTo(StringBuilder("update failed: "))
            throw IOException()
        }
    }
}
