package com.dogeby.reliccalculator.feature.charsimplereports

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dogeby.reliccalculator.core.domain.index.GetElementInfoMapUseCase
import com.dogeby.reliccalculator.core.domain.index.GetPathInfoMapUseCase
import com.dogeby.reliccalculator.core.domain.model.CharacterSimpleReport
import com.dogeby.reliccalculator.core.domain.preference.GetCharSimpleReportListPrefsDataUseCase
import com.dogeby.reliccalculator.core.domain.preference.SetCharSimpleReportListPrefsDataUseCase
import com.dogeby.reliccalculator.core.domain.report.GetCharSimpleReportsUseCase
import com.dogeby.reliccalculator.core.domain.report.RefreshCharReportsUseCase
import com.dogeby.reliccalculator.core.model.preferences.CharacterSortField
import com.dogeby.reliccalculator.core.ui.component.character.CharSimpleReportCardListUiState
import com.dogeby.reliccalculator.core.ui.component.character.CharSimpleReportCardUiState
import com.dogeby.reliccalculator.core.ui.component.preset.CharacterListOptionBarUiState
import com.dogeby.reliccalculator.core.ui.component.relic.CharRelicRatingListUiState
import com.dogeby.reliccalculator.core.ui.component.relic.RelicRatingUiState
import com.dogeby.reliccalculator.feature.charsimplereports.model.CharSimpleReportsMessageUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
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
class CharSimpleReportsViewModel @Inject constructor(
    private val setCharSimpleReportListPrefsDataUseCase: SetCharSimpleReportListPrefsDataUseCase,
    private val refreshCharReportsUseCase: RefreshCharReportsUseCase,
    getCharSimpleReportListPrefsDataUseCase: GetCharSimpleReportListPrefsDataUseCase,
    getCharSimpleReportsUseCase: GetCharSimpleReportsUseCase,
    getPathInfoMapUseCase: GetPathInfoMapUseCase,
    getElementInfoMapUseCase: GetElementInfoMapUseCase,
) : ViewModel() {

    val characterListOptionBarUiState: StateFlow<CharacterListOptionBarUiState> = combine(
        getPathInfoMapUseCase(),
        getElementInfoMapUseCase(),
        getCharSimpleReportListPrefsDataUseCase(),
    ) { pathInfoList, elementInfoList, charSimpleReportListPrefsData ->
        CharacterListOptionBarUiState.Success(
            pathInfoList = pathInfoList.values.toList(),
            elementInfoList = elementInfoList.values.toList(),
            characterListPreferencesData = charSimpleReportListPrefsData,
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
    val charSimpleReportCardListUiState: StateFlow<CharSimpleReportCardListUiState> =
        characterListOptionBarUiState.mapNotNull {
            when (it) {
                CharacterListOptionBarUiState.Loading -> null
                is CharacterListOptionBarUiState.Success -> it.characterListPreferencesData
            }
        }
            .flatMapLatest { charListPrefersData ->
                getCharSimpleReportsUseCase(
                    filteredRarities = charListPrefersData.filteredRarities,
                    filteredPathIds = charListPrefersData.filteredPathIds,
                    filteredElementIds = charListPrefersData.filteredElementIds,
                    sortField = charListPrefersData.sortField,
                )
            }
            .filterNot {
                it.isEmpty()
            }
            .map<List<CharacterSimpleReport>, CharSimpleReportCardListUiState>
            { charSimpleReports ->
                CharSimpleReportCardListUiState.Success(
                    characterSimpleReportCards = charSimpleReports.map { charSimpleReport ->
                        val charRelicRatingListUiState = CharRelicRatingListUiState.Success(
                            cavernRelics = charSimpleReport.cavernRelics.map {
                                RelicRatingUiState(
                                    id = it.id,
                                    icon = it.icon,
                                    score = it.score,
                                )
                            },
                            planarOrnaments = charSimpleReport.planarOrnaments.map {
                                RelicRatingUiState(
                                    id = it.id,
                                    icon = it.icon,
                                    score = it.score,
                                )
                            },
                        )
                        CharSimpleReportCardUiState(
                            id = charSimpleReport.id,
                            characterId = charSimpleReport.characterId,
                            characterName = charSimpleReport.name,
                            characterIcon = charSimpleReport.icon,
                            updatedDate = charSimpleReport.updatedDate,
                            score = charSimpleReport.totalScore,
                            charRelicRatingListUiState = charRelicRatingListUiState,
                        )
                    },
                )
            }
            .onStart { emit(CharSimpleReportCardListUiState.Loading) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = CharSimpleReportCardListUiState.Loading,
            )

    private val _isCharReportsRefreshing = MutableStateFlow(false)
    val isCharReportsRefreshing: StateFlow<Boolean> = _isCharReportsRefreshing

    private val _snackbarMessage = MutableSharedFlow<CharSimpleReportsMessageUiState>()
    val snackbarMessage: SharedFlow<CharSimpleReportsMessageUiState> = _snackbarMessage

    fun setCharactersSortField(newSortField: CharacterSortField) {
        viewModelScope.launch {
            when (val uiState = characterListOptionBarUiState.value) {
                CharacterListOptionBarUiState.Loading -> Unit
                is CharacterListOptionBarUiState.Success -> {
                    with(uiState.characterListPreferencesData) {
                        setCharSimpleReportListPrefsDataUseCase(
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

    fun setCharactersFilters(
        selectedRarities: Set<Int>,
        selectedPathIds: Set<String>,
        selectedElementIds: Set<String>,
    ) {
        viewModelScope.launch {
            when (val uiState = characterListOptionBarUiState.value) {
                CharacterListOptionBarUiState.Loading -> Unit
                is CharacterListOptionBarUiState.Success -> {
                    with(uiState.characterListPreferencesData) {
                        setCharSimpleReportListPrefsDataUseCase(
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

    fun refreshCharacterReports() {
        if (isCharReportsRefreshing.value) return
        viewModelScope.launch {
            _isCharReportsRefreshing.emit(true)

            refreshCharReportsUseCase()
                .onSuccess {
                    _snackbarMessage.emit(
                        if (it == 0) {
                            CharSimpleReportsMessageUiState.ReportsRefreshEmpty
                        } else {
                            CharSimpleReportsMessageUiState.ReportsRefreshSuccess(it)
                        },
                    )
                }
                .onFailure {
                    _snackbarMessage.emit(CharSimpleReportsMessageUiState.ReportsRefreshFailed)
                }

            _isCharReportsRefreshing.emit(false)
        }
    }
}
