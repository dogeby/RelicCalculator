package com.dogeby.game.resource

import android.content.Context
import com.dogeby.core.common.dispatcher.Dispatcher
import com.dogeby.core.common.dispatcher.RcDispatchers
import com.dogeby.reliccalculator.core.model.GameTextLanguage
import com.dogeby.reliccalculator.core.model.hoyo.index.AffixData
import com.dogeby.reliccalculator.core.model.hoyo.index.CharacterInfo
import com.dogeby.reliccalculator.core.model.hoyo.index.ElementInfo
import com.dogeby.reliccalculator.core.model.hoyo.index.LightConeInfo
import com.dogeby.reliccalculator.core.model.hoyo.index.PathInfo
import com.dogeby.reliccalculator.core.model.hoyo.index.PropertyInfo
import com.dogeby.reliccalculator.core.model.hoyo.index.RelicInfo
import com.dogeby.reliccalculator.core.model.hoyo.index.RelicSetInfo
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

@Singleton
class GameResDataSourceImpl @Inject constructor(
    private val dataJson: Json,
    @ApplicationContext private val context: Context,
    @Dispatcher(RcDispatchers.IO) private val dispatcher: CoroutineDispatcher,
) : GameResDataSource {

    override suspend fun getCharacters(lang: GameTextLanguage): Result<Map<String, CharacterInfo>> =
        runCatching {
            dataJson.decodeFromString<Map<String, CharacterInfo>>(
                getIndexJson(CHARACTERS_FILE_NAME, lang).getOrThrow(),
            )
        }

    override suspend fun getElements(lang: GameTextLanguage): Result<Map<String, ElementInfo>> =
        runCatching {
            dataJson.decodeFromString<Map<String, ElementInfo>>(
                getIndexJson(ELEMENTS_FILE_NAME, lang).getOrThrow(),
            )
        }

    override suspend fun getLightCones(lang: GameTextLanguage): Result<Map<String, LightConeInfo>> =
        runCatching {
            dataJson.decodeFromString<Map<String, LightConeInfo>>(
                getIndexJson(LIGHT_CONES_FILE_NAME, lang).getOrThrow(),
            )
        }

    override suspend fun getPaths(lang: GameTextLanguage): Result<Map<String, PathInfo>> =
        runCatching {
            dataJson.decodeFromString<Map<String, PathInfo>>(
                getIndexJson(PATHS_FILE_NAME, lang).getOrThrow(),
            )
        }

    override suspend fun getProperties(lang: GameTextLanguage): Result<Map<String, PropertyInfo>> =
        runCatching {
            dataJson.decodeFromString<Map<String, PropertyInfo>>(
                getIndexJson(PROPERTIES_FILE_NAME, lang).getOrThrow(),
            )
        }

    override suspend fun getRelicSets(lang: GameTextLanguage): Result<Map<String, RelicSetInfo>> =
        runCatching {
            dataJson.decodeFromString<Map<String, RelicSetInfo>>(
                getIndexJson(RELIC_SETS_FILE_NAME, lang).getOrThrow(),
            )
        }

    override suspend fun getRelics(lang: GameTextLanguage): Result<Map<String, RelicInfo>> =
        runCatching {
            dataJson.decodeFromString<Map<String, RelicInfo>>(
                getIndexJson(RELICS_FILE_NAME, lang).getOrThrow(),
            )
        }

    override suspend fun getRelicMainAffixes(
        lang: GameTextLanguage,
    ): Result<Map<String, AffixData>> = runCatching {
        dataJson.decodeFromString<Map<String, AffixData>>(
            getIndexJson(RELIC_MAIN_AFFIXES_FILE_NAME, lang).getOrThrow(),
        )
    }

    override suspend fun getRelicSubAffixes(
        lang: GameTextLanguage,
    ): Result<Map<String, AffixData>> = runCatching {
        dataJson.decodeFromString<Map<String, AffixData>>(
            getIndexJson(RELIC_SUB_AFFIXES_FILE_NAME, lang).getOrThrow(),
        )
    }

    private suspend fun getIndexJson(
        fileName: String,
        lang: GameTextLanguage,
    ): Result<String> = runCatching {
        withContext(dispatcher) {
            context.assets
                .open("$INDEX_PATH${lang.code}/$fileName")
                .bufferedReader()
                .readText()
        }
    }

    private companion object {
        const val INDEX_PATH = "game/index_min/"

        const val CHARACTERS_FILE_NAME = "characters.json"
        const val ELEMENTS_FILE_NAME = "elements.json"
        const val LIGHT_CONES_FILE_NAME = "light_cones.json"
        const val PATHS_FILE_NAME = "paths.json"
        const val PROPERTIES_FILE_NAME = "properties.json"
        const val RELIC_SETS_FILE_NAME = "relic_sets.json"
        const val RELICS_FILE_NAME = "relics.json"
        const val RELIC_MAIN_AFFIXES_FILE_NAME = "relic_main_affixes.json"
        const val RELIC_SUB_AFFIXES_FILE_NAME = "relic_sub_affixes.json"
    }
}
