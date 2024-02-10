package com.dogeby.reliccalculator.core.network

import com.dogeby.reliccalculator.core.model.data.preset.PresetData

interface PresetNetworkDataSource {

    suspend fun getDefaultPreset(): Result<PresetData>
}
