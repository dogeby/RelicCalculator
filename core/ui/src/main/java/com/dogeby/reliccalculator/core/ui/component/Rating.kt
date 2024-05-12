package com.dogeby.reliccalculator.core.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dogeby.reliccalculator.core.ui.theme.RelicCalculatorTheme

@Composable
fun Rating(
    rating: Float,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(8),
    color: Color = MaterialTheme.colorScheme.surface,
    minRating: Float = 0.0f,
    maxRating: Float = 5.0f,
    style: TextStyle = MaterialTheme.typography.bodyLarge,
) {
    Surface(
        modifier = modifier,
        shape = shape,
        color = color,
    ) {
        Row(
            modifier = Modifier.padding(
                start = 4.dp,
                top = 4.dp,
                end = 8.dp,
                bottom = 4.dp,
            ),
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                modifier = Modifier.size(18.dp),
            )
            Text(
                text = rating
                    .coerceIn(minRating..maxRating)
                    .toString(),
                style = style,
            )
        }
    }
}

@Preview(apiLevel = 33)
@Composable
private fun RatingPreview() {
    RelicCalculatorTheme {
        Rating(
            rating = 6f,
            color = MaterialTheme.colorScheme.primaryContainer,
        )
    }
}
