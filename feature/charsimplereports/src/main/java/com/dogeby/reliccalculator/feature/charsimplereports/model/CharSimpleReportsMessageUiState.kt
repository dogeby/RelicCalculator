package com.dogeby.reliccalculator.feature.charsimplereports.model

sealed interface CharSimpleReportsMessageUiState {

    data class ReportsRefreshSuccess(
        val count: Int,
    ) : CharSimpleReportsMessageUiState

    data object ReportsRefreshEmpty : CharSimpleReportsMessageUiState

    data object ReportsRefreshFailed : CharSimpleReportsMessageUiState
}
