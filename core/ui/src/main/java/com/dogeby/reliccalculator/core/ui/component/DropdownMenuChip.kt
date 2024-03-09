package com.dogeby.reliccalculator.core.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.AssistChip
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.dogeby.reliccalculator.core.ui.theme.RelicCalculatorTheme

@Composable
fun DropdownMenuChip(
    label: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    expandedState: MutableState<Boolean> = remember {
        mutableStateOf(false)
    },
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = {
        ExpandIcon({ expandedState.value })
    },
    dropdownMenuContent: @Composable ColumnScope.() -> Unit,
) {
    Box(
        modifier = modifier.wrapContentSize(Alignment.TopCenter),
    ) {
        AssistChip(
            onClick = { expandedState.value = !expandedState.value },
            label = label,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
        )
        DropdownMenu(
            expanded = expandedState.value,
            onDismissRequest = { expandedState.value = false },
        ) {
            dropdownMenuContent()
        }
    }
}

@Composable
fun ExpandIcon(
    expanded: () -> Boolean,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
) {
    Icon(
        imageVector = if (expanded()) {
            Icons.Default.ExpandLess
        } else {
            Icons.Default.ExpandMore
        },
        contentDescription = contentDescription,
        modifier = modifier,
    )
}

@Preview(apiLevel = 33)
@Composable
private fun PreviewDropdownMenuChip() {
    RelicCalculatorTheme {
        DropdownMenuChip(
            label = { Text(text = "test") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Sort,
                    contentDescription = null,
                )
            },
        ) {
            List(3) {
                DropdownMenuItem(
                    text = {
                        Text(text = "Item: $it")
                    },
                    onClick = {},
                )
            }
        }
    }
}
