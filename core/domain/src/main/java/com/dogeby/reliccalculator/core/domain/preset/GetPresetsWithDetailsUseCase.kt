package com.dogeby.reliccalculator.core.domain.preset

import com.dogeby.core.data.repository.GameRepository
import com.dogeby.core.data.repository.PreferencesRepository
import com.dogeby.core.data.repository.PresetRepository
import com.dogeby.reliccalculator.core.domain.model.AffixWeightWithInfo
import com.dogeby.reliccalculator.core.domain.model.AttrComparisonWithInfo
import com.dogeby.reliccalculator.core.domain.model.PresetWithDetails
import com.dogeby.reliccalculator.core.domain.model.toPresetWithDetails
import com.dogeby.reliccalculator.core.model.hoyo.index.CharacterInfoWithDetails
import com.dogeby.reliccalculator.core.model.hoyo.index.PropertyInfo
import com.dogeby.reliccalculator.core.model.hoyo.index.RelicSetInfo
import com.dogeby.reliccalculator.core.model.preferences.CharacterSortField
import com.dogeby.reliccalculator.core.model.preferences.PresetListPreferencesData
import com.dogeby.reliccalculator.core.model.preset.AffixWeight
import com.dogeby.reliccalculator.core.model.preset.Preset
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

class GetPresetsWithDetailsUseCase @Inject constructor(
    private val presetRepository: PresetRepository,
    private val gameRepository: GameRepository,
    private val preferencesRepository: PreferencesRepository,
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<List<PresetWithDetails>> {
        return combine(
            gameRepository.charactersInfoWithDetails,
            gameRepository.relicSetsInfo,
            gameRepository.propertiesInfo,
            preferencesRepository.getPresetListPreferencesData(),
        ) { charactersInfoWithDetails, relicSetsInfo, propertiesInfo, preferencesData ->
            val isMatchingPreferences: (CharacterInfoWithDetails) -> Boolean = { details ->
                details.characterInfo.rarity.isMatching(preferencesData.filteredRarities) &&
                    details.pathInfo.id.isMatching(preferencesData.filteredPathIds) &&
                    details.elementInfo.id.isMatching(preferencesData.filteredElementIds)
            }

            val filteredCharactersInfoWithDetails = charactersInfoWithDetails
                .filter(isMatchingPreferences)
                .associateBy { it.characterInfo.id }

            CharacterFilterResults(
                filteredCharacters = filteredCharactersInfoWithDetails,
                relicSets = relicSetsInfo,
                properties = propertiesInfo,
                preferencesData = preferencesData,
            )
        }.flatMapLatest { characterFilterResults ->
            val filteredPreset = presetRepository
                .getPresets(characterFilterResults.filteredCharacters.keys)

            filteredPreset
                .mapToPresetsWithDetails(
                    charactersInfoWithDetails = characterFilterResults.filteredCharacters,
                    relicSetsInfo = characterFilterResults.relicSets,
                    propertiesInfo = characterFilterResults.properties,
                )
                .map {
                    it.sortedBy(characterFilterResults.preferencesData.sortField)
                }
        }
    }

    private data class CharacterFilterResults(
        val filteredCharacters: Map<String, CharacterInfoWithDetails>,
        val relicSets: Map<String, RelicSetInfo>,
        val properties: Map<String, PropertyInfo>,
        val preferencesData: PresetListPreferencesData,
    )

    private fun <T> T.isMatching(list: Set<T>) = list.isEmpty() or (this in list)

    private fun Flow<List<Preset>>.mapToPresetsWithDetails(
        charactersInfoWithDetails: Map<String, CharacterInfoWithDetails>,
        relicSetsInfo: Map<String, RelicSetInfo>,
        propertiesInfo: Map<String, PropertyInfo>,
    ): Flow<List<PresetWithDetails>> {
        return map { presets ->
            presets.mapNotNull { preset ->
                val characterInfoWithDetails = charactersInfoWithDetails[preset.characterId]
                    ?: return@mapNotNull null

                preset.toPresetWithDetails(
                    characterInfo = characterInfoWithDetails.characterInfo,
                    pathInfo = characterInfoWithDetails.pathInfo,
                    elementInfo = characterInfoWithDetails.elementInfo,
                    relicSets = preset.relicSetIds.mapNotNull { relicSetsInfo[it] },
                    pieceMainAffixWeightsWithInfo = preset
                        .pieceMainAffixWeights
                        .mapValues { pieceMainAffixWeight ->
                            pieceMainAffixWeight.value.mapNotNullToAffixWeightsWithInfo(
                                propertiesInfo,
                            )
                        },
                    subAffixWeightsWithInfo = preset
                        .subAffixWeights
                        .mapNotNullToAffixWeightsWithInfo(propertiesInfo),
                    attrComparisons = preset.attrComparisons.mapNotNull { attrComparison ->
                        propertiesInfo[attrComparison.type]?.let {
                            AttrComparisonWithInfo(
                                attrComparison = attrComparison,
                                propertyInfo = it,
                            )
                        }
                    },
                )
            }
        }
    }

    private fun List<AffixWeight>.mapNotNullToAffixWeightsWithInfo(
        propertiesInfo: Map<String, PropertyInfo>,
    ): List<AffixWeightWithInfo> {
        return mapNotNull { affixWeight ->
            propertiesInfo[affixWeight.type]?.let {
                AffixWeightWithInfo(
                    affixWeight = affixWeight,
                    propertyInfo = it,
                )
            }
        }
    }

    private fun String.toAdjustedId(): Int =
        toIntOrNull()?.let { if (it >= TRAILBLAZER_ID) it - TRAILBLAZER_ID else it } ?: 0

    private fun List<PresetWithDetails>.sortedBy(
        characterSortField: CharacterSortField,
    ): List<PresetWithDetails> {
        return when (characterSortField) {
            CharacterSortField.LATEST_RELEASED -> {
                sortedWith { o1, o2 ->
                    o2.characterId.toAdjustedId() - o1.characterId.toAdjustedId()
                }
            }
            CharacterSortField.EARLIEST_RELEASED -> {
                sortedWith { o1, o2 ->
                    o1.characterId.toAdjustedId() - o2.characterId.toAdjustedId()
                }
            }
            CharacterSortField.NAME -> {
                sortedBy {
                    it.characterInfo.name
                }
            }
        }
    }

    private companion object {
        const val TRAILBLAZER_ID = 8001
    }
}
