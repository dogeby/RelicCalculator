package com.dogeby.reliccalculator.core.ui.component.lightcone

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dogeby.reliccalculator.core.ui.R
import com.dogeby.reliccalculator.core.ui.component.image.GameImage
import com.dogeby.reliccalculator.core.ui.theme.FiveStarRarityColor
import com.dogeby.reliccalculator.core.ui.theme.FourStarRarityColor
import com.dogeby.reliccalculator.core.ui.theme.RelicCalculatorTheme
import com.dogeby.reliccalculator.core.ui.theme.ThreeStarRarityColor

@Composable
fun LightConeCard(
    lightConeCardUiState: LightConeCardUiState,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        with(lightConeCardUiState) {
            GameImage(
                src = lightConeCardUiState.iconSrc,
                modifier = Modifier
                    .size(54.dp)
                    .clip(CircleShape),
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    text = lightConeCardUiState.name,
                    color = when (rarity) {
                        3 -> {
                            ThreeStarRarityColor
                        }
                        4 -> {
                            FourStarRarityColor
                        }
                        5 -> {
                            FiveStarRarityColor
                        }
                        else -> {
                            Color.Unspecified
                        }
                    },
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = "Lv.$level ${stringResource(id = R.string.superimposition, rank)} ",
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = MaterialTheme.typography.labelSmall,
                )
            }
        }
    }
}

data class LightConeCardUiState(
    val name: String,
    val iconSrc: String,
    val rarity: Int,
    val rank: Int,
    val level: Int,
)

@Preview(apiLevel = 33)
@Composable
fun PreviewLightConeCard() {
    RelicCalculatorTheme {
        LightConeCard(
            lightConeCardUiState = LightConeCardUiState(
                name = "On the Fall of an Aeon",
                iconSrc = "icon/light_cone/24000.png",
                rarity = 5,
                rank = 5,
                level = 80,
            ),
        )
    }
}
