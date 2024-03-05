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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dogeby.reliccalculator.core.domain.model.AffixWithDetails
import com.dogeby.reliccalculator.core.model.mihomo.index.samplePropertyInfo
import com.dogeby.reliccalculator.core.ui.R
import com.dogeby.reliccalculator.core.ui.component.image.GameImage
import com.dogeby.reliccalculator.core.ui.theme.RelicCalculatorTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AffixAddDialogue(
    affixes: List<AffixWithDetails>,
    onDismissRequest: () -> Unit,
    onAddBtnClick: (List<AffixWithDetails>) -> Unit,
    modifier: Modifier = Modifier,
) {
    var selectedAffixes: List<AffixWithDetails> by remember(affixes) {
        mutableStateOf(emptyList())
    }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(
                onClick = { onAddBtnClick(selectedAffixes) },
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
            FlowRow(
                modifier = modifier,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                affixes.forEach { affix ->
                    val isSelected = affix in selectedAffixes
                    FilterChip(
                        selected = isSelected,
                        onClick = {
                            if (isSelected) {
                                selectedAffixes -= affix
                                return@FilterChip
                            }
                            selectedAffixes += affix
                        },
                        label = { Text(text = affix.propertyInfo.name) },
                        leadingIcon = {
                            val contentColor = LocalContentColor.current
                            GameImage(
                                src = affix.propertyInfo.icon,
                                modifier = Modifier.size(18.dp),
                                colorFilter = ColorFilter.tint(contentColor),
                            )
                        },
                    )
                }
            }
        },
    )
}

@Preview(apiLevel = 33)
@Composable
fun PreviewAffixAddDialogue() {
    RelicCalculatorTheme {
        var shown by remember {
            mutableStateOf(true)
        }
        if (shown) {
            AffixAddDialogue(
                affixes = List(5) {
                    AffixWithDetails(
                        id = "$it",
                        propertyInfo = samplePropertyInfo,
                    )
                },
                onDismissRequest = { shown = false },
                onAddBtnClick = { shown = false },
            )
        }
    }
}
