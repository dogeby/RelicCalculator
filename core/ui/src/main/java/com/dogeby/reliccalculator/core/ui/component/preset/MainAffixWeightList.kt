package com.dogeby.reliccalculator.core.ui.component.preset

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dogeby.reliccalculator.core.domain.model.AffixWeightWithInfo
import com.dogeby.reliccalculator.core.model.mihomo.index.PropertyInfo
import com.dogeby.reliccalculator.core.model.mihomo.index.RelicPiece
import com.dogeby.reliccalculator.core.model.preset.AffixWeight
import com.dogeby.reliccalculator.core.ui.R
import com.dogeby.reliccalculator.core.ui.component.image.GameImage
import com.dogeby.reliccalculator.core.ui.theme.RelicCalculatorTheme

fun LazyListScope.mainAffixWeightList(
    relicPiece: RelicPiece,
    affixWeightWithInfoList: List<AffixWeightWithInfo>,
    onAddButtonClick: (RelicPiece) -> Unit,
    onMainAffixDelete: (RelicPiece, affixId: String) -> Unit,
    onWeightChangeFinished: (Float) -> Unit,
    shape: Shape = RoundedCornerShape(4.dp),
    tonalElevation: Dp = 0.dp,
) {
    item {
        Surface(
            shape = shape,
            tonalElevation = tonalElevation,
        ) {
            Row(
                modifier = Modifier.padding(
                    horizontal = 16.dp,
                    vertical = 8.dp,
                ),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    GameImage(
                        src = relicPiece.icon,
                        modifier = Modifier
                            .size(48.dp)
                            .padding(4.dp),
                        colorFilter = ColorFilter.tint(LocalContentColor.current),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    val name = stringResource(
                        when (relicPiece) {
                            RelicPiece.HEAD -> R.string.head
                            RelicPiece.HAND -> R.string.hand
                            RelicPiece.BODY -> R.string.body
                            RelicPiece.FOOT -> R.string.foot
                            RelicPiece.NECK -> R.string.planar_sphere
                            RelicPiece.OBJECT -> R.string.link_rope
                        },
                    )
                    Text(
                        text = name,
                        style = MaterialTheme.typography.titleMedium,
                    )
                }
                IconButton(onClick = { onAddButtonClick(relicPiece) }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                    )
                }
            }
        }
    }
    items(
        items = affixWeightWithInfoList,
        key = { it.affixWeight.affixId },
    ) { affixWeightWithInfo ->
        AffixWeightEditItem(
            name = affixWeightWithInfo.propertyInfo.name,
            weight = { affixWeightWithInfo.affixWeight.weight },
            icon = affixWeightWithInfo.propertyInfo.icon,
            onWeightChangeFinished = onWeightChangeFinished,
            onDelete = {
                onMainAffixDelete(relicPiece, affixWeightWithInfo.affixWeight.affixId)
            },
            tonalElevation = tonalElevation + 1.dp,
        )
    }
}

@Preview(apiLevel = 33)
@Composable
private fun PreviewMainAffixWeightList() {
    RelicCalculatorTheme {
        Box(modifier = Modifier.padding(8.dp)) {
            Surface(
                shape = RoundedCornerShape(4.dp),
            ) {
                LazyColumn {
                    mainAffixWeightList(
                        relicPiece = RelicPiece.BODY,
                        affixWeightWithInfoList = List(3) {
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
                        onAddButtonClick = {},
                        onMainAffixDelete = { _, _ -> },
                        onWeightChangeFinished = {},
                        shape = RoundedCornerShape(4.dp),
                    )
                }
            }
        }
    }
}
