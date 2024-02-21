package com.dogeby.reliccalculator.core.domain

import com.dogeby.core.data.fake.FakeGameRepository
import com.dogeby.core.data.fake.FakePreferencesRepository
import com.dogeby.core.data.fake.FakePresetRepository
import com.dogeby.reliccalculator.core.domain.model.AffixWeightWithInfo
import com.dogeby.reliccalculator.core.domain.model.AttrComparisonWithInfo
import com.dogeby.reliccalculator.core.domain.model.PresetWithDetails
import com.dogeby.reliccalculator.core.domain.model.toPresetWithDetails
import com.dogeby.reliccalculator.core.domain.preset.GetPresetsWithDetailsUseCase
import com.dogeby.reliccalculator.core.model.hoyo.index.RelicPiece
import com.dogeby.reliccalculator.core.model.hoyo.index.sampleCharacterInfo
import com.dogeby.reliccalculator.core.model.hoyo.index.sampleCharacterInfoWithDetails
import com.dogeby.reliccalculator.core.model.hoyo.index.sampleElementInfo
import com.dogeby.reliccalculator.core.model.hoyo.index.samplePathInfo
import com.dogeby.reliccalculator.core.model.hoyo.index.samplePropertyInfo
import com.dogeby.reliccalculator.core.model.hoyo.index.sampleRelicSetInfo
import com.dogeby.reliccalculator.core.model.preferences.CharacterSortField
import com.dogeby.reliccalculator.core.model.preferences.samplePresetListPreferencesData
import com.dogeby.reliccalculator.core.model.preset.samplePreset
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetPresetsWithDetailsUseCaseTest {

    private lateinit var getPresetsWithDetailsUseCase: GetPresetsWithDetailsUseCase
    private lateinit var presetRepository: FakePresetRepository
    private lateinit var gameRepository: FakeGameRepository
    private lateinit var preferencesRepository: FakePreferencesRepository

    private fun insertSampleInfoData() {
        gameRepository.apply {
            sendCharacterInfoWithDetails(listOf(sampleCharacterInfoWithDetails))
            sendRelicSetInfo(mapOf(sampleRelicSetInfo.id to sampleRelicSetInfo))
            sendPropertyInfo(mapOf(samplePropertyInfo.type to samplePropertyInfo))
        }
    }

    @Before
    fun setUp() {
        presetRepository = FakePresetRepository()
        gameRepository = FakeGameRepository()
        preferencesRepository = FakePreferencesRepository()

        getPresetsWithDetailsUseCase = GetPresetsWithDetailsUseCase(
            presetRepository = presetRepository,
            gameRepository = gameRepository,
        )
    }

    @Test
    fun test_getPresetsWithDetailsUseCase_success() = runTest {
        presetRepository.insertPresets(listOf(samplePreset))
        insertSampleInfoData()
        preferencesRepository.setPresetListPreferencesData(samplePresetListPreferencesData)
        val listPreferences = preferencesRepository.getPresetListPreferencesData().first()

        val mainAffixWeight = samplePreset.pieceMainAffixWeights[RelicPiece.FOOT]?.first()
            ?: throw NullPointerException()
        val mainAffixWeightWithInfo = AffixWeightWithInfo(
            affixWeight = mainAffixWeight,
            propertyInfo = samplePropertyInfo,
        )
        val subAffixWeight = samplePreset.subAffixWeights.find {
            it.type == samplePropertyInfo.type
        } ?: throw NullPointerException()
        val subAffixWeightWithInfo = AffixWeightWithInfo(
            affixWeight = subAffixWeight,
            propertyInfo = samplePropertyInfo,
        )
        val attrComparison = samplePreset.attrComparisons.first()
        val expectedPresetsWithDetails = samplePreset.toPresetWithDetails(
            characterInfo = sampleCharacterInfo,
            pathInfo = samplePathInfo,
            elementInfo = sampleElementInfo,
            relicSets = listOf(sampleRelicSetInfo),
            pieceMainAffixWeightsWithInfo = samplePreset.pieceMainAffixWeights.mapValues {
                if (it.key == RelicPiece.FOOT) return@mapValues listOf(mainAffixWeightWithInfo)
                emptyList()
            },
            subAffixWeightsWithInfo = listOf(subAffixWeightWithInfo),
            attrComparisons = listOf(
                AttrComparisonWithInfo(
                    attrComparison = attrComparison,
                    propertyInfo = samplePropertyInfo,
                ),
            ),
        )

        Assert.assertEquals(
            expectedPresetsWithDetails,
            getPresetsWithDetailsUseCase(
                filteredRarities = listPreferences.filteredRarities,
                filteredPathIds = listPreferences.filteredPathIds,
                filteredElementIds = listPreferences.filteredElementIds,
                sortField = listPreferences.sortField,
            ).first().first(),
        )
    }

    @Test
    fun test_getPresetsWithDetailsUseCase_noMatching() = runTest {
        presetRepository.insertPresets(listOf(samplePreset))
        insertSampleInfoData()
        preferencesRepository.setPresetListPreferencesData(
            samplePresetListPreferencesData.copy(
                filteredPathIds = setOf(""),
            ),
        )
        val testPathPreferences = preferencesRepository.getPresetListPreferencesData().first()

        Assert.assertEquals(
            emptyList<PresetWithDetails>(),
            getPresetsWithDetailsUseCase(
                filteredRarities = testPathPreferences.filteredRarities,
                filteredPathIds = testPathPreferences.filteredPathIds,
                filteredElementIds = testPathPreferences.filteredElementIds,
                sortField = testPathPreferences.sortField,
            ).first(),
        )

        preferencesRepository.clearFilteredData()
        preferencesRepository.setFilteredElementIds(setOf(""))
        val testElementPreferences = preferencesRepository.getPresetListPreferencesData().first()

        Assert.assertEquals(
            emptyList<PresetWithDetails>(),
            getPresetsWithDetailsUseCase(
                filteredRarities = testElementPreferences.filteredRarities,
                filteredPathIds = testElementPreferences.filteredPathIds,
                filteredElementIds = testElementPreferences.filteredElementIds,
                sortField = testElementPreferences.sortField,
            ).first(),
        )

        preferencesRepository.clearFilteredData()
        preferencesRepository.setFilteredRarities(setOf(4))
        val testRaritiesPreferences = preferencesRepository.getPresetListPreferencesData().first()

        Assert.assertEquals(
            emptyList<PresetWithDetails>(),
            getPresetsWithDetailsUseCase(
                filteredRarities = testRaritiesPreferences.filteredRarities,
                filteredPathIds = testRaritiesPreferences.filteredPathIds,
                filteredElementIds = testRaritiesPreferences.filteredElementIds,
                sortField = testRaritiesPreferences.sortField,
            ).first(),
        )
    }

    @Test
    fun test_getPresetsWithDetailsUseCase_sortedByName() = runTest {
        val ids = listOf("5", "3", "4", "2", "1")
        val initialPresets = ids.map { samplePreset.copy(characterId = it) }
        presetRepository.insertPresets(initialPresets)
        insertSampleInfoData()
        val characterInfoWithDetails = ids.map {
            sampleCharacterInfoWithDetails.copy(
                characterInfo = sampleCharacterInfo.copy(
                    id = it,
                    name = it,
                ),
            )
        }
        gameRepository.sendCharacterInfoWithDetails(characterInfoWithDetails)
        preferencesRepository.setPresetListPreferencesData(
            samplePresetListPreferencesData.copy(
                sortField = CharacterSortField.NAME,
            ),
        )
        val listPreferences = preferencesRepository.getPresetListPreferencesData().first()

        Assert.assertEquals(
            ids.sortedBy { it },
            getPresetsWithDetailsUseCase(
                filteredRarities = listPreferences.filteredRarities,
                filteredPathIds = listPreferences.filteredPathIds,
                filteredElementIds = listPreferences.filteredElementIds,
                sortField = listPreferences.sortField,
            ).first().map { it.characterId },
        )
    }

    @Test
    fun test_getPresetsWithDetailsUseCase_sortedByLatestReleased() = runTest {
        val ids = listOf("5", "3", "4", "2", "8001")
        val initialPresets = ids.map { samplePreset.copy(characterId = it) }
        presetRepository.insertPresets(initialPresets)
        insertSampleInfoData()
        val characterInfoWithDetails = ids.map {
            sampleCharacterInfoWithDetails.copy(
                characterInfo = sampleCharacterInfo.copy(
                    id = it,
                    name = it,
                ),
            )
        }
        gameRepository.sendCharacterInfoWithDetails(characterInfoWithDetails)
        preferencesRepository.setPresetListPreferencesData(
            samplePresetListPreferencesData.copy(
                sortField = CharacterSortField.LATEST_RELEASED,
            ),
        )
        val listPreferences = preferencesRepository.getPresetListPreferencesData().first()

        Assert.assertEquals(
            listOf("5", "4", "3", "2", "8001"),
            getPresetsWithDetailsUseCase(
                filteredRarities = listPreferences.filteredRarities,
                filteredPathIds = listPreferences.filteredPathIds,
                filteredElementIds = listPreferences.filteredElementIds,
                sortField = listPreferences.sortField,
            ).first().map { it.characterId },
        )
    }

    @Test
    fun test_getPresetsWithDetailsUseCase_sortedByEarliestReleased() = runTest {
        val ids = listOf("5", "8003", "4", "2", "8001")
        val initialPresets = ids.map { samplePreset.copy(characterId = it) }
        presetRepository.insertPresets(initialPresets)
        insertSampleInfoData()
        val characterInfoWithDetails = ids.map {
            sampleCharacterInfoWithDetails.copy(
                characterInfo = sampleCharacterInfo.copy(
                    id = it,
                    name = it,
                ),
            )
        }
        gameRepository.sendCharacterInfoWithDetails(characterInfoWithDetails)
        preferencesRepository.setPresetListPreferencesData(
            samplePresetListPreferencesData.copy(
                sortField = CharacterSortField.EARLIEST_RELEASED,
            ),
        )
        val listPreferences = preferencesRepository.getPresetListPreferencesData().first()

        Assert.assertEquals(
            listOf("8001", "8003", "2", "4", "5"),
            getPresetsWithDetailsUseCase(
                filteredRarities = listPreferences.filteredRarities,
                filteredPathIds = listPreferences.filteredPathIds,
                filteredElementIds = listPreferences.filteredElementIds,
                sortField = listPreferences.sortField,
            ).first().map { it.characterId },
        )
    }
}
