package com.dogeby.reliccalculator.core.ui.component.preset

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dogeby.reliccalculator.core.ui.component.image.GameImageWithBackground
import com.dogeby.reliccalculator.core.ui.theme.RelicCalculatorTheme

@Composable
fun AffixWeightEditItem(
    name: String,
    weight: () -> Float,
    icon: String,
    onWeightChangeFinished: (Float) -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(4.dp),
    color: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = contentColorFor(color),
    tonalElevation: Dp = 0.dp,
    shadowElevation: Dp = 0.dp,
) {
    Surface(
        modifier = modifier,
        shape = shape,
        color = color,
        contentColor = contentColor,
        tonalElevation = tonalElevation,
        shadowElevation = shadowElevation,
    ) {
        Row(
            modifier = Modifier
                .padding(
                    horizontal = 16.dp,
                    vertical = 8.dp,
                ),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val iconBackgroundColor = MaterialTheme.colorScheme.primaryContainer
            GameImageWithBackground(
                src = icon,
                backgroundColor = iconBackgroundColor,
                imageSize = 48.dp,
                imageColorFilter = ColorFilter.tint(contentColorFor(iconBackgroundColor)),
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.bodyLarge,
                )
                AffixWeightSlider(
                    weight = weight,
                    onValueChangeFinished = onWeightChangeFinished,
                )
            }
        }
    }
}

@Composable
fun AffixWeightSlider(
    weight: () -> Float,
    onValueChangeFinished: (Float) -> Unit,
    modifier: Modifier = Modifier,
) {
    var sliderPosition by remember(weight) {
        mutableFloatStateOf("%.2f".format(weight()).toFloat())
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "$sliderPosition",
            modifier = Modifier.width(60.dp),
            style = MaterialTheme.typography.bodyMedium,
        )
        Spacer(modifier = Modifier.width(4.dp))
        Slider(
            value = sliderPosition,
            onValueChange = {
                sliderPosition = "%.2f".format(it).toFloat()
            },
            onValueChangeFinished = {
                onValueChangeFinished(sliderPosition)
            },
            steps = 19,
        )
    }
}

@Preview(apiLevel = 33)
@Composable
private fun PreviewAffixWeightEditItem() {
    RelicCalculatorTheme {
        AffixWeightEditItem(
            name = "name",
            weight = { 0f },
            icon = "icon/property/IconSpeed.png",
            onWeightChangeFinished = {},
        )
    }
}
