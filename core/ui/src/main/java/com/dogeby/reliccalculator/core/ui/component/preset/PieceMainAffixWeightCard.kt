package com.dogeby.reliccalculator.core.ui.component.preset

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dogeby.reliccalculator.core.domain.model.AffixWeightWithInfo
import com.dogeby.reliccalculator.core.model.mihomo.index.PropertyInfo
import com.dogeby.reliccalculator.core.model.mihomo.index.RelicPiece
import com.dogeby.reliccalculator.core.model.preset.AffixWeight
import com.dogeby.reliccalculator.core.ui.R
import com.dogeby.reliccalculator.core.ui.component.image.GameImage
import com.dogeby.reliccalculator.core.ui.theme.RelicCalculatorTheme

@Composable
fun PieceMainAffixWeightCard(
    relicPiece: RelicPiece,
    affixWeightWithInfoList: List<AffixWeightWithInfo>,
    onWeightChangeFinished: (relicPiece: RelicPiece, affixId: String, weight: Float) -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(4.dp),
    colors: CardColors = CardDefaults.cardColors(),
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    Card(
        modifier = modifier,
        shape = shape,
        colors = colors,
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
            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    imageVector = if (expanded) {
                        Icons.Default.ExpandLess
                    } else {
                        Icons.Default.ExpandMore
                    },
                    contentDescription = null,
                )
            }
        }
        AnimatedVisibility(visible = expanded) {
            Column {
                HorizontalDivider(modifier = Modifier.fillMaxWidth())
                affixWeightWithInfoList.forEach { affixWeightWithInfo ->
                    AffixWeightEditItem(
                        name = affixWeightWithInfo.propertyInfo.name,
                        weight = { affixWeightWithInfo.affixWeight.weight },
                        icon = affixWeightWithInfo.propertyInfo.icon,
                        onWeightChangeFinished = {
                            onWeightChangeFinished(
                                relicPiece,
                                affixWeightWithInfo.affixWeight.affixId,
                                it,
                            )
                        },
                        color = colors.containerColor,
                        contentColor = colors.contentColor,
                    )
                }
            }
        }
    }
}

@Preview(apiLevel = 33)
@Composable
private fun PreviewPieceMainAffixWeightCard() {
    RelicCalculatorTheme {
        Box(modifier = Modifier.padding(8.dp)) {
            PieceMainAffixWeightCard(
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
                onWeightChangeFinished = { _, _, _ -> },
                shape = RoundedCornerShape(4.dp),
            )
        }
    }
}
