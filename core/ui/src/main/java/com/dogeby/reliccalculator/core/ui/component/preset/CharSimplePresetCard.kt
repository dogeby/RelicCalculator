package com.dogeby.reliccalculator.core.ui.component.preset

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
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
import com.dogeby.reliccalculator.core.ui.R
import com.dogeby.reliccalculator.core.ui.component.EmptyState
import com.dogeby.reliccalculator.core.ui.theme.RelicCalculatorTheme

@Composable
fun CharSimplePresetCard(
    presetWithDetails: PresetWithDetails,
    modifier: Modifier = Modifier,
    shape: Shape = CardDefaults.shape,
    colors: CardColors = CardDefaults.cardColors(),
) {
    var openDialog by remember { mutableStateOf(false) }

    Card(
        modifier = modifier,
        shape = shape,
        colors = colors,
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            with(presetWithDetails) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            openDialog = true
                        },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = stringResource(id = R.string.preset),
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowForward,
                        contentDescription = null,
                    )
                }
                if (subAffixWeightsWithInfo.isEmpty()) {
                    EmptyState()
                } else {
                    SubAffixWeightsWithInfoList(subAffixWeightsWithInfo)
                }
                if (openDialog) {
                    Dialog(onDismissRequest = { openDialog = false }) {
                        PresetCard(
                            presetWithDetails = presetWithDetails,
                            onEditMenuItemClick = null,
                            onAutoUpdateChanged = null,
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewCharSimplePresetCard() {
    RelicCalculatorTheme {
        CharSimplePresetCard(
            presetWithDetails = PresetWithDetails(
                characterId = "",
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
            ),
        )
    }
}
