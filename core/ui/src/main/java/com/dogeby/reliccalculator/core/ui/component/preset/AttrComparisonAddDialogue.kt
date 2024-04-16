package com.dogeby.reliccalculator.core.ui.component.preset

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FilterChip
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dogeby.reliccalculator.core.domain.model.AttrComparisonWithInfo
import com.dogeby.reliccalculator.core.model.mihomo.index.PropertyInfo
import com.dogeby.reliccalculator.core.model.preset.AttrComparison
import com.dogeby.reliccalculator.core.model.preset.ComparisonOperator
import com.dogeby.reliccalculator.core.ui.R
import com.dogeby.reliccalculator.core.ui.component.EmptyState
import com.dogeby.reliccalculator.core.ui.component.image.GameImage
import com.dogeby.reliccalculator.core.ui.theme.RelicCalculatorTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AttrComparisonAddDialogue(
    attrComparisonAddDialogueUiState: AttrComparisonAddDialogueUiState,
    onDismissRequest: () -> Unit,
    onAddBtnClick: (type: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var selectedAttrComparison: String? by rememberSaveable(attrComparisonAddDialogueUiState) {
        mutableStateOf(null)
    }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(
                onClick = { selectedAttrComparison?.let { onAddBtnClick(it) } },
                enabled = attrComparisonAddDialogueUiState is
                    AttrComparisonAddDialogueUiState.Success,
            ) {
                Text(text = stringResource(id = R.string.add))
            }
        },
        modifier = modifier,
        dismissButton = {
            TextButton(
                onClick = onDismissRequest,
            ) {
                Text(text = stringResource(id = R.string.dismiss))
            }
        },
        text = {
            when (attrComparisonAddDialogueUiState) {
                AttrComparisonAddDialogueUiState.Loading -> Unit
                AttrComparisonAddDialogueUiState.Empty -> {
                    EmptyState()
                }
                is AttrComparisonAddDialogueUiState.Success -> {
                    FlowRow(
                        modifier = modifier,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        attrComparisonAddDialogueUiState
                            .attrComparisons
                            .forEach { attrComparison ->
                                val isSelected = attrComparison.attrComparison.type ==
                                    selectedAttrComparison
                                FilterChip(
                                    selected = isSelected,
                                    onClick = {
                                        if (isSelected) {
                                            selectedAttrComparison = null
                                            return@FilterChip
                                        }
                                        selectedAttrComparison = attrComparison.attrComparison.type
                                    },
                                    label = { Text(text = attrComparison.propertyInfo.name) },
                                    leadingIcon = {
                                        val contentColor = LocalContentColor.current
                                        GameImage(
                                            src = attrComparison.propertyInfo.icon,
                                            modifier = Modifier.size(18.dp),
                                            colorFilter = ColorFilter.tint(contentColor),
                                        )
                                    },
                                )
                            }
                    }
                }
            }
        },
    )
}

sealed interface AttrComparisonAddDialogueUiState {

    data object Loading : AttrComparisonAddDialogueUiState

    data object Empty : AttrComparisonAddDialogueUiState

    data class Success(
        val attrComparisons: List<AttrComparisonWithInfo>,
    ) : AttrComparisonAddDialogueUiState
}

@Preview(apiLevel = 33)
@Composable
fun PreviewAttrComparisonAddDialogue() {
    RelicCalculatorTheme {
        var shown by remember {
            mutableStateOf(true)
        }
        if (shown) {
            AttrComparisonAddDialogue(
                attrComparisonAddDialogueUiState = AttrComparisonAddDialogueUiState.Success(
                    attrComparisons = List(5) {
                        AttrComparisonWithInfo(
                            attrComparison = AttrComparison(
                                type = "$it",
                                field = "",
                                comparedValue = 0f,
                                display = "",
                                percent = false,
                                comparisonOperator = ComparisonOperator.GREATER_THAN,
                            ),
                            propertyInfo = PropertyInfo(
                                type = "$it",
                                name = "Attr$it",
                                field = "",
                                affix = false,
                                ratio = false,
                                percent = false,
                                order = 0,
                                icon = "",
                            ),
                        )
                    },
                ),
                onDismissRequest = { shown = false },
                onAddBtnClick = { shown = false },
            )
        }
    }
}
