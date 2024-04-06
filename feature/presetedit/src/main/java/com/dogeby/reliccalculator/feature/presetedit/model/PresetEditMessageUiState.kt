package com.dogeby.reliccalculator.feature.presetedit.model

sealed interface PresetEditMessageUiState {

    data object EditError : PresetEditMessageUiState

    data object EditDuplicate : PresetEditMessageUiState

    data object EditSuccess : PresetEditMessageUiState

    data object AddError : PresetEditMessageUiState

    data object DeleteError : PresetEditMessageUiState

    data object ModifyError : PresetEditMessageUiState
}
