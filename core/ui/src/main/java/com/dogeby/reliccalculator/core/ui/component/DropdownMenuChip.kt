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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.dogeby.reliccalculator.core.ui.theme.RelicCalculatorTheme

@Composable
fun DropdownMenuChip(
    text: String,
    modifier: Modifier = Modifier,
    leadingIcon: @Composable (() -> Unit)? = null,
    dropdownMenuContent: @Composable ColumnScope.() -> Unit,
) {
    var expanded by remember {
        mutableStateOf(false)
    }
    Box(
        modifier = modifier.wrapContentSize(Alignment.TopCenter),
    ) {
        AssistChip(
            onClick = { expanded = !expanded },
            label = { Text(text = text) },
            leadingIcon = leadingIcon,
            trailingIcon = {
                Icon(
                    imageVector = if (expanded) {
                        Icons.Default.ExpandLess
                    } else {
                        Icons.Default.ExpandMore
                    },
                    contentDescription = null,
                )
            },
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            dropdownMenuContent()
        }
    }
}

@Preview(apiLevel = 33)
@Composable
private fun PreviewDropdownMenuChip() {
    RelicCalculatorTheme {
        DropdownMenuChip(
            text = "test",
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
