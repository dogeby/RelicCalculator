package com.dogeby.core.ui.component.preset

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dogeby.core.ui.component.character.CharacterFilterChip
import com.dogeby.core.ui.component.character.CharacterSortFieldChip
import com.dogeby.core.ui.theme.RelicCalculatorTheme
import com.dogeby.reliccalculator.core.model.hoyo.index.ElementInfo
import com.dogeby.reliccalculator.core.model.hoyo.index.PathInfo
import com.dogeby.reliccalculator.core.model.hoyo.index.sampleElementInfo
import com.dogeby.reliccalculator.core.model.hoyo.index.samplePathInfo
import com.dogeby.reliccalculator.core.model.preferences.CharacterSortField

@Composable
fun PresetListOptionBar(
    selectedSortField: CharacterSortField,
    onSetSortField: (CharacterSortField) -> Unit,
    pathInfoList: List<PathInfo>,
    elementInfoList: List<ElementInfo>,
    filteredRarities: Set<Int>,
    filteredPathIds: Set<String>,
    filteredElementIds: Set<String>,
    onConfirmFilters: (
        selectedRarities: Set<Int>,
        selectedPathIds: Set<String>,
        selectedElementIds: Set<String>,
    ) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        CharacterSortFieldChip(
            selectedSortField = selectedSortField,
            onSetSortField = onSetSortField,
        )
        CharacterFilterChip(
            pathInfoList = pathInfoList,
            elementInfoList = elementInfoList,
            filteredRarities = filteredRarities,
            filteredPathIds = filteredPathIds,
            filteredElementIds = filteredElementIds,
            onConfirmFilters = onConfirmFilters,
        )
    }
}

@Preview(apiLevel = 33)
@Composable
private fun PreviewPresetListOptionBar() {
    RelicCalculatorTheme {
        PresetListOptionBar(
            selectedSortField = CharacterSortField.ID_ASC,
            onSetSortField = {},
            pathInfoList = List(7) { samplePathInfo.copy(id = "$it", name = "$it") },
            elementInfoList = List(7) { sampleElementInfo.copy(id = "$it", name = "$it") },
            filteredRarities = setOf(5),
            filteredPathIds = setOf("0"),
            filteredElementIds = setOf("0"),
            onConfirmFilters = { _, _, _ -> },
        )
    }
}
