package com.dogeby.reliccalculator.core.ui.component.relic

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dogeby.reliccalculator.core.ui.component.Rating
import com.dogeby.reliccalculator.core.ui.component.image.GameImage
import com.dogeby.reliccalculator.core.ui.component.stat.RelicMainStatRow
import com.dogeby.reliccalculator.core.ui.component.stat.RelicMainStatRowUiState
import com.dogeby.reliccalculator.core.ui.component.stat.RelicSubStatListUiState
import com.dogeby.reliccalculator.core.ui.component.stat.RelicSubStatRowUiState
import com.dogeby.reliccalculator.core.ui.component.stat.relicSubStatList
import com.dogeby.reliccalculator.core.ui.theme.RelicCalculatorTheme

@Composable
fun RelicReportCard(
    relicReportCardUiState: RelicReportCardUiState,
    modifier: Modifier = Modifier,
    shape: Shape = CardDefaults.shape,
    colors: CardColors = CardDefaults.cardColors(),
) {
    Card(
        modifier = modifier,
        shape = shape,
        colors = colors,
    ) {
        LazyColumn(
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            with(relicReportCardUiState) {
                item {
                    RelicInfoRow(
                        iconSrc = relicIconSrc,
                        name = relicName,
                        level = level,
                        score = score,
                    )
                }
                item {
                    RelicMainStatRow(
                        relicMainStatRowUiState = relicReportCardUiState.relicMainStatRowUiState,
                    )
                }
                relicSubStatList(relicReportCardUiState.relicSubStatListUiState)
            }
        }
    }
}

data class RelicReportCardUiState(
    val relicName: String,
    val relicIconSrc: String,
    val level: Int,
    val score: Float,
    val relicMainStatRowUiState: RelicMainStatRowUiState,
    val relicSubStatListUiState: RelicSubStatListUiState,
)

@Composable
fun RelicInfoRow(
    iconSrc: String,
    name: String,
    level: Int,
    score: Float,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        GameImage(
            src = iconSrc,
            modifier = Modifier.size(32.dp),
        )
        Spacer(modifier = Modifier.width(24.dp))
        Text(
            text = name,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.titleMedium,
        )
        Text(
            text = "+$level",
            modifier = Modifier.widthIn(min = 30.dp),
            maxLines = 1,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Rating(
            rating = score,
            color = MaterialTheme.colorScheme.primaryContainer,
        )
    }
}

@Preview(apiLevel = 33)
@Composable
private fun PreviewRelicReportCard() {
    RelicCalculatorTheme {
        RelicReportCard(
            relicReportCardUiState = RelicReportCardUiState(
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
            ),
            modifier = Modifier.padding(8.dp),
        )
    }
}

@Preview(apiLevel = 33)
@Composable
private fun PreviewRelicInfoRow() {
    RelicCalculatorTheme {
        RelicInfoRow(
            iconSrc = "icon/relic/309_1.png",
            name = "Taikiyan's Arclight Race Track",
            level = 15,
            score = 4.5f,
            modifier = Modifier.padding(8.dp),
        )
    }
}
