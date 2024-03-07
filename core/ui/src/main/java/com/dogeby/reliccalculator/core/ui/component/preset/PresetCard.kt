package com.dogeby.reliccalculator.core.ui.component.preset

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dogeby.reliccalculator.core.domain.model.AffixWeightWithInfo
import com.dogeby.reliccalculator.core.domain.model.AttrComparisonWithInfo
import com.dogeby.reliccalculator.core.domain.model.PresetWithDetails
import com.dogeby.reliccalculator.core.model.mihomo.index.RelicPiece
import com.dogeby.reliccalculator.core.model.mihomo.index.RelicSetInfo
import com.dogeby.reliccalculator.core.model.mihomo.index.sampleCharacterInfo
import com.dogeby.reliccalculator.core.model.mihomo.index.sampleElementInfo
import com.dogeby.reliccalculator.core.model.mihomo.index.samplePathInfo
import com.dogeby.reliccalculator.core.model.mihomo.index.samplePropertyInfo
import com.dogeby.reliccalculator.core.model.mihomo.index.sampleRelicSetInfo
import com.dogeby.reliccalculator.core.model.preset.AffixWeight
import com.dogeby.reliccalculator.core.model.preset.AttrComparison
import com.dogeby.reliccalculator.core.model.preset.ComparisonOperator
import com.dogeby.reliccalculator.core.ui.R
import com.dogeby.reliccalculator.core.ui.component.HorizontalGameImageText
import com.dogeby.reliccalculator.core.ui.component.VerticalAffixImageText
import com.dogeby.reliccalculator.core.ui.component.image.AffixImage
import com.dogeby.reliccalculator.core.ui.component.image.GameImage
import com.dogeby.reliccalculator.core.ui.component.image.GameImageWithRichTooltipAndBackground
import com.dogeby.reliccalculator.core.ui.theme.RelicCalculatorTheme

@Composable
fun PresetCard(
    presetWithDetails: PresetWithDetails,
    onEditMenuItemClick: (id: String) -> Unit,
    onAutoUpdateChanged: (id: String, isAutoUpdate: Boolean) -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = CardDefaults.shape,
    colors: CardColors = CardDefaults.cardColors(),
) {
    Card(
        modifier = modifier.size(360.dp, 416.dp),
        shape = shape,
        colors = colors,
    ) {
        Column {
            Surface(tonalElevation = 1.dp) {
                CharacterListItem(
                    characterId = presetWithDetails.characterId,
                    characterName = presetWithDetails.characterInfo.name,
                    characterIcon = presetWithDetails.characterInfo.icon,
                    isAutoUpdate = presetWithDetails.isAutoUpdate,
                    onEditMenuItemClick = { onEditMenuItemClick(presetWithDetails.characterId) },
                    onAutoUpdateChanged = onAutoUpdateChanged,
                )
            }
            RelicSetsAndAttrComparisonsRow(
                relicSets = presetWithDetails.relicSets,
                attrComparisons = presetWithDetails.attrComparisons,
            )
            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
            MainPieceAffixWeightsGrid(
                pieceAffixWeights = presetWithDetails.pieceMainAffixWeightsWithInfo,
            )
            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
            SubAffixWeightsWithInfoList(
                subAffixWeightsWithInfo = presetWithDetails.subAffixWeightsWithInfo,
            )
        }
    }
}

