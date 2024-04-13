package com.dogeby.reliccalculator.core.ui.component.preset

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dogeby.reliccalculator.core.domain.model.AffixWeightWithInfo
import com.dogeby.reliccalculator.core.model.mihomo.index.PropertyInfo
import com.dogeby.reliccalculator.core.model.preset.AffixWeight
import com.dogeby.reliccalculator.core.ui.theme.RelicCalculatorTheme

fun LazyListScope.subAffixWeightList(
    subAffixWeightListUiState: SubAffixWeightListUiState,
    onWeightChangeFinished: (affixId: String, weight: Float) -> Unit,
    onDeleteSubAffixWeight: (affixId: String) -> Unit,
    itemShape: Shape = RoundedCornerShape(4.dp),
    itemTonalElevation: Dp = 0.dp,
    itemShadowElevation: Dp = 0.dp,
) {
    when (subAffixWeightListUiState) {
        SubAffixWeightListUiState.Loading -> Unit
        is SubAffixWeightListUiState.Success -> {
            items(
                items = subAffixWeightListUiState.affixWeightWithInfoList,
                key = {
                    it.affixWeight.affixId
                },
            ) { affixWeightWithInfo ->
                AffixWeightEditItem(
                    name = affixWeightWithInfo.propertyInfo.name,
                    weight = { affixWeightWithInfo.affixWeight.weight },
                    icon = affixWeightWithInfo.propertyInfo.icon,
                    onWeightChangeFinished = {
                        onWeightChangeFinished(
                            affixWeightWithInfo.affixWeight.affixId,
                            it,
                        )
                    },
                    onDeleteItem = {
                        onDeleteSubAffixWeight(affixWeightWithInfo.affixWeight.affixId)
                    },
                    shape = itemShape,
                    tonalElevation = itemTonalElevation,
                    shadowElevation = itemShadowElevation,
                )
            }
        }
    }
}

sealed interface SubAffixWeightListUiState {

    data object Loading : SubAffixWeightListUiState

    data class Success(
        val affixWeightWithInfoList: List<AffixWeightWithInfo>,
    ) : SubAffixWeightListUiState
}

@Preview(apiLevel = 33)
@Composable
private fun PreviewSubAffixWeightList() {
    RelicCalculatorTheme {
        LazyColumn {
            subAffixWeightList(
                subAffixWeightListUiState = SubAffixWeightListUiState.Success(
                    List(3) {
                        AffixWeightWithInfo(
                            affixWeight = AffixWeight(
                                affixId = "$it 4",
                                type = "CriticalChanceBase",
                                weight = 1f,
                            ),
                            propertyInfo = PropertyInfo(
                                type = "CriticalChanceBase",
                                name = "치명타 확률",
                                field = "crit_rate",
                                affix = true,
                                ratio = false,
                                percent = true,
                                order = 14,
                                icon = "icon/property/IconCriticalChance.png",
                            ),
                        )
                    },
                ),
                onWeightChangeFinished = { _, _ -> },
                onDeleteSubAffixWeight = {},
            )
        }
    }
}
