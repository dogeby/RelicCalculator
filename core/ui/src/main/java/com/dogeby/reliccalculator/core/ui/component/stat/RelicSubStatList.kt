package com.dogeby.reliccalculator.core.ui.component.stat

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.dogeby.reliccalculator.core.ui.theme.RelicCalculatorTheme

fun LazyListScope.relicSubStatList(relicSubStatListUiState: RelicSubStatListUiState) {
    when (relicSubStatListUiState) {
        RelicSubStatListUiState.Loading -> Unit
        is RelicSubStatListUiState.Success -> {
            items(relicSubStatListUiState.relicSubStats) {
                RelicSubStatRow(relicSubStatRowUiState = it)
            }
        }
    }
}

sealed interface RelicSubStatListUiState {

    data object Loading : RelicSubStatListUiState

    data class Success(
        val relicSubStats: List<RelicSubStatRowUiState>,
    ) : RelicSubStatListUiState
}

@Preview(apiLevel = 33)
@Composable
private fun PreviewRelicSubStatList() {
    RelicCalculatorTheme {
        LazyColumn {
            relicSubStatList(
                relicSubStatListUiState = RelicSubStatListUiState.Success(
                    relicSubStats = List(4) {
                        RelicSubStatRowUiState(
                            iconSrc = "icon/property/IconCriticalChance.png",
                            name = "CRIT Rate",
                            display = "100%",
                            weight = 0.0f,
                            count = 3,
                        )
                    },
                ),
            )
        }
    }
}
