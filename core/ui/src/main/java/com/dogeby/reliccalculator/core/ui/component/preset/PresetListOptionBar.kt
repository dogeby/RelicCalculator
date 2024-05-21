package com.dogeby.reliccalculator.core.ui.component.preset

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dogeby.reliccalculator.core.model.mihomo.index.ElementInfo
import com.dogeby.reliccalculator.core.model.mihomo.index.PathInfo
import com.dogeby.reliccalculator.core.model.mihomo.index.sampleElementInfo
import com.dogeby.reliccalculator.core.model.mihomo.index.samplePathInfo
import com.dogeby.reliccalculator.core.model.preferences.CharacterListPreferencesData
import com.dogeby.reliccalculator.core.model.preferences.CharacterSortField
import com.dogeby.reliccalculator.core.ui.component.character.CharacterFilterChip
import com.dogeby.reliccalculator.core.ui.component.character.CharacterSortFieldChip
import com.dogeby.reliccalculator.core.ui.theme.RelicCalculatorTheme

@Composable
fun PresetListOptionBar(
    presetListOptionBarUiState: PresetListOptionBarUiState,
    onSetSortField: (CharacterSortField) -> Unit,
    onConfirmFilters: (
        selectedRarities: Set<Int>,
        selectedPathIds: Set<String>,
        selectedElementIds: Set<String>,
    ) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (presetListOptionBarUiState) {
        PresetListOptionBarUiState.Loading -> Unit
        is PresetListOptionBarUiState.Success -> {
            with(presetListOptionBarUiState) {
                Row(
                    modifier = modifier.horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    CharacterSortFieldChip(
                        selectedSortField = characterListPreferencesData.sortField,
                        onSetSortField = onSetSortField,
                    )
                    CharacterFilterChip(
                        pathInfoList = pathInfoList,
                        elementInfoList = elementInfoList,
                        filteredRarities = characterListPreferencesData.filteredRarities,
                        filteredPathIds = characterListPreferencesData.filteredPathIds,
                        filteredElementIds = characterListPreferencesData.filteredElementIds,
                        onConfirmFilters = onConfirmFilters,
                    )
                }
            }
        }
    }
}

sealed interface PresetListOptionBarUiState {

    data object Loading : PresetListOptionBarUiState

    data class Success(
        val pathInfoList: List<PathInfo>,
        val elementInfoList: List<ElementInfo>,
        val characterListPreferencesData: CharacterListPreferencesData,
    ) : PresetListOptionBarUiState
}

@Preview(apiLevel = 33)
@Composable
private fun PreviewPresetListOptionBar() {
    RelicCalculatorTheme {
        PresetListOptionBar(
            presetListOptionBarUiState = PresetListOptionBarUiState.Success(
                pathInfoList = List(7) { samplePathInfo.copy(id = "$it", name = "$it") },
                elementInfoList = List(7) { sampleElementInfo.copy(id = "$it", name = "$it") },
                characterListPreferencesData = CharacterListPreferencesData(
                    filteredRarities = setOf(5),
                    filteredPathIds = setOf("0"),
                    filteredElementIds = setOf("0"),
                    sortField = CharacterSortField.ID_ASC,
                ),
            ),
            onSetSortField = {},
            onConfirmFilters = { _, _, _ -> },
        )
    }
}
