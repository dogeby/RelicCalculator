package com.dogeby.reliccalculator.feature.presetedit.navigation

import android.net.Uri
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.dogeby.reliccalculator.core.common.decoder.StringDecoder
import com.dogeby.reliccalculator.feature.presetedit.PresetEditRoute

const val PRESET_EDIT_ROUTE = "preset_edit_route"
internal const val PRESET_ID_ARG = "presetId"

internal class PresetEditArgs(val presetId: String) {

    constructor(savedStateHandle: SavedStateHandle, stringDecoder: StringDecoder) :
        this(stringDecoder.decodeString(checkNotNull(savedStateHandle[PRESET_ID_ARG])))
}

fun NavController.navigateToPresetEdit(
    presetId: String,
    navOptions: NavOptions? = null,
) {
    val encodedId = Uri.encode(presetId)
    navigate(
        route = "$PRESET_EDIT_ROUTE/$encodedId",
        navOptions = navOptions,
    )
}

fun NavGraphBuilder.presetEditScreen(
    snackbarHostState: SnackbarHostState,
    onNavigateUp: () -> Unit,
) {
    composable(
        route = "$PRESET_EDIT_ROUTE/{$PRESET_ID_ARG}",
        arguments = listOf(
            navArgument(PRESET_ID_ARG) { type = NavType.StringType },
        ),
    ) {
        PresetEditRoute(
            snackbarHostState = snackbarHostState,
            onNavigateUp = onNavigateUp,
        )
    }
}
