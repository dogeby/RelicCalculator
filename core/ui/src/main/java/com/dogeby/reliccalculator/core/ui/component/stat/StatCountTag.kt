package com.dogeby.reliccalculator.core.ui.component.stat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dogeby.reliccalculator.core.ui.component.image.GameImage
import com.dogeby.reliccalculator.core.ui.theme.RelicCalculatorTheme

@Composable
fun StatCountTag(
    iconSrc: String,
    name: String,
    count: Int,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(8.dp),
    color: Color = MaterialTheme.colorScheme.surface,
) {
    Surface(
        modifier = modifier,
        shape = shape,
        color = color,
    ) {
        Row(
            modifier = modifier.padding(start = 4.dp, top = 4.dp, end = 8.dp, bottom = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            GameImage(
                src = iconSrc,
                modifier = Modifier.size(16.dp),
                colorFilter = ColorFilter.tint(contentColorFor(color)),
            )
            Text(
                text = "$name · $count",
                style = MaterialTheme.typography.labelMedium,
            )
        }
    }
}

@Preview(apiLevel = 33)
@Composable
private fun PreviewStatCountTag() {
    RelicCalculatorTheme {
        StatCountTag(
            iconSrc = "icon/property/IconCriticalChance.png",
            name = "CRIT Rate",
            count = 32,
            color = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp),
        )
    }
}
