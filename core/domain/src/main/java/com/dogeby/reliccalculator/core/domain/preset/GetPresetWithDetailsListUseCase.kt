package com.dogeby.reliccalculator.core.domain.preset

import com.dogeby.reliccalculator.core.data.repository.GameRepository
import com.dogeby.reliccalculator.core.data.repository.PresetRepository
import com.dogeby.reliccalculator.core.domain.model.AttrComparisonWithInfo
import com.dogeby.reliccalculator.core.domain.model.PresetWithDetails
import com.dogeby.reliccalculator.core.domain.model.mapNotNullToAffixWeightsWithInfo
import com.dogeby.reliccalculator.core.domain.model.toPresetWithDetails
import com.dogeby.reliccalculator.core.model.mihomo.index.CharacterInfoWithDetails
import com.dogeby.reliccalculator.core.model.mihomo.index.PropertyInfo
import com.dogeby.reliccalculator.core.model.mihomo.index.RelicSetInfo
import com.dogeby.reliccalculator.core.model.preferences.CharacterSortField
import com.dogeby.reliccalculator.core.model.preset.Preset
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

class GetPresetWithDetailsListUseCase @Inject constructor(
    private val presetRepository: PresetRepository,
    private val gameRepository: GameRepository,
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(
        filteredRarities: Set<Int>,
        filteredPathIds: Set<String>,
        filteredElementIds: Set<String>,
        sortField: CharacterSortField,
    ): Flow<List<PresetWithDetails>> {
        return combine(
            gameRepository.characterInfoWithDetailsList,
            gameRepository.relicSetInfoMap,
            gameRepository.propertyInfoMap,
        ) { characterInfoWithDetailsList, relicSetInfoMap, propertyInfoMap ->
            val isMatchingPreferences: (CharacterInfoWithDetails) -> Boolean = { details ->
                details.characterInfo.rarity.isMatching(filteredRarities) &&
                    details.pathInfo.id.isMatching(filteredPathIds) &&
                    details.elementInfo.id.isMatching(filteredElementIds)
            }

            val filteredCharacters = characterInfoWithDetailsList
                .filter(isMatchingPreferences)
                .associateBy { it.characterInfo.id }

            CharacterFilterResults(
                filteredCharacters = filteredCharacters,
                relicSets = relicSetInfoMap,
                properties = propertyInfoMap,
            )
        }.flatMapLatest { characterFilterResults ->
            val filteredPreset = presetRepository
                .getPresets(characterFilterResults.filteredCharacters.keys)

            filteredPreset
                .mapToPresetsWithDetails(
                    characterInfoWithDetailsMap = characterFilterResults.filteredCharacters,
                    relicSetInfoMap = characterFilterResults.relicSets,
                    propertyInfoMap = characterFilterResults.properties,
                )
                .map {
                    it.sortedBy(sortField)
                }
        }
    }

    private data class CharacterFilterResults(
        val filteredCharacters: Map<String, CharacterInfoWithDetails>,
        val relicSets: Map<String, RelicSetInfo>,
        val properties: Map<String, PropertyInfo>,
    )

    private fun <T> T.isMatching(list: Set<T>) = list.isEmpty() or (this in list)

    private fun Flow<List<Preset>>.mapToPresetsWithDetails(
        characterInfoWithDetailsMap: Map<String, CharacterInfoWithDetails>,
        relicSetInfoMap: Map<String, RelicSetInfo>,
        propertyInfoMap: Map<String, PropertyInfo>,
    ): Flow<List<PresetWithDetails>> {
        return map { presets ->
            presets.mapNotNull { preset ->
                val characterInfoWithDetails = characterInfoWithDetailsMap[preset.characterId]
                    ?: return@mapNotNull null

                preset.toPresetWithDetails(
                    characterInfo = characterInfoWithDetails.characterInfo,
                    pathInfo = characterInfoWithDetails.pathInfo,
                    elementInfo = characterInfoWithDetails.elementInfo,
                    relicSets = preset.relicSetIds.mapNotNull { relicSetInfoMap[it] },
                    pieceMainAffixWeightsWithInfo = preset
                        .pieceMainAffixWeights
                        .mapValues { pieceMainAffixWeight ->
                            pieceMainAffixWeight.value.mapNotNullToAffixWeightsWithInfo(
                                propertyInfoMap,
                            )
                        },
                    subAffixWeightsWithInfo = preset
                        .subAffixWeights
                        .mapNotNullToAffixWeightsWithInfo(propertyInfoMap),
                    attrComparisons = preset.attrComparisons.mapNotNull { attrComparison ->
                        propertyInfoMap[attrComparison.type]?.let {
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

    private fun String.toAdjustedId(): Int =
        toIntOrNull()?.let { if (it >= TRAILBLAZER_ID) it - TRAILBLAZER_ID else it } ?: 0

    private fun List<PresetWithDetails>.sortedBy(
        characterSortField: CharacterSortField,
    ): List<PresetWithDetails> {
        return when (characterSortField) {
            CharacterSortField.ID_ASC -> {
                sortedWith { o1, o2 ->
                    o2.characterId.toAdjustedId() - o1.characterId.toAdjustedId()
                }
            }
            CharacterSortField.ID_DESC -> {
                sortedWith { o1, o2 ->
                    o1.characterId.toAdjustedId() - o2.characterId.toAdjustedId()
                }
            }
            CharacterSortField.NAME_ASC -> {
                sortedBy {
                    it.characterInfo.name
                }
            }
            CharacterSortField.NAME_DESC -> {
                sortedByDescending {
                    it.characterInfo.name
                }
            }
        }
    }

    private companion object {
        const val TRAILBLAZER_ID = 8001
    }
}
