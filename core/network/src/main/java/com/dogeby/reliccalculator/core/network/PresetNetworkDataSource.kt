package com.dogeby.reliccalculator.core.network

import com.dogeby.reliccalculator.core.model.preset.PresetData

interface PresetNetworkDataSource {

    suspend fun getDefaultPreset(): Result<PresetData>
}
