package com.dogeby.reliccalculator.core.ui.component.image

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.RichTooltip
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.TooltipState
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dogeby.reliccalculator.core.ui.theme.RelicCalculatorTheme
import kotlinx.coroutines.launch

@Composable
fun GameImageWithBackground(
    src: String,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(4.dp),
    backgroundColor: Color = Color.Transparent,
    imageSize: Dp = 24.dp,
    imageContentScale: ContentScale = ContentScale.Fit,
    imageColorFilter: ColorFilter? = null,
    tonalElevation: Dp = 0.dp,
    shadowElevation: Dp = 0.dp,
) {
    Surface(
        modifier = modifier,
        shape = shape,
        color = backgroundColor,
        tonalElevation = tonalElevation,
        shadowElevation = shadowElevation,
    ) {
        GameImage(
            src = src,
            modifier = Modifier
                .padding(4.dp)
                .size(size = imageSize),
            contentScale = imageContentScale,
            colorFilter = imageColorFilter,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameImageWithTooltipAndBackground(
    src: String,
    tooltipContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(4.dp),
    backgroundColor: Color = Color.Transparent,
    imageSize: Dp = 24.dp,
    imageContentScale: ContentScale = ContentScale.Fit,
    imageColorFilter: ColorFilter? = null,
    tonalElevation: Dp = 0.dp,
    shadowElevation: Dp = 0.dp,
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
    ) {
        GameImageWithBackground(
            src = src,
            modifier = modifier,
            shape = shape,
            backgroundColor = backgroundColor,
            imageSize = imageSize,
            imageContentScale = imageContentScale,
            imageColorFilter = imageColorFilter,
            tonalElevation = tonalElevation,
            shadowElevation = shadowElevation,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameImageWithRichTooltipAndBackground(
    src: String,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(4.dp),
    backgroundColor: Color = Color.Transparent,
    imageSize: Dp = 24.dp,
    imageContentScale: ContentScale = ContentScale.Fit,
    imageColorFilter: ColorFilter? = null,
    tonalElevation: Dp = 0.dp,
    shadowElevation: Dp = 0.dp,
    tooltipState: TooltipState = rememberTooltipState(isPersistent = true),
    tooltipTitle: (@Composable () -> Unit)? = null,
    tooltipAction: (@Composable () -> Unit)? = null,
    tooltipText: @Composable () -> Unit,
) {
    TooltipBox(
        positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
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
        GameImageWithBackground(
            src = src,
            modifier = modifier,
            shape = shape,
            backgroundColor = backgroundColor,
            imageSize = imageSize,
            imageContentScale = imageContentScale,
            imageColorFilter = imageColorFilter,
            tonalElevation = tonalElevation,
            shadowElevation = shadowElevation,
        )
    }
}

@Preview(apiLevel = 33)
@Composable
private fun PreviewGameImageWithBackground() {
    RelicCalculatorTheme {
        GameImageWithBackground(
            src = "icon/relic/103.png",
            backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
        )
    }
}

@Preview(apiLevel = 33)
@Composable
private fun PreviewGameImageWithTooltipAndBackground() {
    RelicCalculatorTheme {
        GameImageWithTooltipAndBackground(
            src = "icon/relic/103.png",
            backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
            tooltipContent = { Text(text = "name") },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(apiLevel = 33)
@Composable
fun PreviewGameImageWithRichTooltipAndBackground() {
    val tooltipState = rememberTooltipState(isPersistent = true)
    val scope = rememberCoroutineScope()

    RelicCalculatorTheme {
        GameImageWithRichTooltipAndBackground(
            src = "icon/relic/103.png",
            backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
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
