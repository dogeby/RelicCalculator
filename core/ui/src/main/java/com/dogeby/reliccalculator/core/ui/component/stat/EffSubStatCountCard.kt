package com.dogeby.reliccalculator.core.ui.component.stat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dogeby.reliccalculator.core.ui.R
import com.dogeby.reliccalculator.core.ui.theme.RelicCalculatorTheme

@Composable
fun EffSubStatCountCard(
    effSubStatCountCardUiState: EffSubStatCountCardUiState,
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
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 8.dp,
            ),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(id = R.string.effective_sub_stats),
                    style = MaterialTheme.typography.titleMedium,
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "${stringResource(
                            id = R.string.total,
                        )}: ${effSubStatCountCardUiState.totalCount}",
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            }
            if (effSubStatCountCardUiState.statCountTags.isNotEmpty()) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(effSubStatCountCardUiState.statCountTags) {
                        StatCountTag(statCountTagUiState = it)
                    }
                }
            }
        }
    }
}

data class EffSubStatCountCardUiState(
    val totalCount: Int,
    val statCountTags: List<StatCountTagUiState>,
)

@Preview(apiLevel = 33)
@Composable
private fun PreviewEffSubStatCountCard() {
    RelicCalculatorTheme {
        EffSubStatCountCard(
            effSubStatCountCardUiState = EffSubStatCountCardUiState(
                totalCount = 15,
                statCountTags = List(5) {
                    StatCountTagUiState(
                        iconSrc = "icon/property/IconCriticalChance.png",
                        name = "CRIT Rate",
                        count = 3,
                    )
                },
            ),
            modifier = Modifier.padding(8.dp),
        )
    }
}
