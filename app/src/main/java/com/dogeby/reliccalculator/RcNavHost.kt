package com.dogeby.reliccalculator

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.dogeby.reliccalculator.feature.presets.navigation.PRESETS_ROUTE
import com.dogeby.reliccalculator.feature.presets.navigation.presetsScreen

@Composable
fun RcNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = PRESETS_ROUTE,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        presetsScreen { }
    }
}
