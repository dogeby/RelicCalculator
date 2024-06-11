package com.dogeby.reliccalculator.core.ui.component.stat

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.dogeby.reliccalculator.core.ui.theme.RelicCalculatorTheme

fun LazyGridScope.characterStatList(characterStatListUiState: CharacterStatListUiState) {
    when (characterStatListUiState) {
        CharacterStatListUiState.Loading -> Unit
        is CharacterStatListUiState.Success -> {
            items(
                items = characterStatListUiState.characterStats,
            ) {
                CharacterStatRow(
                    characterStatRowUiState = it,
                )
            }
        }
    }
}

sealed interface CharacterStatListUiState {

    data object Loading : CharacterStatListUiState

    data class Success(
        val characterStats: List<CharacterStatRowUiState>,
    ) : CharacterStatListUiState
}

@Preview(apiLevel = 33)
@Composable
private fun PreviewCharacterStatList() {
    RelicCalculatorTheme {
        val characterStatListUiState = CharacterStatListUiState.Success(
            characterStats = List(9) {
                CharacterStatRowUiState(
                    iconSrc = "icon/property/IconCriticalChance.png",
                    name = "CRIT Rate",
                    display = "5.0",
                    isPercent = true,
                    comparedDisplay = "3.0",
                    comparisonOperatorSymbol = ">=",
                    isComparisonPass = true,
                )
            },
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
        ) {
            characterStatList(characterStatListUiState)
        }
    }
}
