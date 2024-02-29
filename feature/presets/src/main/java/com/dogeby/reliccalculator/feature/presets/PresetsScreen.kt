package com.dogeby.reliccalculator.feature.presets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dogeby.core.ui.R
import com.dogeby.core.ui.component.HideableBarLazyVerticalGrid
import com.dogeby.core.ui.component.preset.PresetListOptionBar
import com.dogeby.core.ui.component.preset.PresetListOptionBarUiState
import com.dogeby.core.ui.component.preset.PresetListUiState
import com.dogeby.core.ui.component.preset.presetList
import com.dogeby.core.ui.theme.RelicCalculatorTheme
import com.dogeby.reliccalculator.core.domain.model.AffixWeightWithInfo
import com.dogeby.reliccalculator.core.domain.model.AttrComparisonWithInfo
import com.dogeby.reliccalculator.core.domain.model.PresetWithDetails
import com.dogeby.reliccalculator.core.model.hoyo.index.RelicPiece
import com.dogeby.reliccalculator.core.model.hoyo.index.sampleCharacterInfo
import com.dogeby.reliccalculator.core.model.hoyo.index.sampleElementInfo
import com.dogeby.reliccalculator.core.model.hoyo.index.samplePathInfo
import com.dogeby.reliccalculator.core.model.hoyo.index.samplePropertyInfo
import com.dogeby.reliccalculator.core.model.hoyo.index.sampleRelicSetInfo
import com.dogeby.reliccalculator.core.model.preferences.CharacterSortField
import com.dogeby.reliccalculator.core.model.preferences.PresetListPreferencesData
import com.dogeby.reliccalculator.core.model.preset.AffixWeight
import com.dogeby.reliccalculator.core.model.preset.AttrComparison
import com.dogeby.reliccalculator.core.model.preset.ComparisonOperator

@Composable
fun PresetsRoute(
    navigateToPresetEdit: (id: String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PresetsViewModel = hiltViewModel(),
) {
    val presetsOptionBarUiState by viewModel
        .presetsOptionBarUiState
        .collectAsStateWithLifecycle()
    val presetsUiState by viewModel.presetsUiState.collectAsStateWithLifecycle()

    PresetsScreen(
        presetListOptionBarUiState = presetsOptionBarUiState,
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
    presetListOptionBarUiState: PresetListOptionBarUiState,
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
                        PresetListOptionBar(
                            presetListOptionBarUiState = presetListOptionBarUiState,
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
                modifier = modifier.fillMaxSize(),
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

@Composable
private fun LoadingState(modifier: Modifier = Modifier) {
    LinearProgressIndicator(modifier.fillMaxWidth())
}

@Composable
private fun EmptyState(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(56.dp))
        Text(
            text = stringResource(id = R.string.empty_list),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
        )
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
            presetListOptionBarUiState = PresetListOptionBarUiState.Success(
                pathInfoList = List(7) { samplePathInfo.copy(id = "$it", name = "$it") },
                elementInfoList = List(7) { sampleElementInfo.copy(id = "$it", name = "$it") },
                presetListPreferencesData = PresetListPreferencesData(
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
