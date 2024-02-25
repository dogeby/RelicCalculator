package com.dogeby.core.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
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
import com.dogeby.core.ui.theme.RelicCalculatorTheme

@Composable
fun DropdownMenuChip(
    label: String,
    modifier: Modifier = Modifier,
    dropdownMenuContent: @Composable ColumnScope.() -> Unit,
) {
    var expanded by remember {
        mutableStateOf(false)
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.TopCenter),
    ) {
        AssistChip(
            onClick = { expanded = !expanded },
            label = { Text(text = label) },
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
        DropdownMenuChip(label = "test") {
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
