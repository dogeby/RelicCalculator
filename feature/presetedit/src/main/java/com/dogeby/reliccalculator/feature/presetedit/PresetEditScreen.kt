package com.dogeby.reliccalculator.feature.presetedit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dogeby.reliccalculator.core.model.mihomo.index.RelicPiece
import com.dogeby.reliccalculator.core.model.preset.ComparisonOperator
import com.dogeby.reliccalculator.core.ui.component.EmptyState
import com.dogeby.reliccalculator.core.ui.component.LoadingState
import com.dogeby.reliccalculator.core.ui.component.preset.AffixAddDialogue
import com.dogeby.reliccalculator.core.ui.component.preset.AffixAddDialogueUiState
import com.dogeby.reliccalculator.core.ui.component.preset.AttrComparisonAddDialogue
import com.dogeby.reliccalculator.core.ui.component.preset.AttrComparisonAddDialogueUiState
import com.dogeby.reliccalculator.core.ui.component.preset.AttrComparisonEditListUiState
import com.dogeby.reliccalculator.core.ui.component.preset.PieceMainAffixWeightListUiState
import com.dogeby.reliccalculator.core.ui.component.preset.RelicSetFiltersFlowRow
import com.dogeby.reliccalculator.core.ui.component.preset.RelicSetFiltersUiState
import com.dogeby.reliccalculator.core.ui.component.preset.SubAffixWeightListUiState
import com.dogeby.reliccalculator.core.ui.component.preset.attrComparisonEditList
import com.dogeby.reliccalculator.core.ui.component.preset.pieceMainAffixWeightList
import com.dogeby.reliccalculator.core.ui.component.preset.subAffixWeightList
import com.dogeby.reliccalculator.feature.presetedit.model.PresetEditMessageUiState

@Composable
fun PresetEditRoute(
    snackbarHostState: SnackbarHostState,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PresetEditViewModel = hiltViewModel(),
) {
    val relicSetFiltersUiState by viewModel.relicSetFiltersUiState.collectAsStateWithLifecycle()
    val pieceMainAffixWeightListUiState by viewModel
        .pieceMainAffixWeightListUiState
        .collectAsStateWithLifecycle()
    val subAffixWeightListUiState by viewModel
        .subAffixWeightListUiState
        .collectAsStateWithLifecycle()
    val subAffixAddDialogueUiState by viewModel
        .subAffixAddDialogueUiState
        .collectAsStateWithLifecycle()
    val attrComparisonEditListUiState by viewModel
        .attrComparisonEditListUiState
        .collectAsStateWithLifecycle()
    val attrComparisonAddDialogueUiState by viewModel
        .attrComparisonAddDialogueUiState
        .collectAsStateWithLifecycle()

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.snackbarMessage.collect {
            val message = context.getString(
                when (it) {
                    PresetEditMessageUiState.AddError -> R.string.add_error_message
                    PresetEditMessageUiState.DeleteError -> R.string.delete_error_message
                    PresetEditMessageUiState.EditError -> R.string.edit_error_message
                    PresetEditMessageUiState.EditSuccess -> R.string.edit_success_message
                    PresetEditMessageUiState.ModifyError -> R.string.modify_error_message
                    PresetEditMessageUiState.NoChanges -> R.string.no_changes_message
                    PresetEditMessageUiState.ResetError -> R.string.reset_error_message
                    PresetEditMessageUiState.ResetSuccess -> R.string.reset_success_message
                },
            )
            snackbarHostState.showSnackbar(
                message = message,
            )
        }
    }

    PresetEditScreen(
        relicSetFiltersUiState = relicSetFiltersUiState,
        pieceMainAffixWeightListUiState = pieceMainAffixWeightListUiState,
        subAffixWeightListUiState = subAffixWeightListUiState,
        subAffixAddDialogueUiState = subAffixAddDialogueUiState,
        attrComparisonEditListUiState = attrComparisonEditListUiState,
        attrComparisonAddDialogueUiState = attrComparisonAddDialogueUiState,
        onUpdatePreset = {
            viewModel.updatePreset()
            onNavigateUp()
        },
        onResetEditedPreset = viewModel::resetEditedPreset,
        onModifyRelicSet = viewModel::modifyRelicSet,
        onModifyPieceMainAffixWeight = viewModel::modifyPieceMainAffixWeight,
        onAddSubAffixWeight = viewModel::addSubAffixWeight,
        onDeleteSubAffixWeight = viewModel::deleteSubAffixWeight,
        onModifySubAffixWeight = viewModel::modifySubAffixWeight,
        onAddAttrComparison = viewModel::addAttrComparison,
        onDeleteAttrComparison = viewModel::deleteAttrComparison,
        onModifyAttrComparison = viewModel::modifyAttrComparison,
        modifier = modifier,
    )
}

