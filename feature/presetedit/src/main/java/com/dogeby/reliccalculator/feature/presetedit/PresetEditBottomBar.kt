package com.dogeby.reliccalculator.feature.presetedit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dogeby.reliccalculator.core.ui.theme.RelicCalculatorTheme

internal val presetEditBottomBarHeight = 64.dp

@Composable
internal fun PresetEditBottomBar(
    onSave: () -> Unit,
    onReset: () -> Unit,
    modifier: Modifier = Modifier,
    saveBtnEnabled: Boolean = true,
    resetBtnEnabled: Boolean = true,
    containerColor: Color = BottomAppBarDefaults.containerColor,
    contentColor: Color = contentColorFor(containerColor),
    tonalElevation: Dp = BottomAppBarDefaults.ContainerElevation,
    contentPadding: PaddingValues = BottomAppBarDefaults.ContentPadding,
) {
    Surface(
        modifier = modifier,
        color = containerColor,
        contentColor = contentColor,
        tonalElevation = tonalElevation,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(presetEditBottomBarHeight)
                .padding(contentPadding),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Button(
                onClick = onReset,
                enabled = resetBtnEnabled,
            ) {
                Text(text = stringResource(id = R.string.reset))
            }
            Button(
                onClick = onSave,
                enabled = saveBtnEnabled,
            ) {
                Text(text = stringResource(id = R.string.save))
            }
        }
    }
}

@Preview(apiLevel = 33)
@Composable
private fun PreviewPresetEditBottomBarBtnDisabled() {
    RelicCalculatorTheme {
        PresetEditBottomBar(
            onSave = {},
            onReset = {},
        )
    }
}

@Preview(apiLevel = 33)
@Composable
private fun PreviewPresetEditBottomBar() {
    RelicCalculatorTheme {
        PresetEditBottomBar(
            onSave = {},
            onReset = {},
            resetBtnEnabled = false,
            saveBtnEnabled = false,
        )
    }
}
