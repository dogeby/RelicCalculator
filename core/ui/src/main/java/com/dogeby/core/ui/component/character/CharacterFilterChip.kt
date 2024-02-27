package com.dogeby.core.ui.component.character

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Surface
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
import com.dogeby.core.ui.R
import com.dogeby.core.ui.component.image.GameImage
import com.dogeby.core.ui.theme.RelicCalculatorTheme
import com.dogeby.reliccalculator.core.model.hoyo.index.ElementInfo
import com.dogeby.reliccalculator.core.model.hoyo.index.PathInfo
import com.dogeby.reliccalculator.core.model.hoyo.index.sampleElementInfo
import com.dogeby.reliccalculator.core.model.hoyo.index.samplePathInfo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterFilterChip(
    pathInfoList: List<PathInfo>,
    elementInfoList: List<ElementInfo>,
    filteredRarities: Set<Int>,
    filteredPathIds: Set<String>,
    filteredElementIds: Set<String>,
    onConfirmFilters: (
        selectedRarities: Set<Int>,
        selectedPathIds: Set<String>,
        selectedElementIds: Set<String>,
    ) -> Unit,
    modifier: Modifier = Modifier,
) {
    var selectedRarities by remember(key1 = filteredRarities) {
        mutableStateOf(filteredRarities)
    }
    var selectedPathIds by remember(key1 = filteredPathIds) {
        mutableStateOf(filteredPathIds)
    }
    var selectedElementIds by remember(key1 = filteredElementIds) {
        mutableStateOf(filteredElementIds)
    }
    var openDialog by remember {
        mutableStateOf(false)
    }
    val onDialogDismiss = {
        selectedRarities = filteredRarities
        selectedPathIds = filteredPathIds
        selectedElementIds = filteredElementIds
        openDialog = false
    }

    FilterChip(
        selected = isFiltered(
            filteredRarities = filteredRarities,
            filteredPathIds = filteredPathIds,
            filteredElementIds = filteredElementIds,
        ),
        onClick = { openDialog = true },
        label = { Text(text = stringResource(id = R.string.filter)) },
        modifier = modifier,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.FilterList,
                contentDescription = null,
            )
        },
    )

    if (openDialog) {
        BasicAlertDialog(onDismissRequest = onDialogDismiss) {
            Surface(
                shape = AlertDialogDefaults.shape,
                color = AlertDialogDefaults.containerColor,
                tonalElevation = AlertDialogDefaults.TonalElevation,
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .verticalScroll(rememberScrollState()),
                ) {
                    Text(text = stringResource(id = R.string.rarity))
                    RaritiesRow(
                        selectedRarities = selectedRarities,
                        onItemClick = {
                            if (it in selectedRarities) {
                                selectedRarities -= it
                                return@RaritiesRow
                            }
                            selectedRarities += it
                        },
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = stringResource(id = R.string.path))
                    PathsFlow(
                        selectedPathIds = selectedPathIds,
                        pathInfoList = pathInfoList,
                        onItemClick = {
                            if (it in selectedPathIds) {
                                selectedPathIds -= it
                                return@PathsFlow
                            }
                            selectedPathIds += it
                        },
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = stringResource(id = R.string.element))
                    ElementsFlow(
                        selectedElementIds = selectedElementIds,
                        elementInfoList = elementInfoList,
                        onItemClick = {
                            if (it in selectedElementIds) {
                                selectedElementIds -= it
                                return@ElementsFlow
                            }
                            selectedElementIds += it
                        },
                    )
                    CharacterFilterDialogButtons(
                        onDismissBtnClick = onDialogDismiss,
                        onClearAllBtnClick = {
                            selectedRarities = emptySet()
                            selectedPathIds = emptySet()
                            selectedElementIds = emptySet()
                        },
                        onConfirmBtnClick = {
                            onConfirmFilters(
                                selectedRarities,
                                selectedPathIds,
                                selectedElementIds,
                            )
                            openDialog = false
                        },
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun RaritiesRow(
    selectedRarities: Set<Int>,
    onItemClick: (rarity: Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        listOf(4, 5).forEach { rarity ->
            FilterChip(
                selected = rarity in selectedRarities,
                onClick = { onItemClick(rarity) },
                label = { Text(text = rarity.toString()) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                    )
                },
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun PathsFlow(
    selectedPathIds: Set<String>,
    pathInfoList: List<PathInfo>,
    onItemClick: (pathId: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        pathInfoList.forEach { pathInfo ->
            FilterChip(
                selected = pathInfo.id in selectedPathIds,
                onClick = { onItemClick(pathInfo.id) },
                label = { Text(text = pathInfo.name) },
                leadingIcon = {
                    val contentColor = LocalContentColor.current
                    GameImage(
                        src = pathInfo.icon,
                        modifier = Modifier.size(18.dp),
                        colorFilter = ColorFilter.tint(contentColor),
                    )
                },
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ElementsFlow(
    selectedElementIds: Set<String>,
    elementInfoList: List<ElementInfo>,
    onItemClick: (elementId: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        elementInfoList.forEach { elementInfo ->
            FilterChip(
                selected = elementInfo.id in selectedElementIds,
                onClick = { onItemClick(elementInfo.id) },
                label = { Text(text = elementInfo.name) },
                leadingIcon = {
                    val contentColor = LocalContentColor.current
                    GameImage(
                        src = elementInfo.icon,
                        modifier = Modifier.size(18.dp),
                        colorFilter = ColorFilter.tint(contentColor),
                    )
                },
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun CharacterFilterDialogButtons(
    onDismissBtnClick: () -> Unit,
    onClearAllBtnClick: () -> Unit,
    onConfirmBtnClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    FlowRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        TextButton(
            onClick = onDismissBtnClick,
        ) {
            Text(text = stringResource(id = R.string.dismiss))
        }
        FlowRow {
            TextButton(
                onClick = onClearAllBtnClick,
            ) {
                Text(text = stringResource(id = R.string.clear_all))
            }
            Spacer(modifier = Modifier.width(16.dp))
            TextButton(
                onClick = onConfirmBtnClick,
            ) {
                Text(text = stringResource(id = R.string.confirm))
            }
        }
    }
}

private fun isFiltered(
    filteredRarities: Set<Int>,
    filteredPathIds: Set<String>,
    filteredElementIds: Set<String>,
) = filteredRarities.isNotEmpty() or filteredPathIds.isNotEmpty() or filteredElementIds.isNotEmpty()

@Preview(apiLevel = 33)
@Composable
private fun PreviewCharacterFilterChip() {
    RelicCalculatorTheme {
        CharacterFilterChip(
            pathInfoList = List(7) { samplePathInfo.copy(id = "$it", name = "$it") },
            elementInfoList = List(7) { sampleElementInfo.copy(id = "$it", name = "$it") },
            filteredRarities = setOf(5),
            filteredPathIds = setOf("0"),
            filteredElementIds = setOf("0"),
            onConfirmFilters = { _, _, _ -> },
        )
    }
}
