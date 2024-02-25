package com.dogeby.core.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.dogeby.core.ui.theme.RelicCalculatorTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogFilterChip(
    selected: () -> Boolean,
    text: String,
    modifier: Modifier = Modifier,
    leadingIcon: @Composable (() -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    var expanded by remember {
        mutableStateOf(false)
    }
    FilterChip(
        selected = selected(),
        onClick = { expanded = !expanded },
        label = { Text(text = text) },
        modifier = modifier,
        leadingIcon = leadingIcon,
    )
    if (expanded) {
        BasicAlertDialog(
            onDismissRequest = { expanded = !expanded },
            content = {
                Surface(
                    shape = AlertDialogDefaults.shape,
                    color = AlertDialogDefaults.containerColor,
                    tonalElevation = AlertDialogDefaults.TonalElevation,
                ) {
                    content()
                }
            },
        )
    }
}

@Preview(apiLevel = 33)
@Composable
fun PreviewDialogFilterChip() {
    RelicCalculatorTheme {
        DialogFilterChip(
            selected = { false },
            text = "test",
            leadingIcon = {
                Icon(imageVector = Icons.Default.FilterList, contentDescription = null)
            },
        ) {
            Text(text = "test")
        }
    }
}
