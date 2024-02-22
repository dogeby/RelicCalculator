package com.dogeby.core.ui.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import com.dogeby.core.ui.theme.RelicCalculatorTheme

@Composable
fun ExpandableContent(
    modifier: Modifier = Modifier,
    onToggle: ((isExpanded: Boolean) -> Unit)? = null,
    content: @Composable AnimatedContentScope.(isExpanded: Boolean) -> Unit,
) {
    var isExpanded by remember {
        mutableStateOf(false)
    }
    AnimatedContent(
        targetState = isExpanded,
        modifier = modifier.clickable {
            isExpanded = !isExpanded
            if (onToggle != null) {
                onToggle(isExpanded)
            }
        },
        transitionSpec = {
            fadeIn(animationSpec = tween(150, 150)) togetherWith
                fadeOut(animationSpec = tween(150)) using
                SizeTransform { initialSize, targetSize ->
                    keyframes {
                        durationMillis = if (targetState) {
                            IntSize(targetSize.width, initialSize.height) at 150
                            300
                        } else {
                            IntSize(initialSize.width, targetSize.height) at 150
                            300
                        }
                    }
                }
        },
        label = "SizeTransformAnimation",
        content = content,
    )
}

@Preview
@Composable
fun PreviewExpandableRow() {
    RelicCalculatorTheme {
        val list = List(5) { it.toString() }
        ExpandableContent(
            content = { isExpanded ->
                if (isExpanded) {
                    Column {
                        list.forEach { Text(text = it) }
                    }
                    return@ExpandableContent
                }
                Text(text = list[0])
            },
        )
    }
}