@Composable
private fun CharacterListItem(
    characterId: String,
    characterName: String,
    characterIcon: String,
    isAutoUpdate: Boolean,
    onEditMenuItemClick: () -> Unit,
    onAutoUpdateChanged: (id: String, isAutoUpdate: Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    Row(
        modifier = modifier
            .padding(
                start = 16.dp,
                top = 12.dp,
                end = 4.dp,
                bottom = 12.dp,
            ),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        GameImage(
            src = characterIcon,
            modifier = Modifier
                .size(54.dp)
                .clip(CircleShape),
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = characterName,
            modifier = Modifier.weight(1f),
            overflow = TextOverflow.Ellipsis,
            maxLines = 2,
            style = MaterialTheme.typography.titleMedium,
        )
        Box(
            modifier = Modifier.wrapContentSize(Alignment.TopStart),
        ) {
            IconButton(
                onClick = { expanded = true },
                modifier = Modifier.size(48.dp),
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = null,
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = !expanded },
            ) {
                DropdownMenuItem(
                    text = {
                        Text(text = stringResource(id = R.string.edit))
                    },
                    onClick = onEditMenuItemClick,
                )
                DropdownMenuItem(
                    text = {
                        Text(text = stringResource(id = R.string.default_preset_auto_update))
                    },
                    onClick = {
                        onAutoUpdateChanged(characterId, !isAutoUpdate)
                    },
                    trailingIcon = {
                        Switch(
                            checked = isAutoUpdate,
                            onCheckedChange = {
                                onAutoUpdateChanged(characterId, it)
                            },
                        )
                    },
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RelicSetsAndAttrComparisonsRow(
    relicSets: List<RelicSetInfo>,
    attrComparisons: List<AttrComparisonWithInfo>,
    modifier: Modifier = Modifier,
) {
    val imageSize = 32.dp
    val backgroundColor = MaterialTheme.colorScheme.surface
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(
            space = 8.dp,
            alignment = Alignment.CenterHorizontally,
        ),
    ) {
        if (relicSets.isEmpty() and attrComparisons.isEmpty()) {
            item {
                Text(text = stringResource(id = R.string.empty_list))
            }
        }
        items(
            items = relicSets,
            key = { it.id },
        ) { item ->
            GameImageWithRichTooltipAndBackground(
                src = item.icon,
                backgroundColor = backgroundColor,
                imageSize = imageSize,
                tooltipTitle = {
                    Text(
                        text = item.name,
                        style = MaterialTheme.typography.titleMedium,
                    )
                },
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    item.desc.forEach {
                        Text(text = it)
                    }
                }
            }
        }
        items(
            items = attrComparisons,
            key = { it.attrComparison.type },
        ) { item ->
            HorizontalGameImageText(
                src = item.propertyInfo.icon,
                text = "${item.attrComparison.comparisonOperator.symbol} " +
                    item.attrComparison.display,
                backgroundColor = backgroundColor,
                imageSize = imageSize,
                imageColorFilter = ColorFilter.tint(
                    contentColorFor(backgroundColor),
                ),
            )
        }
    }
}

@Composable
private fun MainPieceAffixWeightsGrid(
    pieceAffixWeights: Map<RelicPiece, List<AffixWeightWithInfo>>,
    modifier: Modifier = Modifier,
) {
    val isEmpty = pieceAffixWeights.isEmpty() or pieceAffixWeights.values.all { it.isEmpty() }
    LazyVerticalGrid(
        columns = GridCells.Fixed(
            if (isEmpty) 1 else 2,
        ),
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(
            space = 8.dp,
            alignment = Alignment.CenterVertically,
        ),
        horizontalArrangement = Arrangement.spacedBy(
            space = 8.dp,
            alignment = Alignment.CenterHorizontally,
        ),
    ) {
        if (isEmpty) {
            item {
                Text(
                    text = stringResource(id = R.string.empty_list),
                    textAlign = TextAlign.Center,
                )
            }
            return@LazyVerticalGrid
        }
        items(
            items = pieceAffixWeights.toList(),
            key = { it.first },
        ) { (relicPiece, affixWeightsWithInfo) ->
            MainPieceAffixWeightItem(
                relicPiece = relicPiece,
                affixWeightsWithInfo = affixWeightsWithInfo,
            )
        }
    }
}

@Composable
private fun MainPieceAffixWeightItem(
    relicPiece: RelicPiece,
    affixWeightsWithInfo: List<AffixWeightWithInfo>,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(4.dp),
) {
    var expanded by remember {
        mutableStateOf(false)
    }
    val itemPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
    val leadingImageSize = 32.dp
    val affixImageSize = 24.dp
    val trailingIconSize = 32.dp

    Box(
        modifier = modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.TopCenter),
    ) {
        val affixWeightWithInfo = affixWeightsWithInfo.firstOrNull()
            ?: return@Box
        Surface(shape = shape) {
            val contentColor = LocalContentColor.current
            Row(
                modifier = Modifier
                    .run {
                        if (affixWeightsWithInfo.size > 1) {
                            clickable {
                                expanded = !expanded
                            }
                        } else {
                            this
                        }
                    }
                    .padding(itemPadding),
                horizontalArrangement = Arrangement.spacedBy(2.dp),
            ) {
                GameImage(
                    src = relicPiece.icon,
                    modifier = Modifier.size(leadingImageSize),
                    colorFilter = ColorFilter.tint(contentColor),
                )
                Spacer(modifier = Modifier.width(2.dp))
                Row(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    AffixImage(
                        type = affixWeightWithInfo.affixWeight.type,
                        modifier = Modifier.size(affixImageSize),
                        colorFilter = ColorFilter.tint(contentColor),
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(text = affixWeightWithInfo.affixWeight.weight.toString())
                }
                Spacer(modifier = Modifier.width(4.dp))

                if (affixWeightsWithInfo.size <= 1) {
                    Spacer(modifier = Modifier.size(trailingIconSize))
                    return@Surface
                }
                Icon(
                    imageVector = if (expanded) {
                        Icons.Default.ExpandLess
                    } else {
                        Icons.Default.ExpandMore
                    },
                    contentDescription = null,
                    modifier = Modifier.size(trailingIconSize),
                )
            }
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            val contentColor = LocalContentColor.current
            Column(
                modifier = Modifier.padding(itemPadding),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                affixWeightsWithInfo.drop(1).forEach { affixWeightWithInfo ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        AffixImage(
                            type = affixWeightWithInfo.affixWeight.type,
                            modifier = Modifier.size(affixImageSize),
                            colorFilter = ColorFilter.tint(contentColor),
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = affixWeightWithInfo.affixWeight.weight.toString(),
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SubAffixWeightsWithInfoList(
    subAffixWeightsWithInfo: List<AffixWeightWithInfo>,
    modifier: Modifier = Modifier,
) {
    val backgroundColor = MaterialTheme.colorScheme.surface
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(
            space = 8.dp,
            alignment = Alignment.CenterHorizontally,
        ),
    ) {
        if (subAffixWeightsWithInfo.isEmpty()) {
            item {
                Text(text = stringResource(id = R.string.empty_list))
            }
        }
        items(
            items = subAffixWeightsWithInfo,
            key = { it.affixWeight.affixId },
        ) { affixWeightWithInfo ->
            VerticalAffixImageText(
                type = affixWeightWithInfo.affixWeight.type,
                text = affixWeightWithInfo.affixWeight.weight.toString(),
                modifier = Modifier.wrapContentSize(),
                backgroundColor = backgroundColor,
                imageSize = 32.dp,
                imageColorFilter = ColorFilter.tint(
                    contentColorFor(backgroundColor = backgroundColor),
                ),
            )
        }
    }
}

@Preview(apiLevel = 33)
@Composable
private fun PreviewPresetCard() {
    RelicCalculatorTheme {
        var isAutoUpdate by remember {
            mutableStateOf(false)
        }
        PresetCard(
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
                isAutoUpdate = isAutoUpdate,
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
            onEditMenuItemClick = {},
            onAutoUpdateChanged = { _, autoUpdate ->
                isAutoUpdate = autoUpdate
            },
            modifier = Modifier.padding(8.dp),
        )
    }
}
