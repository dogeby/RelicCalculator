package com.dogeby.reliccalculator.core.data.repository

import com.dogeby.reliccalculator.core.data.model.toAffixDataEntity
import com.dogeby.reliccalculator.core.data.model.toCharacterInfoEntity
import com.dogeby.reliccalculator.core.data.model.toElementInfoEntity
import com.dogeby.reliccalculator.core.data.model.toLightConeInfoEntity
import com.dogeby.reliccalculator.core.data.model.toPathInfoEntity
import com.dogeby.reliccalculator.core.data.model.toPropertyInfoEntity
import com.dogeby.reliccalculator.core.data.model.toRelicInfoEntity
import com.dogeby.reliccalculator.core.data.model.toRelicSetInfoEntity
import com.dogeby.reliccalculator.core.database.dao.GameInfoDao
import com.dogeby.reliccalculator.core.database.model.hoyo.index.toAffixData
import com.dogeby.reliccalculator.core.database.model.hoyo.index.toCharacterInfo
import com.dogeby.reliccalculator.core.database.model.hoyo.index.toCharacterInfoWithDetails
import com.dogeby.reliccalculator.core.database.model.hoyo.index.toElementInfo
import com.dogeby.reliccalculator.core.database.model.hoyo.index.toLightConeInfo
import com.dogeby.reliccalculator.core.database.model.hoyo.index.toPathInfo
import com.dogeby.reliccalculator.core.database.model.hoyo.index.toPropertyInfo
import com.dogeby.reliccalculator.core.database.model.hoyo.index.toRelicInfo
import com.dogeby.reliccalculator.core.database.model.hoyo.index.toRelicSetInfo
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
import com.dogeby.reliccalculator.game.rating.CharacterRelicCalculator
import com.dogeby.reliccalculator.game.resource.GameResDataSource
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

    override val characterInfoMap: Flow<Map<String, CharacterInfo>> =
        gameInfoDao.getCharacterInfoList().map { characterInfoList ->
            characterInfoList.associateBy(
                keySelector = { it.id },
                valueTransform = { it.toCharacterInfo() },
            )
        }

    override val elementInfoMap: Flow<Map<String, ElementInfo>> =
        gameInfoDao.getElementInfoList().map { elementInfoList ->
            elementInfoList.associateBy(
                keySelector = { it.id },
                valueTransform = { it.toElementInfo() },
            )
        }

    override val pathInfoMap: Flow<Map<String, PathInfo>> =
        gameInfoDao.getPathInfoList().map { pathInfoList ->
            pathInfoList.associateBy(
                keySelector = { it.id },
                valueTransform = { it.toPathInfo() },
            )
        }

    override val lightConeInfoMap: Flow<Map<String, LightConeInfo>> =
        gameInfoDao.getLightConeInfoList().map { lightConeInfoList ->
            lightConeInfoList.associateBy(
                keySelector = { it.id },
                valueTransform = { it.toLightConeInfo() },
            )
        }

    override val propertyInfoMap: Flow<Map<String, PropertyInfo>> =
        gameInfoDao.getPropertyInfoList().map { propertyInfoList ->
            propertyInfoList.associateBy(
                keySelector = { it.type },
                valueTransform = { it.toPropertyInfo() },
            )
        }

    override val relicSetInfoMap: Flow<Map<String, RelicSetInfo>> =
        gameInfoDao.getRelicSetInfoList().map { relicSetInfoList ->
            relicSetInfoList.associateBy(
                keySelector = { it.id },
                valueTransform = { it.toRelicSetInfo() },
            )
        }

    override val relicInfoMap: Flow<Map<String, RelicInfo>> =
        gameInfoDao.getRelicInfoList().map { relicInfoList ->
            relicInfoList.associateBy(
                keySelector = { it.id },
                valueTransform = { it.toRelicInfo() },
            )
        }

    override val relicAffixInfoMap: Flow<Map<String, AffixData>> =
        gameInfoDao.getAffixDataList().map { affixDataList ->
            affixDataList.associateBy(
                keySelector = { it.id },
                valueTransform = { it.toAffixData() },
            )
        }

    override val characterInfoWithDetailsList: Flow<List<CharacterInfoWithDetails>> =
        gameInfoDao.getCharacterInfoWithDetailsList().map { characterInfoListWithDetails ->
            characterInfoListWithDetails.map { it.toCharacterInfoWithDetails() }
        }

    override suspend fun calculateCharacterScore(
        character: Character,
        preset: Preset,
    ): Result<CharacterReport> = runCatching {
        characterRelicCalculator.calculateCharacterScore(
            character = character,
            preset = preset,
            relicInfoMap = relicInfoMap.first(),
            subAffixDataMap = relicAffixInfoMap.first(),
        )
    }

    override suspend fun updateGameInfoInDb(lang: GameTextLanguage): Result<Unit> = runCatching {
        val failInfoNames = mutableListOf<String>()

        gameResDataSource.getElements(lang).getOrNull()?.values?.let { elements ->
            gameInfoDao.upsertElementInfoSet(elements.map { it.toElementInfoEntity() }.toSet())
        } ?: { failInfoNames.add("elements") }

        gameResDataSource.getPaths(lang).getOrNull()?.values?.let { paths ->
            gameInfoDao.upsertPathInfoSet(paths.map { it.toPathInfoEntity() }.toSet())
        } ?: { failInfoNames.add("paths") }

        gameResDataSource.getCharacters(lang).getOrNull()?.values?.let { characters ->
            gameInfoDao.upsertCharacterInfoSet(
                characters.map { it.toCharacterInfoEntity() }.toSet(),
            )
        } ?: { failInfoNames.add("characters") }

        gameResDataSource.getLightCones(lang).getOrNull()?.values?.let { lightCones ->
            gameInfoDao.upsertLightConeInfoSet(
                lightCones.map { it.toLightConeInfoEntity() }.toSet(),
            )
        } ?: { failInfoNames.add("lightCones") }

        gameResDataSource.getProperties(lang).getOrNull()?.values?.let { properties ->
            gameInfoDao.upsertPropertyInfoSet(properties.map { it.toPropertyInfoEntity() }.toSet())
        } ?: { failInfoNames.add("properties") }

        gameResDataSource.getRelics(lang).getOrNull()?.values?.let { relics ->
            gameInfoDao.upsertRelicInfoSet(relics.map { it.toRelicInfoEntity() }.toSet())
        } ?: { failInfoNames.add("relics") }

        gameResDataSource.getRelicSets(lang).getOrNull()?.values?.let { relicSets ->
            gameInfoDao.upsertRelicSetInfoSet(relicSets.map { it.toRelicSetInfoEntity() }.toSet())
        } ?: { failInfoNames.add("relicSets") }

        gameResDataSource.getRelicMainAffixes(lang).getOrNull()?.values?.let { mainAffixData ->
            gameInfoDao.upsertAffixDataSet(mainAffixData.map { it.toAffixDataEntity() }.toSet())
        } ?: { failInfoNames.add("mainAffixData") }

        gameResDataSource.getRelicSubAffixes(lang).getOrNull()?.values?.let { subAffixData ->
            gameInfoDao.upsertAffixDataSet(subAffixData.map { it.toAffixDataEntity() }.toSet())
        } ?: { failInfoNames.add("subAffixData") }

        if (failInfoNames.isNotEmpty()) {
            failInfoNames.joinTo(StringBuilder("update failed: "))
            throw IOException()
        }
    }
}
