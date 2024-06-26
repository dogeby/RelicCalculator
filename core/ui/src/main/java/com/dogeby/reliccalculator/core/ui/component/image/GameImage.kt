package com.dogeby.reliccalculator.core.ui.component.image

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.request.ImageRequest
import com.dogeby.reliccalculator.core.ui.R
import com.dogeby.reliccalculator.core.ui.theme.RelicCalculatorTheme

private const val GAME_IMAGE_PATH = "file:///android_asset/game/"
private const val AFFIX_IMAGE_PATH = "icon/affix/"

private const val GAME_IMAGE_EXTENSION = "webp"

private fun String.modifyExtension(newExtension: String): String {
    if ('.' in newExtension) {
        throw IllegalArgumentException("Invalid extension: $newExtension")
    }
    return replaceAfterLast('.', newExtension, "$this.$newExtension")
}

@Composable
fun GameImage(
    src: String,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    placeholder: Painter? = null,
    error: Painter? = rememberVectorPainter(image = Icons.Default.BrokenImage),
    fallback: Painter? = error,
    onLoading: ((AsyncImagePainter.State.Loading) -> Unit)? = null,
    onSuccess: ((AsyncImagePainter.State.Success) -> Unit)? = null,
    onError: ((AsyncImagePainter.State.Error) -> Unit)? = null,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    filterQuality: FilterQuality = DrawScope.DefaultFilterQuality,
    crossFadeDurationMillis: Int = integerResource(id = R.integer.DEFAULT_CROSS_FADE_DURATION),
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(GAME_IMAGE_PATH + src.modifyExtension(GAME_IMAGE_EXTENSION))
            .crossfade(crossFadeDurationMillis)
            .build(),
        contentDescription = contentDescription,
        modifier = modifier,
        placeholder = placeholder,
        error = error,
        fallback = fallback,
        onLoading = onLoading,
        onSuccess = onSuccess,
        onError = onError,
        alignment = alignment,
        contentScale = contentScale,
        alpha = alpha,
        colorFilter = colorFilter,
        filterQuality = filterQuality,
    )
}

@Composable
fun AffixImage(
    type: String,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    placeholder: Painter? = null,
    error: Painter? = rememberVectorPainter(image = Icons.Default.BrokenImage),
    fallback: Painter? = error,
    onLoading: ((AsyncImagePainter.State.Loading) -> Unit)? = null,
    onSuccess: ((AsyncImagePainter.State.Success) -> Unit)? = null,
    onError: ((AsyncImagePainter.State.Error) -> Unit)? = null,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    filterQuality: FilterQuality = DrawScope.DefaultFilterQuality,
    crossFadeDurationMillis: Int = integerResource(id = R.integer.DEFAULT_CROSS_FADE_DURATION),
) {
    GameImage(
        src = AFFIX_IMAGE_PATH + type,
        contentDescription = contentDescription,
        modifier = modifier,
        placeholder = placeholder,
        error = error,
        fallback = fallback,
        onLoading = onLoading,
        onSuccess = onSuccess,
        onError = onError,
        alignment = alignment,
        contentScale = contentScale,
        alpha = alpha,
        colorFilter = colorFilter,
        filterQuality = filterQuality,
        crossFadeDurationMillis = crossFadeDurationMillis,
    )
}

@Preview(showBackground = true)
@Composable
private fun GameImagePreview() {
    RelicCalculatorTheme {
        GameImage(
            src = "icon/property/IconSpeed.png",
            placeholder = rememberVectorPainter(image = Icons.Default.BrokenImage),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AffixImagePreview() {
    RelicCalculatorTheme {
        AffixImage(
            type = "AttackAddedRatio",
            placeholder = rememberVectorPainter(image = Icons.Default.BrokenImage),
        )
    }
}
