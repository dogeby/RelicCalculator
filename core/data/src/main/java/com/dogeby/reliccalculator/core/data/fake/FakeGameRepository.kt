package com.dogeby.reliccalculator.core.data.fake

import com.dogeby.reliccalculator.core.data.repository.GameRepository
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
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first
import org.jetbrains.annotations.TestOnly

@TestOnly
class FakeGameRepository : GameRepository {

    private val characterInfoMapFlow: MutableSharedFlow<Map<String, CharacterInfo>> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    private val elementInfoMapFlow: MutableSharedFlow<Map<String, ElementInfo>> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    private val pathInfoMapFlow: MutableSharedFlow<Map<String, PathInfo>> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    private val lightConeInfoMapFlow: MutableSharedFlow<Map<String, LightConeInfo>> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    private val propertyInfoMapFlow: MutableSharedFlow<Map<String, PropertyInfo>> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    private val relicSetInfoMapFlow: MutableSharedFlow<Map<String, RelicSetInfo>> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    private val relicInfoMapFlow: MutableSharedFlow<Map<String, RelicInfo>> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    private val relicAffixInfoMapFlow: MutableSharedFlow<Map<String, AffixData>> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    private val characterInfoWithDetailsListFlow:
        MutableSharedFlow<List<CharacterInfoWithDetails>> = MutableSharedFlow(
            replay = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST,
        )

    private val characterScoreFlow: MutableSharedFlow<Result<CharacterReport>> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    private val gameInfoInDbUpdateResultFlow: MutableSharedFlow<Result<Unit>> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    override val characterInfoMap: Flow<Map<String, CharacterInfo>> = characterInfoMapFlow

    override val elementInfoMap: Flow<Map<String, ElementInfo>> = elementInfoMapFlow

    override val pathInfoMap: Flow<Map<String, PathInfo>> = pathInfoMapFlow

    override val lightConeInfoMap: Flow<Map<String, LightConeInfo>> = lightConeInfoMapFlow

    override val propertyInfoMap: Flow<Map<String, PropertyInfo>> = propertyInfoMapFlow

    override val relicSetInfoMap: Flow<Map<String, RelicSetInfo>> = relicSetInfoMapFlow

    override val relicInfoMap: Flow<Map<String, RelicInfo>> = relicInfoMapFlow

    override val relicAffixInfoMap: Flow<Map<String, AffixData>> = relicAffixInfoMapFlow

    override val characterInfoWithDetailsList: Flow<List<CharacterInfoWithDetails>> =
        characterInfoWithDetailsListFlow

    override suspend fun calculateCharacterScore(
        character: Character,
        preset: Preset,
    ): Result<CharacterReport> {
        return characterScoreFlow.first()
    }

    override suspend fun updateGameInfoInDb(lang: GameTextLanguage): Result<Unit> {
        return gameInfoInDbUpdateResultFlow.first()
    }

    fun sendCharacterInfoMap(characterInfoMap: Map<String, CharacterInfo>) {
        characterInfoMapFlow.tryEmit(characterInfoMap)
    }

    fun sendElementInfoMap(elementInfoMap: Map<String, ElementInfo>) {
        elementInfoMapFlow.tryEmit(elementInfoMap)
    }

    fun sendPathInfoMap(pathInfoMap: Map<String, PathInfo>) {
        pathInfoMapFlow.tryEmit(pathInfoMap)
    }

    fun sendLightConeInfoMap(lightConeInfoMap: Map<String, LightConeInfo>) {
        lightConeInfoMapFlow.tryEmit(lightConeInfoMap)
    }

    fun sendPropertyInfoMap(propertyInfoMap: Map<String, PropertyInfo>) {
        propertyInfoMapFlow.tryEmit(propertyInfoMap)
    }

    fun sendRelicSetInfoMap(relicSetInfoMap: Map<String, RelicSetInfo>) {
        relicSetInfoMapFlow.tryEmit(relicSetInfoMap)
    }

    fun sendRelicInfoMap(relicInfoMap: Map<String, RelicInfo>) {
        relicInfoMapFlow.tryEmit(relicInfoMap)
    }

    fun sendAffixDataMap(relicAffixInfoMap: Map<String, AffixData>) {
        relicAffixInfoMapFlow.tryEmit(relicAffixInfoMap)
    }

    fun sendCharacterInfoWithDetailsList(
        characterInfoWithDetailsList: List<CharacterInfoWithDetails>,
    ) {
        characterInfoWithDetailsListFlow.tryEmit(characterInfoWithDetailsList)
    }

    fun sendCharacterScore(characterScore: Result<CharacterReport>) {
        characterScoreFlow.tryEmit(characterScore)
    }

    fun sendGameInfoInDbUpdateResult(gameInfoInDbUpdateResult: Result<Unit>) {
        gameInfoInDbUpdateResultFlow.tryEmit(gameInfoInDbUpdateResult)
    }
}
