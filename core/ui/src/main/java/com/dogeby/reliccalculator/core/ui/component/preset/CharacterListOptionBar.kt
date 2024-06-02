package com.dogeby.reliccalculator.core.ui.component.preset

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
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
fun RowScope.CharacterListOptionBar(
    characterListOptionBarUiState: CharacterListOptionBarUiState,
    onSetSortField: (CharacterSortField) -> Unit,
    onConfirmFilters: (
        selectedRarities: Set<Int>,
        selectedPathIds: Set<String>,
        selectedElementIds: Set<String>,
    ) -> Unit,
) {
    when (characterListOptionBarUiState) {
        CharacterListOptionBarUiState.Loading -> Unit
        is CharacterListOptionBarUiState.Success -> {
            with(characterListOptionBarUiState) {
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

sealed interface CharacterListOptionBarUiState {

    data object Loading : CharacterListOptionBarUiState

    data class Success(
        val pathInfoList: List<PathInfo>,
        val elementInfoList: List<ElementInfo>,
        val characterListPreferencesData: CharacterListPreferencesData,
    ) : CharacterListOptionBarUiState
}

@Preview(apiLevel = 33)
@Composable
private fun PreviewCharacterListOptionBar() {
    RelicCalculatorTheme {
        Row {
            CharacterListOptionBar(
                characterListOptionBarUiState = CharacterListOptionBarUiState.Success(
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
}
