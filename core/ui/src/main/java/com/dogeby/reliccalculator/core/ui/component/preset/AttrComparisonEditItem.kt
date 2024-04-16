package com.dogeby.reliccalculator.core.ui.component.preset

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dogeby.reliccalculator.core.model.preset.ComparisonOperator
import com.dogeby.reliccalculator.core.ui.component.DropdownMenuChip
import com.dogeby.reliccalculator.core.ui.component.image.GameImageWithBackground
import com.dogeby.reliccalculator.core.ui.theme.RelicCalculatorTheme
import com.dogeby.reliccalculator.core.ui.util.clearFocusWhenTap

@Composable
fun AttrComparisonEditItem(
    icon: String,
    name: String,
    displayComparedValue: String,
    percent: Boolean,
    comparisonOperator: ComparisonOperator,
    onComparisonOperatorChanged: (ComparisonOperator) -> Unit,
    onComparedValueChanged: (String) -> Unit,
    onDeleteItem: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(4.dp),
    color: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = contentColorFor(color),
    tonalElevation: Dp = 0.dp,
    shadowElevation: Dp = 0.dp,
) {
    Surface(
        modifier = modifier,
        shape = shape,
        color = color,
        contentColor = contentColor,
        tonalElevation = tonalElevation,
        shadowElevation = shadowElevation,
    ) {
        val expandedState: MutableState<Boolean> = remember {
            mutableStateOf(false)
        }
        Row(
            modifier = Modifier
                .padding(
                    horizontal = 16.dp,
                    vertical = 8.dp,
                ),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val iconBackgroundColor = MaterialTheme.colorScheme.primaryContainer
            GameImageWithBackground(
                src = icon,
                backgroundColor = iconBackgroundColor,
                imageSize = 48.dp,
                imageColorFilter = ColorFilter.tint(contentColorFor(iconBackgroundColor)),
                modifier = Modifier.size(56.dp),
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.bodyLarge,
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    DropdownMenuChip(
                        label = {
                            Text(
                                text = comparisonOperator.symbol,
                                modifier = Modifier.widthIn(min = 32.dp),
                                textAlign = TextAlign.Center,
                            )
                        },
                        expandedState = expandedState,
                        trailingIcon = null,
                    ) {
                        ComparisonOperator.entries.forEach {
                            ComparisonOperatorDropdownMenuItem(
                                symbol = it.symbol,
                                onClick = {
                                    onComparisonOperatorChanged(it)
                                    expandedState.value = false
                                },
                            )
                        }
                    }
                    ComparedValueTextField(
                        keyword = displayComparedValue,
                        onKeywordChange = onComparedValueChanged,
                        modifier = Modifier.weight(1f),
                    )
                    if (percent) {
                        Text(text = "%")
                    }
                }
            }
            IconButton(onClick = onDeleteItem) {
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = null,
                )
            }
        }
    }
}

@Composable
private fun ComparisonOperatorDropdownMenuItem(
    symbol: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    DropdownMenuItem(
        text = {
            Text(
                text = symbol,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )
        },
        onClick = onClick,
        modifier = modifier,
    )
}

@Composable
private fun ComparedValueTextField(
    keyword: String,
    onKeywordChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    borderColor: Color = MaterialTheme.colorScheme.outline,
) {
    val focusManager = LocalFocusManager.current

    BasicTextField(
        value = keyword,
        onValueChange = {
            onKeywordChange(it)
        },
        modifier = modifier
            .border(
                width = 1.dp,
                color = borderColor,
            )
            .padding(
                horizontal = 8.dp,
                vertical = 7.dp,
            ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal,
            imeAction = ImeAction.Done,
        ),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        singleLine = true,
    )
}

@Preview(apiLevel = 33)
@Composable
private fun PreviewAttrComparisonEditItem() {
    var comparisonOperator by remember {
        mutableStateOf(ComparisonOperator.GREATER_THAN_OR_EQUAL_TO)
    }
    var displayComparedValue by remember {
        mutableStateOf("123")
    }
    RelicCalculatorTheme {
        AttrComparisonEditItem(
            icon = "icon/property/IconSpeed.png",
            name = "속도",
            displayComparedValue = displayComparedValue,
            percent = true,
            comparisonOperator = comparisonOperator,
            onComparisonOperatorChanged = {
                comparisonOperator = it
            },
            onComparedValueChanged = {
                displayComparedValue = it
            },
            onDeleteItem = {},
            modifier = Modifier
                .width(360.dp)
                .clearFocusWhenTap(),
        )
    }
}
