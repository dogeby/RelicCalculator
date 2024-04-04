package com.dogeby.reliccalculator.core.domain.preset

import com.dogeby.reliccalculator.core.data.fake.FakeGameRepository
import com.dogeby.reliccalculator.core.data.fake.FakePresetRepository
import com.dogeby.reliccalculator.core.domain.model.AffixWeightWithInfo
import com.dogeby.reliccalculator.core.domain.model.AttrComparisonWithInfo
import com.dogeby.reliccalculator.core.domain.model.toPresetWithDetails
import com.dogeby.reliccalculator.core.model.mihomo.index.RelicPiece
import com.dogeby.reliccalculator.core.model.mihomo.index.sampleCharacterInfo
import com.dogeby.reliccalculator.core.model.mihomo.index.sampleCharacterInfoWithDetails
import com.dogeby.reliccalculator.core.model.mihomo.index.sampleElementInfo
import com.dogeby.reliccalculator.core.model.mihomo.index.samplePathInfo
import com.dogeby.reliccalculator.core.model.mihomo.index.samplePropertyInfo
import com.dogeby.reliccalculator.core.model.mihomo.index.sampleRelicSetInfo
import com.dogeby.reliccalculator.core.model.preset.samplePreset
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetPresetWithDetailsByIdUseCaseTest {

    private lateinit var getPresetWithDetailsByIdUseCase: GetPresetWithDetailsByIdUseCase
    private lateinit var presetRepository: FakePresetRepository
    private lateinit var gameRepository: FakeGameRepository

    private fun insertSampleInfoData() {
        gameRepository.apply {
            sendCharacterInfoWithDetailsList(listOf(sampleCharacterInfoWithDetails))
            sendRelicSetInfoMap(mapOf(sampleRelicSetInfo.id to sampleRelicSetInfo))
            sendPropertyInfoMap(mapOf(samplePropertyInfo.type to samplePropertyInfo))
        }
    }

    @Before
    fun setUp() {
        presetRepository = FakePresetRepository()
        gameRepository = FakeGameRepository()

        getPresetWithDetailsByIdUseCase = GetPresetWithDetailsByIdUseCaseImpl(
            presetRepository = presetRepository,
            gameRepository = gameRepository,
        )
    }

    @Test
    fun test_getPresetWithDetailsByIdUseCase_success() = runTest {
        presetRepository.insertPresets(listOf(samplePreset))
        insertSampleInfoData()

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
        val expectedPresetsWithDetail = samplePreset.toPresetWithDetails(
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
            expectedPresetsWithDetail,
            getPresetWithDetailsByIdUseCase(samplePreset.characterId).first().getOrThrow(),
        )
    }
}
