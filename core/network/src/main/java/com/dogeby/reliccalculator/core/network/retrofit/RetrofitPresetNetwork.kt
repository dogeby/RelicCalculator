package com.dogeby.reliccalculator.core.network.retrofit

import com.dogeby.reliccalculator.core.network.BuildConfig
import com.dogeby.reliccalculator.core.network.PresetNetworkDataSource
import com.dogeby.reliccalculator.core.network.model.preset.NetworkPresetData
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody
import retrofit2.Response
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

    override suspend fun getDefaultPreset(): Result<NetworkPresetData> {
        return runCatching {
            networkApi.getDefaultPreset()
        }
    }

    override suspend fun getDefaultPresetJson(): Result<String> {
        return runCatching {
            networkApi
                .getDefaultPresetResponseBody()
                .body()
                ?.byteStream()
                ?.use {
                    it.readBytes().decodeToString()
                } ?: throw NullPointerException()
        }
    }
}

private interface RetrofitPresetNetworkApi {

    @GET("star_rail_default_preset.json")
    suspend fun getDefaultPreset(): NetworkPresetData

    @GET("star_rail_default_preset.json")
    suspend fun getDefaultPresetResponseBody(): Response<ResponseBody>
}
