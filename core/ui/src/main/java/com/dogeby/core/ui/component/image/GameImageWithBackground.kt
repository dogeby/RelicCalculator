package com.dogeby.core.ui.component.image

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dogeby.core.ui.theme.RelicCalculatorTheme

@Composable
fun GameImageWithBackground(
    src: String,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(4.dp),
    backgroundColor: Color = Color.Transparent,
    imageSize: Dp = 24.dp,
    imageContentScale: ContentScale = ContentScale.Fit,
    imageColorFilter: ColorFilter? = null,
) {
    Surface(
        modifier = modifier,
        shape = shape,
        color = backgroundColor,
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

@Preview
@Composable
fun PreviewGameImageWithBackground() {
    RelicCalculatorTheme {
        GameImageWithBackground(
            src = "icon/relic/103.png",
            backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
        )
    }
}
