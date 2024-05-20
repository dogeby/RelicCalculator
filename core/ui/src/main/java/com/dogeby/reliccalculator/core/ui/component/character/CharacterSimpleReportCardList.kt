package com.dogeby.reliccalculator.core.ui.component.character

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dogeby.reliccalculator.core.ui.component.relic.CharacterRelicRatingListUiState
import com.dogeby.reliccalculator.core.ui.component.relic.RelicRatingUiState
import com.dogeby.reliccalculator.core.ui.theme.RelicCalculatorTheme
import kotlinx.datetime.Clock

fun LazyGridScope.characterSimpleReportCardList(
    characterSimpleReportCardListUiState: CharacterSimpleReportCardListUiState,
    onCardClick: (id: String) -> Unit,
) {
    when (characterSimpleReportCardListUiState) {
        CharacterSimpleReportCardListUiState.Loading -> Unit
        is CharacterSimpleReportCardListUiState.Success -> {
            items(
                items = characterSimpleReportCardListUiState.characterSimpleReportCards,
                key = { it.id },
            ) {
                CharacterSimpleReportCard(
                    characterSimpleReportCardUiState = it,
                    modifier = Modifier.clickable {
                        onCardClick(it.id)
                    },
                )
            }
        }
    }
}

sealed interface CharacterSimpleReportCardListUiState {

    data object Loading : CharacterSimpleReportCardListUiState

    data class Success(
        val characterSimpleReportCards: List<CharacterSimpleReportCardUiState>,
    ) : CharacterSimpleReportCardListUiState
}

@Preview(apiLevel = 33)
@Composable
private fun PreviewCharacterSimpleReportCardList() {
    val characterSimpleReportCards = List(6) {
        CharacterSimpleReportCardUiState(
            id = "$it",
            characterName = "name $it",
            characterIcon = "icon/character/1107.png",
            updatedDate = Clock.System.now(),
            score = 5.0f,
            characterRelicRatingListUiState = CharacterRelicRatingListUiState.Success(
                cavernRelics = List(4) {
                    RelicRatingUiState(
                        id = "cavernRelics$it",
                        icon = "icon/relic/311_1.png",
                        score = 4.5f,
                    )
                },
                planarOrnaments = List(2) {
                    RelicRatingUiState(
                        id = "planarOrnaments$it",
                        icon = "icon/relic/311_1.png",
                        score = 2f,
                    )
                },
            ),
        )
    }
    RelicCalculatorTheme {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(360.dp),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            characterSimpleReportCardList(
                characterSimpleReportCardListUiState = CharacterSimpleReportCardListUiState
                    .Success(characterSimpleReportCards),
                onCardClick = {},
            )
        }
    }
}
