package com.dogeby.reliccalculator.feature.presetedit.navigation

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.dogeby.reliccalculator.core.common.decoder.StringDecoder

const val PRESET_EDIT_ROUTE = "presetEdit"
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
