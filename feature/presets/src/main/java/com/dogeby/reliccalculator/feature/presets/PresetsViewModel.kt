package com.dogeby.reliccalculator.feature.presets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dogeby.reliccalculator.core.domain.index.GetElementInfoMapUseCase
import com.dogeby.reliccalculator.core.domain.index.GetPathInfoMapUseCase
import com.dogeby.reliccalculator.core.domain.model.PresetWithDetails
import com.dogeby.reliccalculator.core.domain.preference.GetPresetListPreferencesDataUseCase
import com.dogeby.reliccalculator.core.domain.preference.SetPresetListPreferencesDataUseCase
import com.dogeby.reliccalculator.core.domain.preset.GetPresetWithDetailsListUseCase
import com.dogeby.reliccalculator.core.domain.preset.UpdatePresetAutoUpdateUseCase
import com.dogeby.reliccalculator.core.model.preferences.CharacterSortField
import com.dogeby.reliccalculator.core.ui.component.preset.CharacterListOptionBarUiState
import com.dogeby.reliccalculator.core.ui.component.preset.PresetListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class PresetsViewModel @Inject constructor(
    private val updatePresetAutoUpdateUseCase: UpdatePresetAutoUpdateUseCase,
    private val setPresetListPreferencesDataUseCase: SetPresetListPreferencesDataUseCase,
    getPathInfoMapUseCase: GetPathInfoMapUseCase,
    getElementInfoMapUseCase: GetElementInfoMapUseCase,
    getPresetListPreferencesDataUseCase: GetPresetListPreferencesDataUseCase,
    getPresetWithDetailsListUseCase: GetPresetWithDetailsListUseCase,
) : ViewModel() {

    val characterListOptionBarUiState: StateFlow<CharacterListOptionBarUiState> = combine(
        getPathInfoMapUseCase(),
        getElementInfoMapUseCase(),
        getPresetListPreferencesDataUseCase(),
    ) { pathInfoList, elementInfoList, presetsPreferencesData ->
        CharacterListOptionBarUiState.Success(
            pathInfoList = pathInfoList.values.toList(),
            elementInfoList = elementInfoList.values.toList(),
            characterListPreferencesData = presetsPreferencesData,
        )
    }
        .onStart<CharacterListOptionBarUiState> {
            emit(CharacterListOptionBarUiState.Loading)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = CharacterListOptionBarUiState.Loading,
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    val presetsUiState: StateFlow<PresetListUiState> = characterListOptionBarUiState
        .mapNotNull {
            when (it) {
                CharacterListOptionBarUiState.Loading -> null
                is CharacterListOptionBarUiState.Success -> it.characterListPreferencesData
            }
        }
        .flatMapLatest { presetListPreferencesData ->
            getPresetWithDetailsListUseCase(
                filteredRarities = presetListPreferencesData.filteredRarities,
                filteredPathIds = presetListPreferencesData.filteredPathIds,
                filteredElementIds = presetListPreferencesData.filteredElementIds,
                sortField = presetListPreferencesData.sortField,
            )
        }
        .filterNot { it.isEmpty() }
        .map<List<PresetWithDetails>, PresetListUiState>(PresetListUiState::Success)
        .onStart { emit(PresetListUiState.Loading) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = PresetListUiState.Loading,
        )

    fun setPresetAutoUpdate(
        characterId: String,
        isAutoUpdate: Boolean,
    ) {
        viewModelScope.launch {
            updatePresetAutoUpdateUseCase(setOf(characterId), isAutoUpdate)
        }
    }

    fun setPresetsSortField(newSortField: CharacterSortField) {
        viewModelScope.launch {
            when (val uiState = characterListOptionBarUiState.value) {
                CharacterListOptionBarUiState.Loading -> Unit
                is CharacterListOptionBarUiState.Success -> {
                    with(uiState.characterListPreferencesData) {
                        setPresetListPreferencesDataUseCase(
                            filteredRarities = filteredRarities,
                            filteredPathIds = filteredPathIds,
                            filteredElementIds = filteredElementIds,
                            sortField = newSortField,
                        )
                    }
                }
            }
        }
    }

    fun setPresetsFilters(
        selectedRarities: Set<Int>,
        selectedPathIds: Set<String>,
        selectedElementIds: Set<String>,
    ) {
        viewModelScope.launch {
            when (val uiState = characterListOptionBarUiState.value) {
                CharacterListOptionBarUiState.Loading -> Unit
                is CharacterListOptionBarUiState.Success -> {
                    with(uiState.characterListPreferencesData) {
                        setPresetListPreferencesDataUseCase(
                            filteredRarities = selectedRarities,
                            filteredPathIds = selectedPathIds,
                            filteredElementIds = selectedElementIds,
                            sortField = sortField,
                        )
                    }
                }
            }
        }
    }
}
