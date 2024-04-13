package com.dogeby.reliccalculator.feature.presets.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.dogeby.reliccalculator.feature.presets.PresetsRoute

const val PRESETS_ROUTE = "presets_route"

fun NavController.navigateToPresets(navOptions: NavOptions? = null) {
    navigate(PRESETS_ROUTE, navOptions)
}

fun NavGraphBuilder.presetsScreen(
    navigateToPresetEdit: (String) -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit,
) {
    composable(route = PRESETS_ROUTE) {
        PresetsRoute(
            navigateToPresetEdit = navigateToPresetEdit,
        )
    }
    nestedGraphs()
}
