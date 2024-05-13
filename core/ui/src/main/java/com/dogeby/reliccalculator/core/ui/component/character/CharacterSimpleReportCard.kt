package com.dogeby.reliccalculator.core.ui.component.character

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dogeby.reliccalculator.core.ui.component.relic.CharacterRelicRatingList
import com.dogeby.reliccalculator.core.ui.component.relic.CharacterRelicRatingListUiState
import com.dogeby.reliccalculator.core.ui.component.relic.RelicRatingUiState
import com.dogeby.reliccalculator.core.ui.theme.RelicCalculatorTheme
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

@Composable
fun CharacterSimpleReportCard(
    characterSimpleReportCardUiState: CharacterSimpleReportCardUiState,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(4.dp),
    color: Color = MaterialTheme.colorScheme.surface,
) {
    Surface(
        modifier = modifier,
        shape = shape,
        color = color,
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            CharacterListItemWithRating(
                characterName = characterSimpleReportCardUiState.characterName,
                characterIcon = characterSimpleReportCardUiState.characterIcon,
                updatedDate = characterSimpleReportCardUiState.updatedDate,
                score = characterSimpleReportCardUiState.score,
            )
            CharacterRelicRatingList(
                characterRelicRatingListUiState = characterSimpleReportCardUiState
                    .characterRelicRatingListUiState,
            )
        }
    }
}

data class CharacterSimpleReportCardUiState(
    val id: String,
    val characterName: String,
    val characterIcon: String,
    val updatedDate: Instant,
    val score: Float,
    val characterRelicRatingListUiState: CharacterRelicRatingListUiState,
)

@Preview(apiLevel = 33)
@Composable
private fun PreviewCharacterSimpleReportCard() {
    RelicCalculatorTheme {
        CharacterSimpleReportCard(
            characterSimpleReportCardUiState = CharacterSimpleReportCardUiState(
                id = "",
                characterName = "name",
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
            ),
            modifier = Modifier.padding(8.dp),
        )
    }
}
