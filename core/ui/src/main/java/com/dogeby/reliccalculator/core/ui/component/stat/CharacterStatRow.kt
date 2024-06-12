package com.dogeby.reliccalculator.core.ui.component.stat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
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
import com.dogeby.reliccalculator.core.ui.theme.comparisonFailureColor
import com.dogeby.reliccalculator.core.ui.theme.comparisonSuccessColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterStatRow(
    characterStatRowUiState: CharacterStatRowUiState,
    modifier: Modifier = Modifier,
    colors: CharacterStatRowColors = CharacterStatRowColors.default(),
) {
    Surface(
        modifier = modifier,
        color = colors.backgroundColor,
    ) {
        with(characterStatRowUiState) {
            TooltipBox(
                positionProvider = TooltipDefaults.rememberRichTooltipPositionProvider(),
                tooltip = {
                    if (isComparisonPass != null) {
                        PlainTooltip {
                            Text(text = "$name $comparisonOperatorSymbol $comparedDisplay")
                        }
                    }
                },
                state = rememberTooltipState(isPersistent = false),
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    val (iconColor, textColor) = if (isComparisonPass == null) {
                        colors.defaultIconColor to colors.defaultTextColor
                    } else {
                        val contentColor = if (isComparisonPass) {
                            colors.successContentColor
                        } else {
                            colors.failureContentColor
                        }
                        contentColor to contentColor
                    }

                    GameImage(
                        src = iconSrc,
                        modifier = Modifier.size(24.dp),
                        colorFilter = ColorFilter.tint(iconColor),
                    )
                    Text(
                        text = name,
                        modifier = Modifier.weight(1f),
                        color = textColor,
                    )
                    Text(
                        text = display,
                        color = textColor,
                    )
                }
            }
        }
    }
}

data class CharacterStatRowUiState(
    val iconSrc: String,
    val name: String,
    val display: String,
    val comparedDisplay: String? = null,
    val comparisonOperatorSymbol: String? = null,
    val isComparisonPass: Boolean? = null,
)

@Immutable
data class CharacterStatRowColors(
    val backgroundColor: Color,
    val defaultIconColor: Color,
    val defaultTextColor: Color,
    val successContentColor: Color,
    val failureContentColor: Color,
) {

    companion object {

        @Composable
        fun default(
            backgroundColor: Color = Color.Transparent,
            defaultIconColor: Color = MaterialTheme.colorScheme.onSurface,
            defaultTextColor: Color = Color.Unspecified,
            successContentColor: Color = comparisonSuccessColor,
            failureContentColor: Color = comparisonFailureColor,
        ): CharacterStatRowColors = CharacterStatRowColors(
            backgroundColor = backgroundColor,
            defaultIconColor = defaultIconColor,
            defaultTextColor = defaultTextColor,
            successContentColor = successContentColor,
            failureContentColor = failureContentColor,
        )
    }
}

@Preview(apiLevel = 33)
@Composable
fun PreviewCharacterStatRow_default() {
    RelicCalculatorTheme {
        CharacterStatRow(
            characterStatRowUiState = CharacterStatRowUiState(
                iconSrc = "icon/property/IconCriticalChance.png",
                name = "CRIT Rate",
                display = "5.0%",
            ),
            modifier = Modifier.width(160.dp),
        )
    }
}

@Preview(apiLevel = 33)
@Composable
fun PreviewCharacterStatRow_attrComparison_success() {
    RelicCalculatorTheme {
        CharacterStatRow(
            characterStatRowUiState = CharacterStatRowUiState(
                iconSrc = "icon/property/IconCriticalChance.png",
                name = "CRIT Rate",
                display = "5.0%",
                comparedDisplay = "3.0%",
                comparisonOperatorSymbol = ">=",
                isComparisonPass = true,
            ),
            modifier = Modifier.width(160.dp),
        )
    }
}

@Preview(apiLevel = 33)
@Composable
fun PreviewCharacterStatRow_attrComparison_fail() {
    RelicCalculatorTheme {
        CharacterStatRow(
            characterStatRowUiState = CharacterStatRowUiState(
                iconSrc = "icon/property/IconCriticalChance.png",
                name = "CRIT Rate",
                display = "5.0%",
                comparedDisplay = "6.0%",
                comparisonOperatorSymbol = ">=",
                isComparisonPass = false,
            ),
            modifier = Modifier.width(160.dp),
        )
    }
}
