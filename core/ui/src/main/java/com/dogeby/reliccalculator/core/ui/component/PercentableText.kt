package com.dogeby.reliccalculator.core.ui.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.dogeby.reliccalculator.core.ui.theme.RelicCalculatorTheme

@Composable
fun PercentableText(
    text: String,
    isPercent: Boolean,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
) {
    Text(
        text = if (isPercent) "$text%" else text,
        modifier = modifier,
        color = color,
        style = style,
    )
}

@Preview(apiLevel = 33)
@Composable
private fun PreviewPercentableText() {
    RelicCalculatorTheme {
        PercentableText(
            text = "50",
            isPercent = true,
        )
    }
}
