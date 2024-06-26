package com.dogeby.reliccalculator.core.ui.component.preset

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dogeby.reliccalculator.core.domain.model.AffixWeightWithInfo
import com.dogeby.reliccalculator.core.model.mihomo.index.PropertyInfo
import com.dogeby.reliccalculator.core.model.mihomo.index.RelicPiece
import com.dogeby.reliccalculator.core.model.preset.AffixWeight
import com.dogeby.reliccalculator.core.ui.theme.RelicCalculatorTheme

fun LazyListScope.pieceMainAffixWeightList(
    pieceMainAffixWeightListUiState: PieceMainAffixWeightListUiState,
    onMainAffixWeightChangeFinished: (
        relicPiece: RelicPiece,
        affixId: String,
        weight: Float,
    ) -> Unit,
) {
    when (pieceMainAffixWeightListUiState) {
        PieceMainAffixWeightListUiState.Loading -> Unit
        is PieceMainAffixWeightListUiState.Success -> {
            items(
                items = pieceMainAffixWeightListUiState.pieceToAffixWeightsMap.toList(),
                key = { (relicPiece, _) ->
                    relicPiece
                },
            ) { (relicPiece, affixes) ->
                PieceMainAffixWeightCard(
                    relicPiece = relicPiece,
                    affixWeightWithInfoList = affixes,
                    onWeightChangeFinished = onMainAffixWeightChangeFinished,
                )
            }
        }
    }
}

sealed interface PieceMainAffixWeightListUiState {

    data object Loading : PieceMainAffixWeightListUiState

    data class Success(
        val pieceToAffixWeightsMap: Map<RelicPiece, List<AffixWeightWithInfo>>,
    ) : PieceMainAffixWeightListUiState
}

@Preview(apiLevel = 33)
@Composable
private fun PreviewPieceMainAffixWeightList() {
    RelicCalculatorTheme {
        val affixes = List(3) {
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
        }
        LazyColumn(
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            pieceMainAffixWeightList(
                pieceMainAffixWeightListUiState = PieceMainAffixWeightListUiState.Success(
                    RelicPiece.entries.associateWith {
                        affixes
                    },
                ),
                onMainAffixWeightChangeFinished = { _, _, _ -> },
            )
        }
    }
}
