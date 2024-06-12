package com.dogeby.reliccalculator.core.ui.component.stat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dogeby.reliccalculator.core.ui.component.character.CharacterListItemWithRating
import com.dogeby.reliccalculator.core.ui.theme.RelicCalculatorTheme
import kotlinx.datetime.Instant

@Composable
fun CharacterStatCard(
    characterStatCardUiState: CharacterStatCardUiState,
    modifier: Modifier = Modifier,
    shape: Shape = CardDefaults.shape,
    colors: CardColors = CardDefaults.cardColors(),
) {
    Card(
        modifier = modifier,
        shape = shape,
        colors = colors,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            with(characterStatCardUiState) {
                Surface(tonalElevation = 1.dp) {
                    CharacterListItemWithRating(
                        characterName = characterName,
                        characterIcon = characterIconSrc,
                        updatedDate = updatedDate,
                        score = characterScore,
                    )
                }
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    characterStatList(characterStatListUiState)
                }
            }
        }
    }
}

data class CharacterStatCardUiState(
    val characterName: String,
    val characterIconSrc: String,
    val updatedDate: Instant,
    val characterScore: Float,
    val characterStatListUiState: CharacterStatListUiState,
)

@Preview(apiLevel = 33)
@Composable
private fun PreviewCharacterStatCard() {
    RelicCalculatorTheme {
        val characterStatListUiState = CharacterStatListUiState.Success(
            characterStats = List(9) {
                CharacterStatRowUiState(
                    iconSrc = "icon/property/IconCriticalChance.png",
                    name = "CRIT Rate",
                    display = "5.0%",
                    comparedDisplay = "3.0",
                    comparisonOperatorSymbol = ">=",
                    isComparisonPass = true,
                )
            },
        )

        CharacterStatCard(
            characterStatCardUiState = CharacterStatCardUiState(
                characterName = "name",
                characterIconSrc = "icon/character/1107.png",
                updatedDate = Instant.parse("2023-06-01T22:19:44.475Z"),
                characterScore = 4.5f,
                characterStatListUiState = characterStatListUiState,
            ),
            modifier = Modifier.padding(8.dp),
        )
    }
}