@Composable
fun PresetEditScreen(
    relicSetFiltersUiState: RelicSetFiltersUiState,
    pieceMainAffixWeightListUiState: PieceMainAffixWeightListUiState,
    subAffixWeightListUiState: SubAffixWeightListUiState,
    subAffixAddDialogueUiState: AffixAddDialogueUiState,
    attrComparisonEditListUiState: AttrComparisonEditListUiState,
    attrComparisonAddDialogueUiState: AttrComparisonAddDialogueUiState,
    modifier: Modifier = Modifier,
    onUpdatePreset: () -> Unit,
    onResetEditedPreset: () -> Unit,
    onModifyRelicSet: (id: String, selected: Boolean) -> Unit,
    onModifyPieceMainAffixWeight: (RelicPiece, String, Float) -> Unit,
    onAddSubAffixWeight: (affixIds: List<String>) -> Unit,
    onDeleteSubAffixWeight: (affixId: String) -> Unit,
    onModifySubAffixWeight: (affixId: String, weight: Float) -> Unit,
    onAddAttrComparison: (type: String) -> Unit,
    onDeleteAttrComparison: (type: String) -> Unit,
    onModifyAttrComparison: (String, ComparisonOperator?, Float?) -> Unit,
) {
    var openAttrComparisonAddDialogue by rememberSaveable {
        mutableStateOf(false)
    }
    var openSubAffixAddDialogue by rememberSaveable {
        mutableStateOf(false)
    }

    if (openAttrComparisonAddDialogue) {
        AttrComparisonAddDialogue(
            attrComparisonAddDialogueUiState = attrComparisonAddDialogueUiState,
            onDismissRequest = { openAttrComparisonAddDialogue = false },
            onAddBtnClick = {
                onAddAttrComparison(it)
                openAttrComparisonAddDialogue = false
            },
        )
    }

    if (openSubAffixAddDialogue) {
        AffixAddDialogue(
            affixAddDialogueUiState = subAffixAddDialogueUiState,
            onDismissRequest = { openSubAffixAddDialogue = false },
            onAddBtnClick = {
                onAddSubAffixWeight(it)
                openSubAffixAddDialogue = false
            },
        )
    }
    Box(modifier) {
        if (relicSetFiltersUiState is RelicSetFiltersUiState.Success &&
            attrComparisonEditListUiState is AttrComparisonEditListUiState.Success &&
            pieceMainAffixWeightListUiState is PieceMainAffixWeightListUiState.Success &&
            subAffixWeightListUiState is SubAffixWeightListUiState.Success
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(
                    start = 8.dp,
                    top = 8.dp,
                    end = 8.dp,
                    bottom = presetEditBottomBarHeight,
                ),
            ) {
                relicSetFiltersWithTitle(
                    relicSetFiltersUiState = relicSetFiltersUiState,
                    onModifyRelicSet = onModifyRelicSet,
                )
                pieceMainAffixWeightListWithTitle(
                    pieceMainAffixWeightListUiState = pieceMainAffixWeightListUiState,
                    onModifyPieceMainAffixWeight = onModifyPieceMainAffixWeight,
                )
                subAffixWeightListWithTitle(
                    subAffixWeightListUiState = subAffixWeightListUiState,
                    onSubAffixWeightAddBtnClick = {
                        openSubAffixAddDialogue = true
                    },
                    onModifySubAffixWeight = onModifySubAffixWeight,
                    onDeleteSubAffixWeight = onDeleteSubAffixWeight,
                )
                attrComparisonEditListWithTitle(
                    attrComparisonEditListUiState = attrComparisonEditListUiState,
                    onAttrComparisonAddBtnClick = {
                        openAttrComparisonAddDialogue = true
                    },
                    onModifyAttrComparison = onModifyAttrComparison,
                    onDeleteAttrComparison = onDeleteAttrComparison,
                )
            }
        } else {
            LoadingState()
        }
        PresetEditBottomBar(
            onSave = onUpdatePreset,
            onReset = onResetEditedPreset,
            modifier = Modifier.align(Alignment.BottomCenter),
        )
    }
}

