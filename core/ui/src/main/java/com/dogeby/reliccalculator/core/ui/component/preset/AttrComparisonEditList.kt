package com.dogeby.reliccalculator.core.ui.component.preset

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dogeby.reliccalculator.core.domain.model.AttrComparisonWithInfo
import com.dogeby.reliccalculator.core.model.mihomo.index.samplePropertyInfo
import com.dogeby.reliccalculator.core.model.preset.ComparisonOperator
import com.dogeby.reliccalculator.core.model.preset.sampleAttrComparison
import com.dogeby.reliccalculator.core.ui.theme.RelicCalculatorTheme

fun LazyListScope.attrComparisonEditList(
    attrComparisonEditListUiState: AttrComparisonEditListUiState,
    onComparisonOperatorChanged: (type: String, comparisonOperator: ComparisonOperator) -> Unit,
    onComparedValueChanged: (type: String, comparedValue: String) -> Unit,
    onDeleteAttrComparisonEditItem: (type: String) -> Unit,
) {
    when (attrComparisonEditListUiState) {
        AttrComparisonEditListUiState.Loading -> Unit
        is AttrComparisonEditListUiState.Success -> {
            items(
                items = attrComparisonEditListUiState.attrComparisonWithInfoList,
                key = {
                    it.propertyInfo.type
                },
            ) { editItem ->
                AttrComparisonEditItem(
                    icon = editItem.propertyInfo.icon,
                    name = editItem.propertyInfo.name,
                    comparedValue = editItem.attrComparison.comparedValue,
                    percent = editItem.propertyInfo.percent,
                    comparisonOperator = editItem.attrComparison.comparisonOperator,
                    onComparisonOperatorChanged = {
                        onComparisonOperatorChanged(
                            editItem.propertyInfo.type,
                            it,
                        )
                    },
                    onComparedValueChanged = {
                        onComparedValueChanged(
                            editItem.propertyInfo.type,
                            it,
                        )
                    },
                    onDeleteItem = {
                        onDeleteAttrComparisonEditItem(editItem.propertyInfo.type)
                    },
                )
            }
        }
    }
}

sealed interface AttrComparisonEditListUiState {

    data object Loading : AttrComparisonEditListUiState

    data class Success(
        val attrComparisonWithInfoList: List<AttrComparisonWithInfo>,
    ) : AttrComparisonEditListUiState
}

@Preview(apiLevel = 33)
@Composable
private fun PreviewAttrComparisonEditList() {
    RelicCalculatorTheme {
        LazyColumn(
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            attrComparisonEditList(
                attrComparisonEditListUiState = AttrComparisonEditListUiState.Success(
                    List(3) {
                        AttrComparisonWithInfo(
                            attrComparison = sampleAttrComparison,
                            propertyInfo = samplePropertyInfo.copy(type = "$it"),
                        )
                    },
                ),
                onComparedValueChanged = { _, _ -> },
                onComparisonOperatorChanged = { _, _ -> },
                onDeleteAttrComparisonEditItem = {},
            )
        }
    }
}
