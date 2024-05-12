package com.dogeby.reliccalculator.core.ui.component.relic

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dogeby.reliccalculator.core.ui.component.VerticalGameImageText
import com.dogeby.reliccalculator.core.ui.theme.RelicCalculatorTheme

@Composable
fun CharacterRelicRatingList(
    characterRelicRatingListUiState: CharacterRelicRatingListUiState,
    modifier: Modifier = Modifier,
) {
    when (characterRelicRatingListUiState) {
        CharacterRelicRatingListUiState.Loading -> Unit
        is CharacterRelicRatingListUiState.Success -> {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = modifier,
            ) {
                items(
                    items = characterRelicRatingListUiState.cavernRelics,
                    key = { it.id },
                ) {
                    VerticalGameImageText(
                        src = it.icon,
                        text = String.format("%.1f", it.score),
                        backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
                    )
                }
                items(
                    items = characterRelicRatingListUiState.planarOrnaments,
                    key = { it.id },
                ) {
                    VerticalGameImageText(
                        src = it.icon,
                        text = String.format("%.1f", it.score),
                        backgroundColor = MaterialTheme.colorScheme.tertiaryContainer,
                    )
                }
            }
        }
    }
}

sealed interface CharacterRelicRatingListUiState {

    data object Loading : CharacterRelicRatingListUiState

    data class Success(
        val cavernRelics: List<RelicRatingUiState>,
        val planarOrnaments: List<RelicRatingUiState>,
    ) : CharacterRelicRatingListUiState
}

data class RelicRatingUiState(
    val id: String,
    val icon: String,
    val score: Float,
)

@Preview(apiLevel = 33)
@Composable
private fun PreviewCharacterRelicRatingList() {
    RelicCalculatorTheme {
        CharacterRelicRatingList(
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
}
