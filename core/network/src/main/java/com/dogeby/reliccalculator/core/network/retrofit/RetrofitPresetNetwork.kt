package com.dogeby.reliccalculator.core.network.retrofit

import com.dogeby.reliccalculator.core.model.data.preset.PresetData
import com.dogeby.reliccalculator.core.network.BuildConfig
import com.dogeby.reliccalculator.core.network.PresetNetworkDataSource
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET

private const val DEFAULT_PRESET_URL = BuildConfig.DEFAULT_PRESET_URL

@Singleton
class RetrofitPresetNetwork @Inject constructor(
    networkJson: Json,
) : PresetNetworkDataSource {

    private val networkApi =
        Retrofit.Builder()
            .baseUrl(DEFAULT_PRESET_URL)
            .addConverterFactory(
                networkJson.asConverterFactory("application/json".toMediaType()),
            )
            .build()
            .create(RetrofitPresetNetworkApi::class.java)

    override suspend fun getDefaultPreset(): Result<PresetData> {
        return runCatching {
            networkApi.getDefaultPreset()
        }
    }
}

private interface RetrofitPresetNetworkApi {

    @GET("star_rail_default_preset.json")
    suspend fun getDefaultPreset(): PresetData
}
