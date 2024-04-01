package com.dogeby.reliccalculator.feature.presetedit.navigation

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavOptions

const val PRESET_EDIT_ROUTE = "presetEdit"
internal const val PRESET_ID_ARG = "presetId"

internal class PresetEditArgs(val presetId: String) {

    constructor(savedStateHandle: SavedStateHandle) :
        this(Uri.decode(checkNotNull(savedStateHandle[PRESET_ID_ARG])))
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
