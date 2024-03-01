package com.dogeby.reliccalculator.core.ui.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Update
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dogeby.reliccalculator.core.ui.theme.RelicCalculatorTheme

@Composable
fun LoadingAssistChip(
    isLoading: () -> Boolean,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    leadingIcon: @Composable (() -> Unit)? = null,
) {
    AssistChip(
        onClick = onClick,
        label = { Text(text = text) },
        modifier = modifier,
        leadingIcon = {
            AnimatedContent(
                targetState = isLoading(),
                label = "LoadingAssistChipLeadingIconAnimation",
            ) {
                if (it) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(AssistChipDefaults.IconSize),
                        strokeWidth = 2.dp,
                    )
                    return@AnimatedContent
                }
                if (leadingIcon != null) {
                    leadingIcon()
                }
            }
        },
    )
}

@Preview(apiLevel = 33)
@Composable
private fun PreviewLoadingAssistChip() {
    RelicCalculatorTheme {
        LoadingAssistChip(
            isLoading = { false },
            text = "test",
            onClick = {},
            leadingIcon = { Icon(imageVector = Icons.Default.Update, contentDescription = null) },
        )
    }
}
