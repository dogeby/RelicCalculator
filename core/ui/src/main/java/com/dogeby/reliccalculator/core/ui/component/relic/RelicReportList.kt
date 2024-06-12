package com.dogeby.reliccalculator.core.ui.component.relic

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dogeby.reliccalculator.core.ui.component.stat.RelicMainStatRowUiState
import com.dogeby.reliccalculator.core.ui.component.stat.RelicSubStatListUiState
import com.dogeby.reliccalculator.core.ui.component.stat.RelicSubStatRowUiState
import com.dogeby.reliccalculator.core.ui.theme.RelicCalculatorTheme

fun LazyGridScope.relicReportList(relicReportListUiState: RelicReportListUiState) {
    when (relicReportListUiState) {
        RelicReportListUiState.Loading -> Unit
        is RelicReportListUiState.Success -> {
            items(relicReportListUiState.relicReports) {
                RelicReportCard(
                    relicReportCardUiState = it,
                    modifier = Modifier.size(344.dp, 232.dp),
                )
            }
        }
    }
}

sealed interface RelicReportListUiState {

    data object Loading : RelicReportListUiState

    data class Success(
        val relicReports: List<RelicReportCardUiState>,
    ) : RelicReportListUiState
}

@Preview(apiLevel = 33)
@Composable
private fun PreviewRelicReportList() {
    RelicCalculatorTheme {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(308.dp),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            relicReportList(
                relicReportListUiState = RelicReportListUiState.Success(
                    relicReports = List(6) {
                        RelicReportCardUiState(
                            relicName = "Taikiyan's Arclight Race Track",
                            relicIconSrc = "icon/relic/309_1.png",
                            level = 15,
                            score = 5.0f,
                            relicMainStatRowUiState = RelicMainStatRowUiState(
                                iconSrc = "icon/property/IconCriticalChance.png",
                                name = "CRIT Rate",
                                display = "100%",
                                weight = 0.5f,
                            ),
                            relicSubStatListUiState = RelicSubStatListUiState.Success(
                                relicSubStats = List(4) {
                                    RelicSubStatRowUiState(
                                        iconSrc = "icon/property/IconCriticalChance.png",
                                        name = "CRIT Rate",
                                        display = "100%",
                                        weight = 0.25f * it,
                                        count = 3,
                                    )
                                },
                            ),
                        )
                    },
                ),
            )
        }
    }
}
