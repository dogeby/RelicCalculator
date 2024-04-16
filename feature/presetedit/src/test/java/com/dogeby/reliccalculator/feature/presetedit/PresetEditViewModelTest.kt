package com.dogeby.reliccalculator.feature.presetedit

import androidx.lifecycle.SavedStateHandle
import com.dogeby.reliccalculator.core.common.decoder.StringDecoder
import com.dogeby.reliccalculator.core.domain.fake.FakeGetAttrComparisonWithInfoListUseCase
import com.dogeby.reliccalculator.core.domain.fake.FakeGetMainAffixWeightWithInfoUseCase
import com.dogeby.reliccalculator.core.domain.fake.FakeGetPresetWithDetailsByIdUseCase
import com.dogeby.reliccalculator.core.domain.fake.FakeGetRelicSetInfoListUseCase
import com.dogeby.reliccalculator.core.domain.fake.FakeGetSubAffixWeightWithInfoUseCase
import com.dogeby.reliccalculator.core.domain.model.AffixWeightWithInfo
import com.dogeby.reliccalculator.core.domain.model.AttrComparisonWithInfo
import com.dogeby.reliccalculator.core.domain.preset.UpdatePresetUseCase
import com.dogeby.reliccalculator.core.domain.preset.UpdatedPresetCount
import com.dogeby.reliccalculator.core.model.mihomo.index.RelicPiece
import com.dogeby.reliccalculator.core.model.mihomo.index.samplePropertyInfo
import com.dogeby.reliccalculator.core.model.mihomo.index.sampleRelicSetInfo
import com.dogeby.reliccalculator.core.model.preset.AffixWeight
import com.dogeby.reliccalculator.core.model.preset.AttrComparison
import com.dogeby.reliccalculator.core.model.preset.ComparisonOperator
import com.dogeby.reliccalculator.core.model.preset.sampleAttrComparison
import com.dogeby.reliccalculator.core.model.preset.sampleMainAffixWeight
import com.dogeby.reliccalculator.core.model.preset.sampleSubAffixWeight
import com.dogeby.reliccalculator.core.ui.component.preset.AffixAddDialogueUiState
import com.dogeby.reliccalculator.core.ui.component.preset.AttrComparisonAddDialogueUiState
import com.dogeby.reliccalculator.core.ui.component.preset.AttrComparisonEditListUiState
import com.dogeby.reliccalculator.core.ui.component.preset.PieceMainAffixWeightListUiState
import com.dogeby.reliccalculator.core.ui.component.preset.RelicSetFilterUiState
import com.dogeby.reliccalculator.core.ui.component.preset.RelicSetFiltersUiState
import com.dogeby.reliccalculator.core.ui.component.preset.SubAffixWeightListUiState
import com.dogeby.reliccalculator.feature.presetedit.navigation.PRESET_ID_ARG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class PresetEditViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var presetEditViewModel: PresetEditViewModel
    private lateinit var saveStateHandle: SavedStateHandle

    @Before
    fun setUp() {
        val stringDecoder = mock<StringDecoder> {
            on { decodeString("1212") } doReturn "1212"
        }
        saveStateHandle = SavedStateHandle().apply { set(PRESET_ID_ARG, "1212") }
        presetEditViewModel = PresetEditViewModel(
            getPresetWithDetailsByIdUseCase = FakeGetPresetWithDetailsByIdUseCase(),
            getRelicSetInfoListUseCase = FakeGetRelicSetInfoListUseCase(),
            getAttrComparisonWithInfoListUseCase = FakeGetAttrComparisonWithInfoListUseCase(),
            getMainAffixWeightWithInfoUseCase = FakeGetMainAffixWeightWithInfoUseCase(),
            getSubAffixWeightWithInfoUseCase = FakeGetSubAffixWeightWithInfoUseCase(),
            stringDecoder = stringDecoder,
            savedStateHandle = saveStateHandle,
            updatePresetUseCase = object : UpdatePresetUseCase {
                override suspend fun invoke(
                    characterId: String,
                    relicSetIds: List<String>,
                    pieceMainAffixWeights: Map<RelicPiece, List<AffixWeight>>,
                    subAffixWeights: List<AffixWeight>,
                    attrComparisons: List<AttrComparison>,
                    isAutoUpdate: Boolean,
                ): Result<UpdatedPresetCount> {
                    return Result.success(1)
                }
            },
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun test_getRelicSetFiltersUiState_success() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            presetEditViewModel.relicSetFiltersUiState.collect()
        }

        assertEquals(
            RelicSetFiltersUiState.Success(
                relicSets = listOf(
                    RelicSetFilterUiState(
                        selected = true,
                        relicSetInfo = sampleRelicSetInfo,
                    ),
                ),
            ),
            presetEditViewModel.relicSetFiltersUiState.value,
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun test_getAttrComparisonEditListUiState_success() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            presetEditViewModel.attrComparisonEditListUiState.collect()
        }

        assertEquals(
            AttrComparisonEditListUiState.Success(
                attrComparisonWithInfoList = listOf(
                    AttrComparisonWithInfo(
                        attrComparison = sampleAttrComparison,
                        propertyInfo = samplePropertyInfo,
                    ),
                ),
            ),
            presetEditViewModel.attrComparisonEditListUiState.value,
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun test_getAttrComparisonAddDialogueUiState_empty() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            presetEditViewModel.attrComparisonAddDialogueUiState.collect()
        }

        assertEquals(
            AttrComparisonAddDialogueUiState.Empty,
            presetEditViewModel.attrComparisonAddDialogueUiState.value,
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun test_getPieceMainAffixWeightListUiState_success() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            presetEditViewModel.pieceMainAffixWeightListUiState.collect()
        }

        assertEquals(
            PieceMainAffixWeightListUiState.Success(
                pieceToAffixWeightsMap = mapOf(
                    RelicPiece.HEAD to listOf(
                        AffixWeightWithInfo(
                            affixWeight = sampleMainAffixWeight,
                            propertyInfo = samplePropertyInfo,
                        ),
                    ),
                ),
            ),
            presetEditViewModel.pieceMainAffixWeightListUiState.value,
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun test_getSubAffixWeightListUiState_success() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            presetEditViewModel.subAffixWeightListUiState.collect()
        }

        assertEquals(
            SubAffixWeightListUiState.Success(
                affixWeightWithInfoList = listOf(
                    AffixWeightWithInfo(
                        affixWeight = sampleSubAffixWeight,
                        propertyInfo = samplePropertyInfo,
                    ),
                ),
            ),
            presetEditViewModel.subAffixWeightListUiState.value,
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun test_getSubAffixAddDialogueUiState_empty() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            presetEditViewModel.subAffixAddDialogueUiState.collect()
        }

        assertEquals(
            AffixAddDialogueUiState.Empty,
            presetEditViewModel.subAffixAddDialogueUiState.value,
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun test_deleteAndAddAttrComparison_success() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            presetEditViewModel.attrComparisonEditListUiState.collect()
        }
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            presetEditViewModel.attrComparisonAddDialogueUiState.collect()
        }

        presetEditViewModel.deleteAttrComparison(sampleAttrComparison.type)

        assertEquals(
            AttrComparisonEditListUiState.Success(emptyList()),
            presetEditViewModel.attrComparisonEditListUiState.value,
        )
        assertEquals(
            AttrComparisonAddDialogueUiState.Success(
                attrComparisons = listOf(
                    AttrComparisonWithInfo(
                        attrComparison = sampleAttrComparison,
                        propertyInfo = samplePropertyInfo,
                    ),
                ),
            ),
            presetEditViewModel.attrComparisonAddDialogueUiState.value,
        )

        presetEditViewModel.addAttrComparison(sampleAttrComparison.type)

        assertEquals(
            AttrComparisonEditListUiState.Success(
                attrComparisonWithInfoList = listOf(
                    AttrComparisonWithInfo(
                        attrComparison = sampleAttrComparison,
                        propertyInfo = samplePropertyInfo,
                    ),
                ),
            ),
            presetEditViewModel.attrComparisonEditListUiState.value,
        )
        assertEquals(
            AttrComparisonAddDialogueUiState.Empty,
            presetEditViewModel.attrComparisonAddDialogueUiState.value,
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun test_modifyAttrComparison_success() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            presetEditViewModel.attrComparisonEditListUiState.collect()
        }

        val modifiedAttrComparison = sampleAttrComparison.copy(
            comparisonOperator = ComparisonOperator.LESS_THAN,
            comparedValue = sampleAttrComparison.comparedValue + 100f,
            display = (sampleAttrComparison.display.toInt() + 100).toString(),
        )
        presetEditViewModel.modifyAttrComparison(
            type = sampleAttrComparison.type,
            comparisonOperator = modifiedAttrComparison.comparisonOperator,
            inputComparedValue = modifiedAttrComparison.display,
        )

        assertEquals(
            AttrComparisonEditListUiState.Success(
                attrComparisonWithInfoList = listOf(
                    AttrComparisonWithInfo(
                        attrComparison = modifiedAttrComparison,
                        propertyInfo = samplePropertyInfo,
                    ),
                ),
            ),
            presetEditViewModel.attrComparisonEditListUiState.value,
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun test_modifyRelicSet_success() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            presetEditViewModel.relicSetFiltersUiState.collect()
        }

        presetEditViewModel.modifyRelicSet(
            id = sampleRelicSetInfo.id,
            selected = false,
        )

        assertEquals(
            RelicSetFiltersUiState.Success(
                relicSets = listOf(
                    RelicSetFilterUiState(
                        selected = false,
                        relicSetInfo = sampleRelicSetInfo,
                    ),
                ),
            ),
            presetEditViewModel.relicSetFiltersUiState.value,
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun test_modifyPieceMainAffixWeight_success() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            presetEditViewModel.pieceMainAffixWeightListUiState.collect()
        }

        val modifiedMainAffixWeight = sampleMainAffixWeight.copy(
            weight = sampleMainAffixWeight.weight - 0.5f,
        )
        presetEditViewModel.modifyPieceMainAffixWeight(
            relicPiece = RelicPiece.HEAD,
            affixId = modifiedMainAffixWeight.affixId,
            weight = modifiedMainAffixWeight.weight,
        )

        assertEquals(
            PieceMainAffixWeightListUiState.Success(
                pieceToAffixWeightsMap = mapOf(
                    RelicPiece.HEAD to listOf(
                        AffixWeightWithInfo(
                            affixWeight = modifiedMainAffixWeight,
                            propertyInfo = samplePropertyInfo,
                        ),
                    ),
                ),
            ),
            presetEditViewModel.pieceMainAffixWeightListUiState.value,
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun test_deleteAndAddSubAffixWeight_success() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            presetEditViewModel.subAffixWeightListUiState.collect()
        }
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            presetEditViewModel.subAffixAddDialogueUiState.collect()
        }

        presetEditViewModel.deleteSubAffixWeight(sampleSubAffixWeight.affixId)

        assertEquals(
            SubAffixWeightListUiState.Success(emptyList()),
            presetEditViewModel.subAffixWeightListUiState.value,
        )
        assertEquals(
            AffixAddDialogueUiState.Success(
                affixes = listOf(
                    AffixWeightWithInfo(
                        affixWeight = sampleSubAffixWeight,
                        propertyInfo = samplePropertyInfo,
                    ),
                ),
            ),
            presetEditViewModel.subAffixAddDialogueUiState.value,
        )

        presetEditViewModel.addSubAffixWeight(listOf(sampleSubAffixWeight.affixId))

        assertEquals(
            SubAffixWeightListUiState.Success(
                affixWeightWithInfoList = listOf(
                    AffixWeightWithInfo(
                        affixWeight = sampleSubAffixWeight,
                        propertyInfo = samplePropertyInfo,
                    ),
                ),
            ),
            presetEditViewModel.subAffixWeightListUiState.value,
        )
        assertEquals(
            AffixAddDialogueUiState.Empty,
            presetEditViewModel.subAffixAddDialogueUiState.value,
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun test_modifySubAffixWeight_success() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            presetEditViewModel.subAffixWeightListUiState.collect()
        }

        val modifiedSubAffixWeight = sampleSubAffixWeight.copy(
            weight = sampleSubAffixWeight.weight - 0.5f,
        )
        presetEditViewModel.modifySubAffixWeight(
            affixId = modifiedSubAffixWeight.affixId,
            weight = modifiedSubAffixWeight.weight,
        )

        assertEquals(
            SubAffixWeightListUiState.Success(
                affixWeightWithInfoList = listOf(
                    AffixWeightWithInfo(
                        affixWeight = modifiedSubAffixWeight,
                        propertyInfo = samplePropertyInfo,
                    ),
                ),
            ),
            presetEditViewModel.subAffixWeightListUiState.value,
        )
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherRule(
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher(),
) : TestWatcher() {
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}
