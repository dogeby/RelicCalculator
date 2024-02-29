package com.dogeby.core.ui.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.dogeby.core.ui.theme.RelicCalculatorTheme
import kotlin.math.roundToInt

@Composable
private fun HideableBarList(
    topBar: @Composable () -> Unit,
    topBarHeight: Dp,
    bottomBar: @Composable () -> Unit,
    bottomBarHeight: Dp,
    modifier: Modifier = Modifier,
    scrollableContent: @Composable (
        contentPadding: PaddingValues,
        onScrollToStartOrEnd: (isScrollToStartOrEnd: Boolean) -> Unit,
    ) -> Unit,
) {
    var isScrollToEnd by remember {
        mutableStateOf(true)
    }
    val topBarHeightPx = with(LocalDensity.current) { topBarHeight.roundToPx().toFloat() }
    val bottomBarHeightPx = with(LocalDensity.current) { bottomBarHeight.roundToPx().toFloat() }
    var topBarOffsetHeightPx by remember { mutableFloatStateOf(0f) }
    var bottomBarOffsetHeightPx by remember { mutableFloatStateOf(0f) }
    if (isScrollToEnd) {
        topBarOffsetHeightPx = 0f
        bottomBarOffsetHeightPx = 0f
    }

    val nestedScrollConnection = object : NestedScrollConnection {
        override fun onPreScroll(
            available: Offset,
            source: NestedScrollSource,
        ): Offset {
            topBarOffsetHeightPx = if (isScrollToEnd) {
                0f
            } else {
                val newOffset = topBarOffsetHeightPx - available.y
                newOffset.coerceIn(0f, topBarHeightPx)
            }
            bottomBarOffsetHeightPx = if (isScrollToEnd) {
                0f
            } else {
                val newOffset = bottomBarOffsetHeightPx + available.y
                newOffset.coerceIn(-bottomBarHeightPx, 0f)
            }
            return Offset.Zero
        }
    }

    val animatedTopBarOffsetHeightPx by animateFloatAsState(
        targetValue = topBarOffsetHeightPx,
        label = "BarHideAbleTopBarAnimate",
    )
    val animatedBottomBarOffsetHeightPx by animateFloatAsState(
        targetValue = bottomBarOffsetHeightPx,
        label = "BarHideAbleBottomBarAnimate",
    )
    Scaffold(
        modifier = modifier.nestedScroll(nestedScrollConnection),
        topBar = {
            Box(
                modifier = Modifier
                    .height(topBarHeight)
                    .offsetVertically(animatedTopBarOffsetHeightPx),
            ) {
                topBar()
            }
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .height(bottomBarHeight)
                    .offsetVertically(animatedBottomBarOffsetHeightPx),
            ) {
                bottomBar()
            }
        },
    ) {
        scrollableContent(it) { scrollToEnd ->
            isScrollToEnd = scrollToEnd
        }
    }
}

private fun Modifier.offsetVertically(offsetHeightPx: Float) = offset {
    IntOffset(
        x = 0,
        y = -offsetHeightPx.roundToInt(),
    )
}

@Composable
private fun PaddingValues.plus(
    direction: LayoutDirection,
    other: PaddingValues,
): PaddingValues {
    return PaddingValues(
        start = calculateStartPadding(direction) + other.calculateStartPadding(direction),
        top = calculateTopPadding() + other.calculateTopPadding(),
        end = calculateEndPadding(direction) + other.calculateEndPadding(direction),
        bottom = calculateBottomPadding() + other.calculateBottomPadding(),
    )
}

@Composable
fun HideableBarLazyVerticalGrid(
    topBar: @Composable () -> Unit,
    topBarHeight: Dp,
    bottomBar: @Composable () -> Unit,
    bottomBarHeight: Dp,
    columns: GridCells,
    modifier: Modifier = Modifier,
    state: LazyGridState = rememberLazyGridState(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    reverseLayout: Boolean = false,
    verticalArrangement: Arrangement.Vertical =
        if (!reverseLayout) Arrangement.Top else Arrangement.Bottom,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    userScrollEnabled: Boolean = true,
    content: LazyGridScope.() -> Unit,
) {
    val isScrollToStartOrEnd by remember {
        derivedStateOf {
            (state.canScrollForward and state.canScrollBackward).not()
        }
    }
    HideableBarList(
        topBar = topBar,
        topBarHeight = topBarHeight,
        bottomBar = bottomBar,
        bottomBarHeight = bottomBarHeight,
        modifier = modifier,
    ) { listContentPadding, onScrollToStartOrEnd ->
        LaunchedEffect(isScrollToStartOrEnd) {
            onScrollToStartOrEnd(isScrollToStartOrEnd)
        }
        LazyVerticalGrid(
            columns = columns,
            state = state,
            contentPadding = contentPadding.plus(
                direction = LocalLayoutDirection.current,
                other = listContentPadding,
            ),
            reverseLayout = reverseLayout,
            verticalArrangement = verticalArrangement,
            horizontalArrangement = horizontalArrangement,
            flingBehavior = flingBehavior,
            userScrollEnabled = userScrollEnabled,
            content = content,
        )
    }
}

@Preview(apiLevel = 33)
@Composable
private fun PreviewBarHideableLazyColumn() {
    RelicCalculatorTheme {
        HideableBarLazyVerticalGrid(
            topBar = {
                Button(
                    onClick = {},
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text("top")
                }
            },
            topBarHeight = 40.dp,
            bottomBar = {
                Button(
                    onClick = {},
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text("bottom")
                }
            },
            bottomBarHeight = 40.dp,
            columns = GridCells.Fixed(2),
        ) {
            items(List(100) { it.toString() }) {
                Text(text = it, modifier = Modifier.fillMaxWidth())
            }
        }
    }
}
