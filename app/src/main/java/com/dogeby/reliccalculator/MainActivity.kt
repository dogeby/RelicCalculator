package com.dogeby.reliccalculator

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dogeby.reliccalculator.core.domain.index.UpdateGameInfoInDbUseCase
import com.dogeby.reliccalculator.core.domain.preset.UpdateDefaultPresetsUseCase
import com.dogeby.reliccalculator.ui.theme.RelicCalculatorTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            viewModel
            RelicCalculatorTheme {
                RcApp()
            }
        }
    }
}

@HiltViewModel
class MainViewModel @Inject constructor(
    updateDefaultPresetsUseCase: UpdateDefaultPresetsUseCase,
    updateGameInfoInDbUseCase: UpdateGameInfoInDbUseCase,
) : ViewModel() {

    init {
        viewModelScope.launch {
            val presetUpdateResult = updateDefaultPresetsUseCase()
            val infoUpdateResult = updateGameInfoInDbUseCase()
            Log.d("test", "$presetUpdateResult, $infoUpdateResult")
        }
    }
}
