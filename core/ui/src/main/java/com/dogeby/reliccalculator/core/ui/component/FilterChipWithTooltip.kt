package com.dogeby.reliccalculator.core.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.RichTooltip
import androidx.compose.material3.SelectableChipColors
import androidx.compose.material3.SelectableChipElevation
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.TooltipState
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dogeby.reliccalculator.core.ui.component.image.GameImage
import com.dogeby.reliccalculator.core.ui.theme.RelicCalculatorTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterChipWithTooltip(
    selected: () -> Boolean,
    onClick: () -> Unit,
    label: @Composable () -> Unit,
    tooltipContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    shape: Shape = FilterChipDefaults.shape,
    colors: SelectableChipColors = FilterChipDefaults.filterChipColors(),
    elevation: SelectableChipElevation? = FilterChipDefaults.filterChipElevation(),
    border: BorderStroke? = FilterChipDefaults.filterChipBorder(enabled, selected()),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    TooltipBox(
        positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
        tooltip = {
            PlainTooltip(
                caretProperties = TooltipDefaults.caretProperties,
                content = tooltipContent,
            )
        },
        state = rememberTooltipState(),
        modifier = modifier,
    ) {
        FilterChip(
            selected = selected(),
            onClick = onClick,
            label = label,
            enabled = enabled,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            shape = shape,
            colors = colors,
            elevation = elevation,
            border = border,
            interactionSource = interactionSource,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterChipWithRichTooltip(
    selected: () -> Boolean,
    onClick: () -> Unit,
    label: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    shape: Shape = FilterChipDefaults.shape,
    colors: SelectableChipColors = FilterChipDefaults.filterChipColors(),
    elevation: SelectableChipElevation? = FilterChipDefaults.filterChipElevation(),
    border: BorderStroke? = FilterChipDefaults.filterChipBorder(enabled, selected()),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    tooltipState: TooltipState = rememberTooltipState(isPersistent = true),
    tooltipTitle: (@Composable () -> Unit)? = null,
    tooltipAction: (@Composable () -> Unit)? = null,
    tooltipText: @Composable () -> Unit,
) {
    TooltipBox(
        positionProvider = TooltipDefaults.rememberRichTooltipPositionProvider(),
        tooltip = {
            RichTooltip(
                title = tooltipTitle,
                action = tooltipAction,
                text = tooltipText,
            )
        },
        state = tooltipState,
        modifier = modifier,
    ) {
        FilterChip(
            selected = selected(),
            onClick = onClick,
            label = label,
            enabled = enabled,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            shape = shape,
            colors = colors,
            elevation = elevation,
            border = border,
            interactionSource = interactionSource,
        )
    }
}

@Preview(apiLevel = 33)
@Composable
fun PreviewFilterChipWithTooltip() {
    var selected by remember {
        mutableStateOf(false)
    }
    RelicCalculatorTheme {
        FilterChipWithTooltip(
            selected = { selected },
            onClick = { selected = !selected },
            label = {
                GameImage(
                    src = "icon/relic/103.png",
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .size(32.dp),
                )
            },
            tooltipContent = { Text(text = "name") },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(apiLevel = 33)
@Composable
fun PreviewFilterChipWithRichTooltip() {
    var selected by remember {
        mutableStateOf(false)
    }
    val tooltipState = rememberTooltipState(isPersistent = true)
    val scope = rememberCoroutineScope()

    RelicCalculatorTheme {
        FilterChipWithRichTooltip(
            selected = { selected },
            onClick = { selected = !selected },
            label = {
                GameImage(
                    src = "icon/relic/103.png",
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .size(32.dp),
                )
            },
            tooltipState = tooltipState,
            tooltipTitle = {
                Text(text = "title")
            },
            tooltipAction = {
                TextButton(
                    onClick = { scope.launch { tooltipState.dismiss() } },
                ) { Text("action") }
            },
        ) {
            Text(text = "text")
        }
    }
}
