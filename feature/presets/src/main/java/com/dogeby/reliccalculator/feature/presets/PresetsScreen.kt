package com.dogeby.reliccalculator.feature.presets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dogeby.reliccalculator.core.domain.model.AffixWeightWithInfo
import com.dogeby.reliccalculator.core.domain.model.AttrComparisonWithInfo
import com.dogeby.reliccalculator.core.domain.model.PresetWithDetails
import com.dogeby.reliccalculator.core.model.mihomo.index.RelicPiece
import com.dogeby.reliccalculator.core.model.mihomo.index.sampleCharacterInfo
import com.dogeby.reliccalculator.core.model.mihomo.index.sampleElementInfo
import com.dogeby.reliccalculator.core.model.mihomo.index.samplePathInfo
import com.dogeby.reliccalculator.core.model.mihomo.index.samplePropertyInfo
import com.dogeby.reliccalculator.core.model.mihomo.index.sampleRelicSetInfo
import com.dogeby.reliccalculator.core.model.preferences.CharacterListPreferencesData
import com.dogeby.reliccalculator.core.model.preferences.CharacterSortField
import com.dogeby.reliccalculator.core.model.preset.AffixWeight
import com.dogeby.reliccalculator.core.model.preset.AttrComparison
import com.dogeby.reliccalculator.core.model.preset.ComparisonOperator
import com.dogeby.reliccalculator.core.ui.component.EmptyState
import com.dogeby.reliccalculator.core.ui.component.HideableBarLazyVerticalGrid
import com.dogeby.reliccalculator.core.ui.component.LoadingState
import com.dogeby.reliccalculator.core.ui.component.preset.CharacterListOptionBar
import com.dogeby.reliccalculator.core.ui.component.preset.CharacterListOptionBarUiState
import com.dogeby.reliccalculator.core.ui.component.preset.PresetListUiState
import com.dogeby.reliccalculator.core.ui.component.preset.presetList
import com.dogeby.reliccalculator.core.ui.theme.RelicCalculatorTheme

@Composable
fun PresetsRoute(
    navigateToPresetEdit: (id: String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PresetsViewModel = hiltViewModel(),
) {
    val characterListOptionBarUiState by viewModel
        .characterListOptionBarUiState
        .collectAsStateWithLifecycle()
    val presetsUiState by viewModel.presetsUiState.collectAsStateWithLifecycle()

    PresetsScreen(
        characterListOptionBarUiState = characterListOptionBarUiState,
        presetListUiState = presetsUiState,
        onSetSortField = viewModel::setPresetsSortField,
        onConfirmFilters = viewModel::setPresetsFilters,
        onEditMenuItemClick = navigateToPresetEdit,
        onAutoUpdateChanged = viewModel::setPresetAutoUpdate,
        modifier = modifier,
    )
}

@Composable
private fun PresetsScreen(
    characterListOptionBarUiState: CharacterListOptionBarUiState,
    presetListUiState: PresetListUiState,
    onSetSortField: (CharacterSortField) -> Unit,
    onConfirmFilters: (
        selectedRarities: Set<Int>,
        selectedPathIds: Set<String>,
        selectedElementIds: Set<String>,
    ) -> Unit,
    onEditMenuItemClick: (id: String) -> Unit,
    onAutoUpdateChanged: (id: String, isAutoUpdate: Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (presetListUiState) {
        PresetListUiState.Loading -> LoadingState()
        is PresetListUiState.Success -> if (presetListUiState.presets.isNotEmpty()) {
            HideableBarLazyVerticalGrid(
                topBar = {
                    Surface {
                        CharacterListOptionBar(
                            characterListOptionBarUiState = characterListOptionBarUiState,
                            onSetSortField = onSetSortField,
                            onConfirmFilters = onConfirmFilters,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp),
                        )
                    }
                },
                topBarHeight = 48.dp,
                bottomBar = {},
                bottomBarHeight = 0.dp,
                columns = GridCells.Adaptive(308.dp),
                modifier = modifier,
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                presetList(
                    presetListUiState = presetListUiState,
                    onEditMenuItemClick = onEditMenuItemClick,
                    onAutoUpdateChanged = onAutoUpdateChanged,
                )
            }
        } else {
            EmptyState()
        }
    }
}

@Preview(apiLevel = 33, widthDp = 1500)
@Composable
private fun PreviewPresetsScreen() {
    RelicCalculatorTheme {
        val presets = List(5) { index ->
            PresetWithDetails(
                characterId = "$index",
                characterInfo = sampleCharacterInfo,
                pathInfo = samplePathInfo,
                elementInfo = sampleElementInfo,
                relicSets = List(3) {
                    sampleRelicSetInfo.copy(id = "$it")
                },
                pieceMainAffixWeightsWithInfo = RelicPiece.entries.associateWith {
                    List(3) {
                        AffixWeightWithInfo(
                            AffixWeight(affixId = "1$it", type = "SpeedDelta", weight = 1.0f),
                            samplePropertyInfo,
                        )
                    }
                },
                subAffixWeightsWithInfo = List(6) {
                    AffixWeightWithInfo(
                        AffixWeight(affixId = "2$it", type = "SpeedDelta", weight = 1.0f),
                        samplePropertyInfo,
                    )
                },
                isAutoUpdate = false,
                attrComparisons = List(6) {
                    AttrComparisonWithInfo(
                        attrComparison = AttrComparison(
                            type = "SpeedDelta$it",
                            field = "spd",
                            comparedValue = 500.0f,
                            display = "500",
                            percent = false,
                            comparisonOperator = ComparisonOperator.GREATER_THAN,
                        ),
                        propertyInfo = samplePropertyInfo,
                    )
                },
            )
        }
        PresetsScreen(
            presetListUiState = PresetListUiState.Success(presets),
            characterListOptionBarUiState = CharacterListOptionBarUiState.Success(
                pathInfoList = List(7) { samplePathInfo.copy(id = "$it", name = "$it") },
                elementInfoList = List(7) { sampleElementInfo.copy(id = "$it", name = "$it") },
                characterListPreferencesData = CharacterListPreferencesData(
                    filteredRarities = emptySet(),
                    filteredPathIds = emptySet(),
                    filteredElementIds = emptySet(),
                    sortField = CharacterSortField.ID_ASC,
                ),
            ),
            onSetSortField = {},
            onConfirmFilters = { _, _, _ -> },
            onEditMenuItemClick = {},
            onAutoUpdateChanged = { _, _ -> },
        )
    }
}
