package com.dogeby.core.data.fake

import com.dogeby.core.data.repository.GameRepository
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
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first
import org.jetbrains.annotations.TestOnly

@TestOnly
class FakeGameRepository : GameRepository {

    private val charactersInfoFlow: MutableSharedFlow<Map<String, CharacterInfo>> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    private val elementsInfoFlow: MutableSharedFlow<Map<String, ElementInfo>> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    private val pathsInfoFlow: MutableSharedFlow<Map<String, PathInfo>> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    private val lightConesInfoFlow: MutableSharedFlow<Map<String, LightConeInfo>> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    private val propertiesInfoFlow: MutableSharedFlow<Map<String, PropertyInfo>> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    private val relicSetsInfoFlow: MutableSharedFlow<Map<String, RelicSetInfo>> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    private val relicsInfoFlow: MutableSharedFlow<Map<String, RelicInfo>> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    private val relicAffixesInfoFlow: MutableSharedFlow<Map<String, AffixData>> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    private val charactersInfoWithDetailsFlow: MutableSharedFlow<List<CharacterInfoWithDetails>> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    private val characterScoreFlow: MutableSharedFlow<Result<CharacterReport>> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    private val gameInfoInDbUpdateResultFlow: MutableSharedFlow<Result<Unit>> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    override val charactersInfo: Flow<Map<String, CharacterInfo>> = charactersInfoFlow

    override val elementsInfo: Flow<Map<String, ElementInfo>> = elementsInfoFlow

    override val pathsInfo: Flow<Map<String, PathInfo>> = pathsInfoFlow

    override val lightConesInfo: Flow<Map<String, LightConeInfo>> = lightConesInfoFlow

    override val propertiesInfo: Flow<Map<String, PropertyInfo>> = propertiesInfoFlow

    override val relicSetsInfo: Flow<Map<String, RelicSetInfo>> = relicSetsInfoFlow

    override val relicsInfo: Flow<Map<String, RelicInfo>> = relicsInfoFlow

    override val relicAffixesInfo: Flow<Map<String, AffixData>> = relicAffixesInfoFlow

    override val charactersInfoWithDetails: Flow<List<CharacterInfoWithDetails>> =
        charactersInfoWithDetailsFlow

    override suspend fun calculateCharacterScore(
        character: Character,
        preset: Preset,
    ): Result<CharacterReport> {
        return characterScoreFlow.first()
    }

    override suspend fun updateGameInfoInDb(lang: GameTextLanguage): Result<Unit> {
        return gameInfoInDbUpdateResultFlow.first()
    }

    fun sendCharacterInfo(charactersInfo: Map<String, CharacterInfo>) {
        charactersInfoFlow.tryEmit(charactersInfo)
    }

    fun sendElementsInfo(elementsInfo: Map<String, ElementInfo>) {
        elementsInfoFlow.tryEmit(elementsInfo)
    }

    fun sendPathInfo(pathsInfo: Map<String, PathInfo>) {
        pathsInfoFlow.tryEmit(pathsInfo)
    }

    fun sendLightConeInfo(lightConesInfo: Map<String, LightConeInfo>) {
        lightConesInfoFlow.tryEmit(lightConesInfo)
    }

    fun sendPropertyInfo(propertiesInfo: Map<String, PropertyInfo>) {
        propertiesInfoFlow.tryEmit(propertiesInfo)
    }

    fun sendRelicSetInfo(relicSetsInfo: Map<String, RelicSetInfo>) {
        relicSetsInfoFlow.tryEmit(relicSetsInfo)
    }

    fun sendRelicInfo(relicsInfo: Map<String, RelicInfo>) {
        relicsInfoFlow.tryEmit(relicsInfo)
    }

    fun sendAffixData(relicAffixesInfo: Map<String, AffixData>) {
        relicAffixesInfoFlow.tryEmit(relicAffixesInfo)
    }

    fun sendCharacterInfoWithDetails(charactersInfoWithDetails: List<CharacterInfoWithDetails>) {
        charactersInfoWithDetailsFlow.tryEmit(charactersInfoWithDetails)
    }

    fun sendCharacterScore(characterScore: Result<CharacterReport>) {
        characterScoreFlow.tryEmit(characterScore)
    }

    fun sendGameInfoInDbUpdateResult(gameInfoInDbUpdateResult: Result<Unit>) {
        gameInfoInDbUpdateResultFlow.tryEmit(gameInfoInDbUpdateResult)
    }
}
