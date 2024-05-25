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
import com.dogeby.reliccalculator.core.ui.component.relic.CharRelicRatingListUiState
import com.dogeby.reliccalculator.core.ui.component.relic.RelicRatingUiState
import com.dogeby.reliccalculator.core.ui.theme.RelicCalculatorTheme
import kotlinx.datetime.Clock

fun LazyGridScope.characterSimpleReportCardList(
    charSimpleReportCardListUiState: CharSimpleReportCardListUiState,
    onCardClick: (id: Int, characterId: String) -> Unit,
) {
    when (charSimpleReportCardListUiState) {
        CharSimpleReportCardListUiState.Loading -> Unit
        is CharSimpleReportCardListUiState.Success -> {
            items(
                items = charSimpleReportCardListUiState.characterSimpleReportCards,
                key = { it.id },
            ) {
                CharacterSimpleReportCard(
                    charSimpleReportCardUiState = it,
                    modifier = Modifier.clickable {
                        onCardClick(it.id, it.characterId)
                    },
                )
            }
        }
    }
}

sealed interface CharSimpleReportCardListUiState {

    data object Loading : CharSimpleReportCardListUiState

    data class Success(
        val characterSimpleReportCards: List<CharSimpleReportCardUiState>,
    ) : CharSimpleReportCardListUiState
}

@Preview(apiLevel = 33)
@Composable
private fun PreviewCharacterSimpleReportCardList() {
    val characterSimpleReportCards = List(6) {
        CharSimpleReportCardUiState(
            id = it,
            characterId = "$it",
            characterName = "name $it",
            characterIcon = "icon/character/1107.png",
            updatedDate = Clock.System.now(),
            score = 5.0f,
            charRelicRatingListUiState = CharRelicRatingListUiState.Success(
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
                charSimpleReportCardListUiState = CharSimpleReportCardListUiState
                    .Success(characterSimpleReportCards),
                onCardClick = { _, _ -> },
            )
        }
    }
}
