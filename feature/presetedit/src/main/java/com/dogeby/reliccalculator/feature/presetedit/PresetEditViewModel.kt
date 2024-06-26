package com.dogeby.reliccalculator.feature.presetedit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dogeby.reliccalculator.core.common.decoder.StringDecoder
import com.dogeby.reliccalculator.core.domain.index.GetRelicSetInfoListUseCase
import com.dogeby.reliccalculator.core.domain.model.AttrComparisonWithInfo
import com.dogeby.reliccalculator.core.domain.model.PresetWithDetails
import com.dogeby.reliccalculator.core.domain.preset.GetAttrComparisonWithInfoListUseCase
import com.dogeby.reliccalculator.core.domain.preset.GetMainAffixWeightWithInfoUseCase
import com.dogeby.reliccalculator.core.domain.preset.GetPresetWithDetailsByIdUseCase
import com.dogeby.reliccalculator.core.domain.preset.GetSubAffixWeightWithInfoUseCase
import com.dogeby.reliccalculator.core.domain.preset.UpdatePresetUseCase
import com.dogeby.reliccalculator.core.model.mihomo.index.RelicPiece
import com.dogeby.reliccalculator.core.model.preset.AffixWeight
import com.dogeby.reliccalculator.core.model.preset.AttrComparison
import com.dogeby.reliccalculator.core.model.preset.ComparisonOperator
import com.dogeby.reliccalculator.core.ui.component.preset.AffixAddDialogueUiState
import com.dogeby.reliccalculator.core.ui.component.preset.AttrComparisonAddDialogueUiState
import com.dogeby.reliccalculator.core.ui.component.preset.AttrComparisonEditListUiState
import com.dogeby.reliccalculator.core.ui.component.preset.PieceMainAffixWeightListUiState
import com.dogeby.reliccalculator.core.ui.component.preset.RelicSetFilterUiState
import com.dogeby.reliccalculator.core.ui.component.preset.RelicSetFiltersUiState
import com.dogeby.reliccalculator.core.ui.component.preset.SubAffixWeightListUiState
import com.dogeby.reliccalculator.feature.presetedit.model.PresetEditMessageUiState
import com.dogeby.reliccalculator.feature.presetedit.navigation.PresetEditArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class PresetEditViewModel @Inject constructor(
    getPresetWithDetailsByIdUseCase: GetPresetWithDetailsByIdUseCase,
    getRelicSetInfoListUseCase: GetRelicSetInfoListUseCase,
    getAttrComparisonWithInfoListUseCase: GetAttrComparisonWithInfoListUseCase,
    getMainAffixWeightWithInfoUseCase: GetMainAffixWeightWithInfoUseCase,
    getSubAffixWeightWithInfoUseCase: GetSubAffixWeightWithInfoUseCase,
    stringDecoder: StringDecoder,
    private val savedStateHandle: SavedStateHandle,
    private val updatePresetUseCase: UpdatePresetUseCase,
) : ViewModel() {

    private val presetEditArgs = PresetEditArgs(savedStateHandle, stringDecoder)

    private val presetWithDetails: StateFlow<PresetWithDetails?> = getPresetWithDetailsByIdUseCase(
        presetEditArgs.presetId,
    )
        .map { result ->
            result.getOrNull()?.also { preset ->
                setPresetToSaveStateHandle(preset)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = null,
        )

    private val relicSetInfoList = getRelicSetInfoListUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null,
        )
    private val selectedRelicSetIds: StateFlow<List<String>?> = savedStateHandle
        .getStateFlow(
            key = SELECTED_RELIC_SET_IDS_KEY,
            initialValue = null,
        )
        .onStart {
            emit(null)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null,
        )
    val relicSetFiltersUiState: StateFlow<RelicSetFiltersUiState> = relicSetInfoList
        .combine(selectedRelicSetIds) { relicSetInfoList, selectedRelicSetIds ->
            if (relicSetInfoList == null || selectedRelicSetIds == null) {
                return@combine RelicSetFiltersUiState.Loading
            }
            RelicSetFiltersUiState.Success(
                relicSetInfoList.map { relicSetInfo ->
                    RelicSetFilterUiState(
                        selected = relicSetInfo.id in selectedRelicSetIds,
                        relicSetInfo = relicSetInfo,
                    )
                },
            )
        }
        .onStart {
            emit(RelicSetFiltersUiState.Loading)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = RelicSetFiltersUiState.Loading,
        )

    private val attrComparisonWithInfoList = getAttrComparisonWithInfoListUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null,
        )
    private val editedAttrComparisons: StateFlow<List<AttrComparison>?> = savedStateHandle
        .getStateFlow(
            key = EDITED_ATTR_COMPARISONS_KEY,
            initialValue = null,
        )
        .onStart {
            emit(null)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null,
        )
    val attrComparisonEditListUiState: StateFlow<AttrComparisonEditListUiState> =
        attrComparisonWithInfoList.combine(
            editedAttrComparisons,
        ) { attrComparisonWithInfoList, editedAttrComparisons ->
            if (attrComparisonWithInfoList == null || editedAttrComparisons == null) {
                return@combine AttrComparisonEditListUiState.Loading
            }
            AttrComparisonEditListUiState.Success(
                editedAttrComparisons.map { attrComparison ->
                    AttrComparisonWithInfo(
                        attrComparison = attrComparison,
                        propertyInfo = attrComparisonWithInfoList.find {
                            attrComparison.type == it.attrComparison.type
                        }?.propertyInfo ?: return@combine AttrComparisonEditListUiState.Loading,
                    )
                },
            )
        }
            .onStart {
                emit(AttrComparisonEditListUiState.Loading)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = AttrComparisonEditListUiState.Loading,
            )
    val attrComparisonAddDialogueUiState: StateFlow<AttrComparisonAddDialogueUiState> =
        attrComparisonWithInfoList.combine(
            editedAttrComparisons,
        ) { attrComparisons, editedAttrComparisons ->
            if (attrComparisons == null || editedAttrComparisons == null) {
                return@combine AttrComparisonAddDialogueUiState.Loading
            }

            val editedAttrComparisonIds = editedAttrComparisons.map { it.type }
            val uneditedAttrComparison = attrComparisons.filterNot {
                it.attrComparison.type in editedAttrComparisonIds
            }
            if (uneditedAttrComparison.isEmpty()) {
                return@combine AttrComparisonAddDialogueUiState.Empty
            }

            AttrComparisonAddDialogueUiState.Success(
                attrComparisons = uneditedAttrComparison,
            )
        }
            .onStart {
                AttrComparisonAddDialogueUiState.Loading
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = AttrComparisonAddDialogueUiState.Loading,
            )

    private val editedMainAffixWeights: StateFlow<Map<RelicPiece, List<AffixWeight>>?> =
        savedStateHandle
            .getStateFlow(
                key = EDITED_PIECE_MAIN_AFFIX_WEIGHT_KEY,
                initialValue = null,
            )
            .onStart {
                emit(null)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = null,
            )
    val pieceMainAffixWeightListUiState: StateFlow<PieceMainAffixWeightListUiState> =
        editedMainAffixWeights.combine(
            getMainAffixWeightWithInfoUseCase(),
        ) { editedMainAffixWeights, mainAffixWeightWithInfoMap ->
            if (editedMainAffixWeights == null) {
                return@combine PieceMainAffixWeightListUiState.Loading
            }
            PieceMainAffixWeightListUiState.Success(
                pieceToAffixWeightsMap = editedMainAffixWeights
                    .mapValues { (relicPiece, affixWeights) ->
                        affixWeights.map { affixWeight ->
                            mainAffixWeightWithInfoMap[relicPiece]?.find {
                                it.affixWeight.affixId == affixWeight.affixId
                            }?.copy(
                                affixWeight = affixWeight,
                            ) ?: return@combine PieceMainAffixWeightListUiState.Loading
                        }
                    },
            )
        }
            .onStart {
                PieceMainAffixWeightListUiState.Loading
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = PieceMainAffixWeightListUiState.Loading,
            )

    private val subAffixWeightWithInfoList = getSubAffixWeightWithInfoUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null,
        )
    private val editedSubAffixWeights: StateFlow<List<AffixWeight>?> = savedStateHandle
        .getStateFlow(
            key = EDITED_SUB_AFFIX_WEIGHT_KEY,
            initialValue = null,
        )
        .onStart {
            emit(null)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null,
        )
    val subAffixWeightListUiState: StateFlow<SubAffixWeightListUiState> =
        editedSubAffixWeights.combine(
            subAffixWeightWithInfoList,
        ) { editedSubAffixWeights, subAffixWeightWithInfoList ->
            if (editedSubAffixWeights == null || subAffixWeightWithInfoList == null) {
                return@combine SubAffixWeightListUiState.Loading
            }
            SubAffixWeightListUiState.Success(
                affixWeightWithInfoList = editedSubAffixWeights.map { affixWeight ->
                    subAffixWeightWithInfoList.find {
                        it.affixWeight.affixId == affixWeight.affixId
                    }
                        ?.copy(
                            affixWeight = affixWeight,
                        ) ?: return@combine SubAffixWeightListUiState.Loading
                },
            )
        }
            .onStart {
                SubAffixWeightListUiState.Loading
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = SubAffixWeightListUiState.Loading,
            )
    val subAffixAddDialogueUiState: StateFlow<AffixAddDialogueUiState> =
        subAffixWeightWithInfoList.combine(
            editedSubAffixWeights,
        ) { affixWeights, editedSubAffixWeights ->
            if (affixWeights == null || editedSubAffixWeights == null) {
                return@combine AffixAddDialogueUiState.Loading
            }
            val editedAffixIds = editedSubAffixWeights.map { it.affixId }
            val uneditedAffixes = affixWeights.filterNot {
                it.affixWeight.affixId in editedAffixIds
            }
            if (uneditedAffixes.isEmpty()) {
                return@combine AffixAddDialogueUiState.Empty
            }

            AffixAddDialogueUiState.Success(
                affixes = uneditedAffixes,
            )
        }
            .onStart {
                AffixAddDialogueUiState.Loading
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = AffixAddDialogueUiState.Loading,
            )

    private val _snackbarMessage = MutableSharedFlow<PresetEditMessageUiState>()
    val snackbarMessage: SharedFlow<PresetEditMessageUiState> = _snackbarMessage

    private fun <T> SavedStateHandle.setListIfNotNull(
        key: String,
        list: List<T>?,
    ) {
        list?.let {
            set(key, it)
        }
    }

    private fun <K, V> SavedStateHandle.setMapIfNotNull(
        key: String,
        map: Map<K, List<V>>?,
    ) {
        map?.let {
            set(key, it)
        }
    }

    private fun setPresetToSaveStateHandle(preset: PresetWithDetails) {
        savedStateHandle.setListIfNotNull(
            SELECTED_RELIC_SET_IDS_KEY,
            preset.relicSets.map { it.id },
        )
        savedStateHandle.setListIfNotNull(
            EDITED_ATTR_COMPARISONS_KEY,
            preset.attrComparisons.map {
                it.attrComparison
            },
        )
        savedStateHandle.setMapIfNotNull(
            EDITED_PIECE_MAIN_AFFIX_WEIGHT_KEY,
            preset
                .pieceMainAffixWeightsWithInfo
                .mapValues { (_, affixWeightWithInfoList) ->
                    affixWeightWithInfoList.map {
                        it.affixWeight
                    }
                },
        )
        savedStateHandle.setListIfNotNull(
            EDITED_SUB_AFFIX_WEIGHT_KEY,
            preset.subAffixWeightsWithInfo.map {
                it.affixWeight
            },
        )
    }

    private fun emitMessage(presetEditMessageUiState: PresetEditMessageUiState) {
        viewModelScope.launch {
            _snackbarMessage.emit(presetEditMessageUiState)
        }
    }

    fun updatePreset() {
        val preset = presetWithDetails.value
        val relicSetIds = selectedRelicSetIds.value
        val pieceMainAffixWeights = editedMainAffixWeights.value
        val subAffixWeights = editedSubAffixWeights.value
        val attrComparisons = editedAttrComparisons.value

        if (
            preset == null ||
            relicSetIds == null ||
            pieceMainAffixWeights == null ||
            subAffixWeights == null ||
            attrComparisons == null
        ) {
            emitMessage(PresetEditMessageUiState.EditError)
            return
        }

        if (
            attrComparisons.any {
                it.display.toFloatOrNull() == null ||
                    it.display.firstOrNull() == '.' ||
                    it.display.lastOrNull() == '.'
            }
        ) {
            emitMessage(PresetEditMessageUiState.EditError)
            return
        }

        viewModelScope.launch {
            updatePresetUseCase(
                characterId = preset.characterId,
                relicSetIds = relicSetIds,
                pieceMainAffixWeights = pieceMainAffixWeights,
                subAffixWeights = subAffixWeights,
                attrComparisons = attrComparisons,
                isAutoUpdate = false,
            ).onSuccess {
                when (it) {
                    1 -> _snackbarMessage.emit(PresetEditMessageUiState.EditSuccess)
                    0 -> _snackbarMessage.emit(PresetEditMessageUiState.NoChanges)
                    else -> emitMessage(PresetEditMessageUiState.EditError)
                }
            }.onFailure {
                emitMessage(PresetEditMessageUiState.EditError)
            }
        }
    }

    fun resetEditedPreset() {
        val preset = presetWithDetails.value
        if (preset == null) {
            emitMessage(PresetEditMessageUiState.ResetError)
            return
        }
        setPresetToSaveStateHandle(preset)
        emitMessage(PresetEditMessageUiState.ResetSuccess)
    }

    fun addAttrComparison(types: List<String>) {
        val attrComparisonWithInfoListValue = attrComparisonWithInfoList.value
        val editedAttrComparisonsValue = editedAttrComparisons.value

        if (attrComparisonWithInfoListValue == null || editedAttrComparisonsValue == null) {
            emitMessage(PresetEditMessageUiState.AddError)
            return
        }

        val newAttrComparisons = attrComparisonWithInfoListValue
            .filter { it.attrComparison.type in types }
            .map { it.attrComparison }

        if (newAttrComparisons.isEmpty()) {
            emitMessage(PresetEditMessageUiState.AddError)
            return
        }

        val updatedAttrComparisons = editedAttrComparisonsValue
            .toMutableList()
            .apply {
                addAll(newAttrComparisons)
            }
            .toList()

        savedStateHandle[EDITED_ATTR_COMPARISONS_KEY] = updatedAttrComparisons
    }

    fun deleteAttrComparison(type: String) {
        val editedAttrComparisonsValue = editedAttrComparisons.value
        if (editedAttrComparisonsValue == null) {
            emitMessage(PresetEditMessageUiState.DeleteError)
            return
        }

        val filteredAttrComparisons = editedAttrComparisonsValue.filterNot {
            type == it.type
        }

        savedStateHandle[EDITED_ATTR_COMPARISONS_KEY] = filteredAttrComparisons
    }

    fun modifyAttrComparison(
        type: String,
        comparisonOperator: ComparisonOperator,
        inputComparedValue: String,
    ) {
        val editedAttrComparisonsValue = editedAttrComparisons.value
        if (editedAttrComparisonsValue == null) {
            emitMessage(PresetEditMessageUiState.ModifyError)
            return
        }
        val filteredInputComparedValue = inputComparedValue
            .filter { it.isDigit() || it == '.' }
        val inputComparedValueAsFloat = filteredInputComparedValue
            .toFloatOrNull()
        val modifiedAttrComparisons = editedAttrComparisonsValue.map { attrComparison ->
            if (type == attrComparison.type) {
                val newComparedValue = if (inputComparedValueAsFloat == null) {
                    attrComparison.comparedValue
                } else if (attrComparison.percent) {
                    inputComparedValueAsFloat / 100
                } else {
                    inputComparedValueAsFloat
                }
                attrComparison.copy(
                    comparisonOperator = comparisonOperator,
                    comparedValue = newComparedValue,
                    display = filteredInputComparedValue,
                )
            } else {
                attrComparison
            }
        }

        savedStateHandle[EDITED_ATTR_COMPARISONS_KEY] = modifiedAttrComparisons
    }

    fun modifyRelicSet(
        id: String,
        selected: Boolean,
    ) {
        val selectedRelicSetIdsValue = selectedRelicSetIds.value
        if (selectedRelicSetIdsValue == null) {
            emitMessage(PresetEditMessageUiState.ModifyError)
            return
        }

        val modifiedRelicSets = if (selected) {
            selectedRelicSetIdsValue.toMutableList().apply { add(id) }
        } else {
            selectedRelicSetIdsValue.toMutableList().apply { remove(id) }
        }

        savedStateHandle[SELECTED_RELIC_SET_IDS_KEY] = modifiedRelicSets.toList()
    }

    fun modifyPieceMainAffixWeight(
        relicPiece: RelicPiece,
        affixId: String,
        weight: Float,
    ) {
        val editedMainAffixWeightsValue = editedMainAffixWeights.value
        if (editedMainAffixWeightsValue == null) {
            emitMessage(PresetEditMessageUiState.ModifyError)
            return
        }

        val modifiedMainAffixWeight = editedMainAffixWeightsValue[relicPiece]
            ?.map { affixWeight ->
                if (affixId == affixWeight.affixId) {
                    return@map affixWeight.copy(
                        weight = weight,
                    )
                }
                affixWeight
            }
            ?: run {
                emitMessage(PresetEditMessageUiState.ModifyError)
                return
            }
        val newEditedMainAffixWeights = editedMainAffixWeightsValue
            .toMutableMap()
            .apply {
                this[relicPiece] = modifiedMainAffixWeight
            }

        savedStateHandle[EDITED_PIECE_MAIN_AFFIX_WEIGHT_KEY] = newEditedMainAffixWeights
    }

    fun addSubAffixWeight(affixIds: List<String>) {
        val subAffixWeightWithInfoListValue = subAffixWeightWithInfoList.value
        val editedSubAffixWeightsValue = editedSubAffixWeights.value

        if (subAffixWeightWithInfoListValue == null || editedSubAffixWeightsValue == null) {
            emitMessage(PresetEditMessageUiState.AddError)
            return
        }

        val newSubAffixWeights = subAffixWeightWithInfoListValue
            .filter { it.affixWeight.affixId in affixIds }
            .map { it.affixWeight }
        if (newSubAffixWeights.isEmpty()) {
            emitMessage(PresetEditMessageUiState.AddError)
            return
        }

        val updatedSubAffixWeights = editedSubAffixWeightsValue
            .toMutableList()
            .apply {
                addAll(newSubAffixWeights)
            }
            .toList()

        savedStateHandle[EDITED_SUB_AFFIX_WEIGHT_KEY] = updatedSubAffixWeights
    }

    fun deleteSubAffixWeight(affixId: String) {
        val editedSubAffixWeightsValue = editedSubAffixWeights.value
        if (editedSubAffixWeightsValue == null) {
            emitMessage(PresetEditMessageUiState.DeleteError)
            return
        }

        val filteredSubAffixWeights = editedSubAffixWeightsValue.filterNot {
            affixId == it.affixId
        }

        savedStateHandle[EDITED_SUB_AFFIX_WEIGHT_KEY] = filteredSubAffixWeights
    }

    fun modifySubAffixWeight(
        affixId: String,
        weight: Float,
    ) {
        val editedSubAffixWeightsValue = editedSubAffixWeights.value
        if (editedSubAffixWeightsValue == null) {
            emitMessage(PresetEditMessageUiState.ModifyError)
            return
        }

        val modifiedSubAffix = editedSubAffixWeightsValue.map { affixWeight ->
            if (affixId == affixWeight.affixId) {
                return@map affixWeight.copy(
                    weight = weight,
                )
            }
            affixWeight
        }

        savedStateHandle[EDITED_SUB_AFFIX_WEIGHT_KEY] = modifiedSubAffix
    }

    private companion object {

        const val SELECTED_RELIC_SET_IDS_KEY = "selectedRelicSetIds"
        const val EDITED_ATTR_COMPARISONS_KEY = "editedAttrComparisons"
        const val EDITED_PIECE_MAIN_AFFIX_WEIGHT_KEY = "editedPieceMainAffixWeight"
        const val EDITED_SUB_AFFIX_WEIGHT_KEY = "editedSubAffixWeight"
    }
}
