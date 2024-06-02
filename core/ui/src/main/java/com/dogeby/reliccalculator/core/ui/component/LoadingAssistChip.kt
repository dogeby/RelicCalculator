package com.dogeby.reliccalculator.core.ui.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
        enabled = !isLoading(),
        leadingIcon = {
            AnimatedContent(
                targetState = isLoading(),
                label = "LoadingAssistChipLeadingIconAnimation",
            ) {
                if (it) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(AssistChipDefaults.IconSize)
                            .padding(2.dp),
                        color = AssistChipDefaults
                            .assistChipColors()
                            .disabledLeadingIconContentColor,
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
        var isLoading by remember {
            mutableStateOf(false)
        }
        LoadingAssistChip(
            isLoading = { isLoading },
            text = "test",
            onClick = { isLoading = !isLoading },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = null,
                    modifier = Modifier.size(AssistChipDefaults.IconSize),
                )
            },
        )
    }
}
