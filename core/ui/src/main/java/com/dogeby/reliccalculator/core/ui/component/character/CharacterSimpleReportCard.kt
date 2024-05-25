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
import com.dogeby.reliccalculator.core.ui.component.relic.CharRelicRatingListUiState
import com.dogeby.reliccalculator.core.ui.component.relic.CharacterRelicRatingList
import com.dogeby.reliccalculator.core.ui.component.relic.RelicRatingUiState
import com.dogeby.reliccalculator.core.ui.theme.RelicCalculatorTheme
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

@Composable
fun CharacterSimpleReportCard(
    charSimpleReportCardUiState: CharSimpleReportCardUiState,
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
                characterName = charSimpleReportCardUiState.characterName,
                characterIcon = charSimpleReportCardUiState.characterIcon,
                updatedDate = charSimpleReportCardUiState.updatedDate,
                score = charSimpleReportCardUiState.score,
            )
            CharacterRelicRatingList(
                charRelicRatingListUiState = charSimpleReportCardUiState
                    .charRelicRatingListUiState,
            )
        }
    }
}

data class CharSimpleReportCardUiState(
    val id: Int,
    val characterId: String,
    val characterName: String,
    val characterIcon: String,
    val updatedDate: Instant,
    val score: Float,
    val charRelicRatingListUiState: CharRelicRatingListUiState,
)

@Preview(apiLevel = 33)
@Composable
private fun PreviewCharacterSimpleReportCard() {
    RelicCalculatorTheme {
        CharacterSimpleReportCard(
            charSimpleReportCardUiState = CharSimpleReportCardUiState(
                id = 0,
                characterId = "",
                characterName = "name",
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
            ),
            modifier = Modifier.padding(8.dp),
        )
    }
}
