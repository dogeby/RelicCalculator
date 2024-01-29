package com.dogeby.reliccalculator.core.network

import com.dogeby.reliccalculator.core.network.model.preset.NetworkPresetData

interface PresetNetworkDataSource {

    suspend fun getDefaultPreset(): Result<NetworkPresetData>
}
