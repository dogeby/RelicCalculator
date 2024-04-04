package com.dogeby.reliccalculator.core.domain.preset

import com.dogeby.reliccalculator.core.data.fake.FakeGameRepository
import com.dogeby.reliccalculator.core.domain.model.AffixWeightWithInfo
import com.dogeby.reliccalculator.core.model.mihomo.index.AffixData
import com.dogeby.reliccalculator.core.model.mihomo.index.AffixInfo
import com.dogeby.reliccalculator.core.model.mihomo.index.RelicPiece
import com.dogeby.reliccalculator.core.model.mihomo.index.samplePropertyInfo
import com.dogeby.reliccalculator.core.model.preset.AffixWeight
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetMainAffixWeightWithInfoUseCaseTest {

    private lateinit var getMainAffixWeightWithInfoUseCase: GetMainAffixWeightWithInfoUseCase
    private lateinit var gameRepository: FakeGameRepository

    @Before
    fun setUp() {
        gameRepository = FakeGameRepository()
        getMainAffixWeightWithInfoUseCase = GetMainAffixWeightWithInfoUseCaseImpl(gameRepository)
    }

    @Test
    fun test_getMainAffixWeightWithInfoUseCase_success() = runTest {
        gameRepository.apply {
            sendAffixDataMap(
                mapOf(
                    "54" to AffixData(
                        id = "54",
                        affixes = mapOf(
                            "4" to AffixInfo(
                                affixId = "4",
                                property = "SpeedDelta",
                                base = 4.031999999890104,
                                step = 1.400000000372529,
                                stepNum = 0,
                            ),
                        ),
                    ),
                ),
            )
            sendPropertyInfoMap(mapOf(samplePropertyInfo.type to samplePropertyInfo))
        }

        val expectedResult = RelicPiece.FOOT to listOf(
            AffixWeightWithInfo(
                affixWeight = AffixWeight(
                    affixId = "4",
                    type = "SpeedDelta",
                    weight = 1.0f,
                ),
                propertyInfo = samplePropertyInfo,
            ),
        )
        val result = getMainAffixWeightWithInfoUseCase().first().toList().first()

        Assert.assertEquals(
            expectedResult,
            result,
        )
    }

    @Test
    fun test_getMainAffixWeightWithInfoUseCase_emptyAffixWeights() = runTest {
        gameRepository.apply {
            sendAffixDataMap(
                mapOf(
                    "54" to AffixData(
                        id = "54",
                        affixes = mapOf(
                            "4" to AffixInfo(
                                affixId = "4",
                                property = "SpeedDelta",
                                base = 0.0,
                                step = 0.0,
                                stepNum = 0,
                            ),
                        ),
                    ),
                    "55" to AffixData(
                        id = "55",
                        affixes = mapOf(
                            "5" to AffixInfo(
                                affixId = "5",
                                property = "AttackDelta",
                                base = 0.0,
                                step = 0.0,
                                stepNum = 0,
                            ),
                        ),
                    ),
                ),
            )
            sendPropertyInfoMap(mapOf(samplePropertyInfo.type to samplePropertyInfo))
        }

        val expectedResult = RelicPiece.FOOT to listOf(
            AffixWeightWithInfo(
                affixWeight = AffixWeight(
                    affixId = "4",
                    type = "SpeedDelta",
                    weight = 1.0f,
                ),
                propertyInfo = samplePropertyInfo,
            ),
        )
        val result = getMainAffixWeightWithInfoUseCase().first().toList().first()

        Assert.assertEquals(
            expectedResult,
            result,
        )
    }

    @Test
    fun test_getMainAffixWeightWithInfoUseCase_relicPieceIsNull() = runTest {
        gameRepository.apply {
            sendAffixDataMap(
                mapOf(
                    "40" to AffixData(
                        id = "40",
                        affixes = mapOf(
                            "4" to AffixInfo(
                                affixId = "4",
                                property = "SpeedDelta",
                                base = 0.0,
                                step = 0.0,
                                stepNum = 0,
                            ),
                        ),
                    ),
                    "41" to AffixData(
                        id = "14",
                        affixes = mapOf(
                            "5" to AffixInfo(
                                affixId = "5",
                                property = "AttackDelta",
                                base = 0.0,
                                step = 0.0,
                                stepNum = 0,
                            ),
                        ),
                    ),
                ),
            )
            sendPropertyInfoMap(mapOf(samplePropertyInfo.type to samplePropertyInfo))
        }

        val result = getMainAffixWeightWithInfoUseCase().first()
        Assert.assertTrue(result.isEmpty())
    }
}
