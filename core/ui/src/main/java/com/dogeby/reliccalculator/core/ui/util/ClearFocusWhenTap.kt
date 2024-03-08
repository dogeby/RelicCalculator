package com.dogeby.reliccalculator.core.ui.util

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager

fun Modifier.clearFocusWhenTap(doOnClear: () -> Unit = {}): Modifier = composed {
    val focusManager = LocalFocusManager.current
    this.pointerInput(Unit) {
        detectTapGestures {
            focusManager.clearFocus()
            doOnClear()
        }
    }
}
