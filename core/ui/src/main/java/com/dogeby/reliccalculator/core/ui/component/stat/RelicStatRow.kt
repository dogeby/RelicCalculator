package com.dogeby.reliccalculator.core.ui.component.stat

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dogeby.reliccalculator.core.ui.component.image.GameImage
import com.dogeby.reliccalculator.core.ui.theme.RelicCalculatorTheme

@Composable
fun RelicMainStatRow(
    relicMainStatRowUiState: RelicMainStatRowUiState,
    modifier: Modifier = Modifier,
    colors: RelicStatRowColors = RelicStatRowColors.default().adjustAlphaByWeight(
        relicMainStatRowUiState.weight,
    ),
) {
    Surface(
        modifier = modifier,
        color = colors.backgroundColor,
    ) {
        with(relicMainStatRowUiState) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                GameImage(
                    src = iconSrc,
                    modifier = Modifier.size(32.dp),
                    colorFilter = ColorFilter.tint(colors.iconColor),
                )
                Spacer(modifier = Modifier.width(24.dp))
                Text(
                    text = name,
                    modifier = Modifier.weight(1f),
                    color = colors.textColor,
                    maxLines = 1,
                )
                Text(
                    text = display,
                    modifier = Modifier.widthIn(min = 75.dp),
                    color = colors.textColor,
                    maxLines = 1,
                )
            }
        }
    }
}

data class RelicMainStatRowUiState(
    val iconSrc: String,
    val name: String,
    val display: String,
    val weight: Float,
)

@Composable
fun RelicSubStatRow(
    relicSubStatRowUiState: RelicSubStatRowUiState,
    modifier: Modifier = Modifier,
    colors: RelicStatRowColors = RelicStatRowColors.default().adjustAlphaByWeight(
        relicSubStatRowUiState.weight,
    ),
) {
    Surface(
        modifier = modifier,
        color = colors.backgroundColor,
    ) {
        with(relicSubStatRowUiState) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                GameImage(
                    src = iconSrc,
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(24.dp),
                    colorFilter = ColorFilter.tint(colors.iconColor),
                )
                Spacer(modifier = Modifier.width(24.dp))
                Text(
                    text = name,
                    modifier = Modifier.weight(1f),
                    color = colors.textColor,
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = "+$count",
                    modifier = Modifier.widthIn(min = 30.dp),
                    color = colors.textColor,
                    maxLines = 1,
                    style = MaterialTheme.typography.labelSmall,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = display,
                    modifier = Modifier.widthIn(min = 75.dp),
                    maxLines = 1,
                    color = colors.textColor,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}

data class RelicSubStatRowUiState(
    val iconSrc: String,
    val name: String,
    val display: String,
    val weight: Float,
    val count: Int,
)

@Immutable
data class RelicStatRowColors(
    val backgroundColor: Color,
    val iconColor: Color,
    val textColor: Color,
) {

    companion object {

        @Composable
        fun default(
            backgroundColor: Color = Color.Transparent,
            iconColor: Color = MaterialTheme.colorScheme.onSurface,
            textColor: Color = Color.Unspecified,
        ): RelicStatRowColors = RelicStatRowColors(
            backgroundColor = backgroundColor,
            iconColor = iconColor,
            textColor = textColor,
        )
    }
}

private fun RelicStatRowColors.adjustAlphaByWeight(weight: Float): RelicStatRowColors {
    val alpha = 0.25f + (weight.coerceIn(0.0f, 1.0f) * 0.75f)
    return copy(
        iconColor = iconColor.copy(alpha = alpha),
        textColor = textColor.copy(alpha = alpha),
    )
}

@Preview(apiLevel = 33)
@Composable
private fun PreviewRelicMainStatRow() {
    RelicCalculatorTheme {
        RelicMainStatRow(
            relicMainStatRowUiState = RelicMainStatRowUiState(
                iconSrc = "icon/property/IconCriticalChance.png",
                name = "CRIT Rate",
                display = "100%",
                weight = 0.5f,
            ),
            modifier = Modifier.width(300.dp),
        )
    }
}

@Preview(apiLevel = 33)
@Composable
private fun PreviewRelicSubStatRow() {
    RelicCalculatorTheme {
        RelicSubStatRow(
            relicSubStatRowUiState = RelicSubStatRowUiState(
                iconSrc = "icon/property/IconCriticalChance.png",
                name = "CRIT Rate",
                display = "100%",
                weight = 0.5f,
                count = 3,
            ),
            modifier = Modifier
                .width(300.dp)
                .padding(start = 4.dp),
        )
    }
}

@Preview(apiLevel = 33)
@Composable
private fun PreviewRelicStatList() {
    RelicCalculatorTheme {
        Column {
            RelicMainStatRow(
                relicMainStatRowUiState = RelicMainStatRowUiState(
                    iconSrc = "icon/property/IconCriticalChance.png",
                    name = "CRIT Rate",
                    display = "100%",
                    weight = 0.5f,
                ),
            )
            Spacer(modifier = Modifier.height(8.dp))
            RelicSubStatRow(
                relicSubStatRowUiState = RelicSubStatRowUiState(
                    iconSrc = "icon/property/IconCriticalChance.png",
                    name = "CRIT Rate",
                    display = "100%",
                    weight = 0.0f,
                    count = 3,
                ),
            )
            Spacer(modifier = Modifier.height(8.dp))
            RelicSubStatRow(
                relicSubStatRowUiState = RelicSubStatRowUiState(
                    iconSrc = "icon/property/IconCriticalChance.png",
                    name = "CRIT Rate",
                    display = "100%",
                    weight = 0.0f,
                    count = 3,
                ),
            )
        }
    }
}
