package com.dogeby.reliccalculator.feature.charsimplereports

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dogeby.reliccalculator.core.model.preferences.CharacterListPreferencesData
import com.dogeby.reliccalculator.core.model.preferences.CharacterSortField
import com.dogeby.reliccalculator.core.ui.component.HideableBarLazyVerticalGrid
import com.dogeby.reliccalculator.core.ui.component.LoadingAssistChip
import com.dogeby.reliccalculator.core.ui.component.LoadingState
import com.dogeby.reliccalculator.core.ui.component.character.CharSimpleReportCardListUiState
import com.dogeby.reliccalculator.core.ui.component.character.CharSimpleReportCardUiState
import com.dogeby.reliccalculator.core.ui.component.character.characterSimpleReportCardList
import com.dogeby.reliccalculator.core.ui.component.preset.CharacterListOptionBar
import com.dogeby.reliccalculator.core.ui.component.preset.CharacterListOptionBarUiState
import com.dogeby.reliccalculator.core.ui.component.relic.CharRelicRatingListUiState
import com.dogeby.reliccalculator.core.ui.component.relic.RelicRatingUiState
import com.dogeby.reliccalculator.core.ui.theme.RelicCalculatorTheme
import com.dogeby.reliccalculator.feature.charsimplereports.model.CharSimpleReportsMessageUiState
import kotlinx.datetime.Clock

@Composable
fun CharSimpleReportsRoute(
    snackbarHostState: SnackbarHostState,
    navigateToCharacterReport: (reportId: Int, characterId: String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CharSimpleReportsViewModel = hiltViewModel(),
) {
    val characterListOptionBarUiState by viewModel
        .characterListOptionBarUiState
        .collectAsStateWithLifecycle()
    val charSimpleReportCardListUiState by viewModel
        .charSimpleReportCardListUiState
        .collectAsStateWithLifecycle()
    val isCharReportsRefreshing by viewModel
        .isCharReportsRefreshing
        .collectAsStateWithLifecycle()

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.snackbarMessage.collect {
            val message = when (it) {
                is CharSimpleReportsMessageUiState.ReportsRefreshSuccess -> {
                    context.getString(R.string.reports_refreshed, it.count)
                }
                CharSimpleReportsMessageUiState.ReportsRefreshEmpty -> {
                    context.getString(R.string.reports_refresh_empty)
                }
                CharSimpleReportsMessageUiState.ReportsRefreshFailed -> {
                    context.getString(R.string.reports_refresh_failed)
                }
            }
            snackbarHostState.showSnackbar(message)
        }
    }

    CharSimpleReportsScreen(
        characterListOptionBarUiState = characterListOptionBarUiState,
        charSimpleReportCardListUiState = charSimpleReportCardListUiState,
        isCharReportsRefreshing = { isCharReportsRefreshing },
        onSetSortField = viewModel::setCharactersSortField,
        onConfirmFilters = viewModel::setCharactersFilters,
        onRefreshCharacterReports = viewModel::refreshCharacterReports,
        onReportCardClick = navigateToCharacterReport,
        modifier = modifier,
    )
}

@Composable
private fun CharSimpleReportsScreen(
    characterListOptionBarUiState: CharacterListOptionBarUiState,
    charSimpleReportCardListUiState: CharSimpleReportCardListUiState,
    isCharReportsRefreshing: () -> Boolean,
    onSetSortField: (CharacterSortField) -> Unit,
    onConfirmFilters: (
        selectedRarities: Set<Int>,
        selectedPathIds: Set<String>,
        selectedElementIds: Set<String>,
    ) -> Unit,
    onRefreshCharacterReports: () -> Unit,
    onReportCardClick: (reportId: Int, characterId: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (charSimpleReportCardListUiState) {
        CharSimpleReportCardListUiState.Loading -> LoadingState()
        is CharSimpleReportCardListUiState.Success ->
            if (charSimpleReportCardListUiState.characterSimpleReportCards.isNotEmpty()) {
                HideableBarLazyVerticalGrid(
                    topBar = {
                        Surface {
                            Row(
                                modifier = modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 8.dp)
                                    .horizontalScroll(rememberScrollState()),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                            ) {
                                CharacterListOptionBar(
                                    characterListOptionBarUiState = characterListOptionBarUiState,
                                    onSetSortField = onSetSortField,
                                    onConfirmFilters = onConfirmFilters,
                                )
                                LoadingAssistChip(
                                    isLoading = isCharReportsRefreshing,
                                    text = stringResource(id = R.string.refresh),
                                    onClick = onRefreshCharacterReports,
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Refresh,
                                        contentDescription = null,
                                        modifier = Modifier.size(AssistChipDefaults.IconSize),
                                    )
                                }
                            }
                        }
                    },
                    topBarHeight = 48.dp,
                    bottomBar = {},
                    bottomBarHeight = 0.dp,
                    columns = GridCells.Adaptive(308.dp),
                    modifier = modifier,
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    characterSimpleReportCardList(
                        charSimpleReportCardListUiState = charSimpleReportCardListUiState,
                        onCardClick = onReportCardClick,
                    )
                }
            }
    }
}

@Preview(apiLevel = 33)
@Composable
private fun PreviewCharSimpleReportsScreen() {
    RelicCalculatorTheme {
        CharSimpleReportsScreen(
            characterListOptionBarUiState = CharacterListOptionBarUiState.Success(
                pathInfoList = emptyList(),
                elementInfoList = emptyList(),
                CharacterListPreferencesData(
                    filteredRarities = emptySet(),
                    filteredPathIds = emptySet(),
                    filteredElementIds = emptySet(),
                    sortField = CharacterSortField.ID_DESC,
                ),
            ),
            charSimpleReportCardListUiState = CharSimpleReportCardListUiState.Success(
                characterSimpleReportCards = List(3) {
                    CharSimpleReportCardUiState(
                        id = it,
                        characterId = "1107",
                        characterName = "name",
                        characterIcon = "icon/character/1107.png",
                        updatedDate = Clock.System.now(),
                        score = 5.0f,
                        charRelicRatingListUiState = CharRelicRatingListUiState.Success(
                            cavernRelics = List(4) {
                                RelicRatingUiState(
                                    id = "cavernRelics$it",
                                    icon = "icon/relic/311_1.png",
                                    score = 4.5f,
                                )
                            },
                            planarOrnaments = List(2) {
                                RelicRatingUiState(
                                    id = "planarOrnaments$it",
                                    icon = "icon/relic/311_1.png",
                                    score = 2f,
                                )
                            },
                        ),
                    )
                },
            ),
            isCharReportsRefreshing = { false },
            onSetSortField = {},
            onConfirmFilters = { _, _, _ -> },
            onRefreshCharacterReports = {},
            onReportCardClick = { _, _ -> },
        )
    }
}
