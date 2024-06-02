package com.dogeby.reliccalculator.feature.charsimplereports.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.dogeby.reliccalculator.feature.charsimplereports.CharSimpleReportsRoute

const val CHAR_SIMPLE_REPORTS_ROUTE = "char_simple_reports_route"

fun NavController.navigateToCharSimpleReports(navOptions: NavOptions? = null) {
    navigate(CHAR_SIMPLE_REPORTS_ROUTE, navOptions)
}

fun NavGraphBuilder.charSimpleReportsScreen(
    snackbarHostState: SnackbarHostState,
    navigateToCharacterReport: (reportId: Int, characterId: String) -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit,
) {
    composable(CHAR_SIMPLE_REPORTS_ROUTE) {
        CharSimpleReportsRoute(
            snackbarHostState = snackbarHostState,
            navigateToCharacterReport = navigateToCharacterReport,
        )
    }
    nestedGraphs()
}
