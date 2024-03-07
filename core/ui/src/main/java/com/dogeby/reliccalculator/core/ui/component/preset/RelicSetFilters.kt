package com.dogeby.reliccalculator.core.ui.component.preset

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dogeby.reliccalculator.core.model.mihomo.index.RelicSetInfo
import com.dogeby.reliccalculator.core.model.mihomo.index.sampleRelicSetInfo
import com.dogeby.reliccalculator.core.ui.component.FilterChipWithRichTooltip
import com.dogeby.reliccalculator.core.ui.component.image.GameImage
import com.dogeby.reliccalculator.core.ui.theme.RelicCalculatorTheme

@OptIn(ExperimentalMaterial3Api::class)
fun LazyGridScope.relicSetFilters(
    relicSetFiltersUiState: RelicSetFiltersUiState,
    onFilterChipSelectedChanged: (selected: Boolean) -> Unit,
) {
    when (relicSetFiltersUiState) {
        RelicSetFiltersUiState.Loading -> Unit
        is RelicSetFiltersUiState.Success -> {
            items(relicSetFiltersUiState.relicSets) { relicSetFilterUiState ->
                FilterChipWithRichTooltip(
                    selected = { relicSetFilterUiState.selected },
                    onClick = { onFilterChipSelectedChanged(!relicSetFilterUiState.selected) },
                    label = {
                        GameImage(
                            src = relicSetFilterUiState.relicSetInfo.icon,
                            modifier = Modifier
                                .padding(vertical = 4.dp)
                                .size(32.dp),
                        )
                    },
                    tooltipTitle = {
                        Text(
                            text = relicSetFilterUiState.relicSetInfo.name,
                            style = MaterialTheme.typography.titleMedium,
                        )
                    },
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(2.dp),
                    ) {
                        relicSetFilterUiState.relicSetInfo.desc.forEach {
                            Text(text = it)
                        }
                    }
                }
            }
        }
    }
}

sealed interface RelicSetFiltersUiState {

    data object Loading : RelicSetFiltersUiState

    data class Success(
        val relicSets: List<RelicSetFilterUiState>,
    ) : RelicSetFiltersUiState
}

data class RelicSetFilterUiState(
    val selected: Boolean,
    val relicSetInfo: RelicSetInfo,
)

@Preview(apiLevel = 33)
@Composable
fun PreviewRelicSetFilters() {
    RelicCalculatorTheme {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(64.dp),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            relicSetFilters(
                relicSetFiltersUiState = RelicSetFiltersUiState.Success(
                    List(12) {
                        RelicSetFilterUiState(
                            selected = it % 2 == 0,
                            relicSetInfo = sampleRelicSetInfo.copy(id = "$it"),
                        )
                    },
                ),
                onFilterChipSelectedChanged = {},
            )
        }
    }
}