private fun LazyListScope.relicSetFiltersWithTitle(
    relicSetFiltersUiState: RelicSetFiltersUiState.Success,
    onModifyRelicSet: (id: String, selected: Boolean) -> Unit,
) {
    item {
        Text(
            text = stringResource(id = R.string.relic_set),
            style = MaterialTheme.typography.titleMedium,
        )
    }
    item {
        if (relicSetFiltersUiState.relicSets.isEmpty()) {
            EmptyState()
        } else {
            RelicSetFiltersFlowRow(
                relicSetFiltersUiState = relicSetFiltersUiState,
                onFilterChipSelectedChanged = onModifyRelicSet,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            )
        }
    }
}

private fun LazyListScope.pieceMainAffixWeightListWithTitle(
    pieceMainAffixWeightListUiState: PieceMainAffixWeightListUiState,
    onModifyPieceMainAffixWeight: (
        relicPiece: RelicPiece,
        affixId: String,
        weight: Float,
    ) -> Unit,
) {
    item {
        Text(
            text = stringResource(id = R.string.main_stat_by_piece),
            style = MaterialTheme.typography.titleMedium,
        )
    }
    pieceMainAffixWeightList(
        pieceMainAffixWeightListUiState = pieceMainAffixWeightListUiState,
        onMainAffixWeightChangeFinished = onModifyPieceMainAffixWeight,
    )
}

private fun LazyListScope.subAffixWeightListWithTitle(
    subAffixWeightListUiState: SubAffixWeightListUiState.Success,
    onSubAffixWeightAddBtnClick: () -> Unit,
    onModifySubAffixWeight: (affixId: String, weight: Float) -> Unit,
    onDeleteSubAffixWeight: (affixId: String) -> Unit,
) {
    item {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(id = R.string.sub_stat),
                style = MaterialTheme.typography.titleMedium,
            )
            IconButton(onClick = onSubAffixWeightAddBtnClick) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                )
            }
        }
    }
    if (subAffixWeightListUiState.affixWeightWithInfoList.isEmpty()) {
        item {
            EmptyState()
        }
    } else {
        subAffixWeightList(
            subAffixWeightListUiState = subAffixWeightListUiState,
            onWeightChangeFinished = onModifySubAffixWeight,
            onDeleteSubAffixWeight = onDeleteSubAffixWeight,
        )
    }
}

private fun LazyListScope.attrComparisonEditListWithTitle(
    attrComparisonEditListUiState: AttrComparisonEditListUiState.Success,
    onAttrComparisonAddBtnClick: () -> Unit,
    onModifyAttrComparison: (String, ComparisonOperator?, Float?) -> Unit,
    onDeleteAttrComparison: (type: String) -> Unit,
) {
    item {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(id = R.string.stat_comparison),
                style = MaterialTheme.typography.titleMedium,
            )
            IconButton(onClick = onAttrComparisonAddBtnClick) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                )
            }
        }
    }
    if (attrComparisonEditListUiState.attrComparisonWithInfoList.isEmpty()) {
        item {
            EmptyState()
        }
    } else {
        attrComparisonEditList(
            attrComparisonEditListUiState = attrComparisonEditListUiState,
            onComparisonOperatorChanged = { type, comparisonOperator ->
                onModifyAttrComparison(
                    type,
                    comparisonOperator,
                    null,
                )
            },
            onComparedValueChanged = { type: String, comparedValue: String ->
                onModifyAttrComparison(
                    type,
                    null,
                    comparedValue.toFloatOrNull(),
                )
            },
            onDeleteAttrComparisonEditItem = onDeleteAttrComparison,
        )
    }
}
