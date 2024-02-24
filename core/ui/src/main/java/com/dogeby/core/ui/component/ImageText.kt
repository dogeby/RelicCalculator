package com.dogeby.core.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dogeby.core.ui.component.image.AffixImage
import com.dogeby.core.ui.component.image.GameImage
import com.dogeby.core.ui.theme.RelicCalculatorTheme

@Composable
fun HorizontalGameImageText(
    src: String,
    text: String,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(4.dp),
    backgroundColor: Color = Color.Transparent,
    imageSize: Dp = 24.dp,
    imageContentScale: ContentScale = ContentScale.Fit,
    imageColorFilter: ColorFilter? = null,
    textStyle: TextStyle = MaterialTheme.typography.labelLarge,
) {
    Surface(
        modifier = modifier,
        shape = shape,
        color = backgroundColor,
    ) {
        Row(
            modifier = Modifier.padding(
                start = 4.dp,
                top = 4.dp,
                end = 8.dp,
                bottom = 4.dp,
            ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            GameImage(
                src = src,
                modifier = Modifier.size(size = imageSize),
                contentScale = imageContentScale,
                colorFilter = imageColorFilter,
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = text,
                style = textStyle,
            )
        }
    }
}

@Composable
fun VerticalGameImageText(
    src: String,
    text: String,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(4.dp),
    backgroundColor: Color = Color.Transparent,
    imageSize: Dp = 24.dp,
    imageContentScale: ContentScale = ContentScale.Fit,
    imageColorFilter: ColorFilter? = null,
    textStyle: TextStyle = MaterialTheme.typography.labelLarge,
) {
    Surface(
        modifier = modifier,
        shape = shape,
        color = backgroundColor,
    ) {
        Column(
            modifier = Modifier.padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            GameImage(
                src = src,
                modifier = Modifier.size(size = imageSize),
                contentScale = imageContentScale,
                colorFilter = imageColorFilter,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = text,
                style = textStyle,
            )
        }
    }
}

@Composable
fun VerticalAffixImageText(
    type: String,
    text: String,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(4.dp),
    backgroundColor: Color = Color.Transparent,
    imageSize: Dp = 24.dp,
    imageContentScale: ContentScale = ContentScale.Fit,
    imageColorFilter: ColorFilter? = null,
    textStyle: TextStyle = MaterialTheme.typography.labelLarge,
) {
    Surface(
        modifier = modifier,
        shape = shape,
        color = backgroundColor,
    ) {
        Column(
            modifier = Modifier.padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AffixImage(
                type = type,
                modifier = Modifier.size(size = imageSize),
                contentScale = imageContentScale,
                colorFilter = imageColorFilter,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = text,
                style = textStyle,
            )
        }
    }
}

@Preview
@Composable
private fun PreviewHorizontalGameImageText() {
    RelicCalculatorTheme {
        val backgroundColor = MaterialTheme.colorScheme.secondaryContainer
        HorizontalGameImageText(
            src = "icon/property/IconSpeed.png",
            text = ">= 100",
            backgroundColor = backgroundColor,
            imageColorFilter = ColorFilter.tint(contentColorFor(backgroundColor)),
        )
    }
}

@Preview
@Composable
private fun PreviewVerticalGameImageText() {
    RelicCalculatorTheme {
        val backgroundColor = MaterialTheme.colorScheme.tertiaryContainer
        VerticalGameImageText(
            src = "icon/property/IconSpeed.png",
            text = "100",
            backgroundColor = backgroundColor,
            imageColorFilter = ColorFilter.tint(contentColorFor(backgroundColor)),
        )
    }
}

@Preview
@Composable
private fun PreviewVerticalAffixImageText() {
    RelicCalculatorTheme {
        val backgroundColor = MaterialTheme.colorScheme.tertiaryContainer
        VerticalAffixImageText(
            type = "SpeedDelta",
            text = "5.0",
            backgroundColor = backgroundColor,
            imageColorFilter = ColorFilter.tint(contentColorFor(backgroundColor)),
        )
    }
}
