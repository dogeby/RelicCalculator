package com.dogeby.reliccalculator.core.ui.component.preset

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dogeby.reliccalculator.core.domain.model.AffixWeightWithInfo
import com.dogeby.reliccalculator.core.domain.model.AttrComparisonWithInfo
import com.dogeby.reliccalculator.core.domain.model.PresetWithDetails
import com.dogeby.reliccalculator.core.model.mihomo.index.RelicPiece
import com.dogeby.reliccalculator.core.model.mihomo.index.sampleCharacterInfo
import com.dogeby.reliccalculator.core.model.mihomo.index.sampleElementInfo
import com.dogeby.reliccalculator.core.model.mihomo.index.samplePathInfo
import com.dogeby.reliccalculator.core.model.mihomo.index.samplePropertyInfo
import com.dogeby.reliccalculator.core.model.mihomo.index.sampleRelicSetInfo
import com.dogeby.reliccalculator.core.model.preset.AffixWeight
import com.dogeby.reliccalculator.core.model.preset.AttrComparison
import com.dogeby.reliccalculator.core.model.preset.ComparisonOperator
import com.dogeby.reliccalculator.core.ui.theme.RelicCalculatorTheme

fun LazyGridScope.presetList(
    presetListUiState: PresetListUiState,
    onEditMenuItemClick: (id: String) -> Unit,
    onAutoUpdateChanged: (id: String, isAutoUpdate: Boolean) -> Unit,
) {
    when (presetListUiState) {
        PresetListUiState.Loading -> Unit
        is PresetListUiState.Success -> {
            items(
                items = presetListUiState.presets,
                key = { it.characterId },
            ) { presetWithDetails ->
                PresetCard(
                    presetWithDetails = presetWithDetails,
                    onEditMenuItemClick = onEditMenuItemClick,
                    onAutoUpdateChanged = onAutoUpdateChanged,
                )
            }
        }
    }
}

sealed interface PresetListUiState {

    data object Loading : PresetListUiState

    data class Success(
        val presets: List<PresetWithDetails>,
    ) : PresetListUiState
}

@Preview
@Composable
private fun PreviewPresetList() {
    RelicCalculatorTheme {
        val presets = List(3) { index ->
            PresetWithDetails(
                characterId = "$index",
                characterInfo = sampleCharacterInfo,
                pathInfo = samplePathInfo,
                elementInfo = sampleElementInfo,
                relicSets = List(3) {
                    sampleRelicSetInfo.copy(id = "$it")
                },
                pieceMainAffixWeightsWithInfo = RelicPiece.entries.associateWith {
                    List(3) {
                        AffixWeightWithInfo(
                            AffixWeight(affixId = "1$it", type = "SpeedDelta", weight = 1.0f),
                            samplePropertyInfo,
                        )
                    }
                },
                subAffixWeightsWithInfo = List(6) {
                    AffixWeightWithInfo(
                        AffixWeight(affixId = "2$it", type = "SpeedDelta", weight = 1.0f),
                        samplePropertyInfo,
                    )
                },
                isAutoUpdate = false,
                attrComparisons = List(6) {
                    AttrComparisonWithInfo(
                        attrComparison = AttrComparison(
                            type = "SpeedDelta$it",
                            field = "spd",
                            comparedValue = 500.0f,
                            display = "500",
                            percent = false,
                            comparisonOperator = ComparisonOperator.GREATER_THAN,
                        ),
                        propertyInfo = samplePropertyInfo,
                    )
                },
            )
        }
        LazyVerticalGrid(
            columns = GridCells.Adaptive(360.dp),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            presetList(
                presetListUiState = PresetListUiState.Success(presets),
                onEditMenuItemClick = {},
                onAutoUpdateChanged = { _, _ -> },
            )
        }
    }
}
