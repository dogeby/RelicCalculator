package com.dogeby.reliccalculator.core.network.retrofit

import com.dogeby.reliccalculator.core.model.mihomo.Profile
import com.dogeby.reliccalculator.core.network.BuildConfig
import com.dogeby.reliccalculator.core.network.ProfileNetworkDataSource
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val PROFILE_URL = BuildConfig.PROFILE_URL

@Singleton
class RetrofitProfileNetwork @Inject constructor(
    networkJson: Json,
) : ProfileNetworkDataSource {

    private val networkApi =
        Retrofit.Builder()
            .baseUrl(PROFILE_URL)
            .addConverterFactory(
                networkJson.asConverterFactory("application/json".toMediaType()),
            )
            .build()
            .create(RetrofitProfileNetworkApi::class.java)

    override suspend fun getProfile(
        uid: String,
        language: String,
    ): Result<Profile> {
        return runCatching {
            networkApi.getProfile(uid, language)
        }
    }
}

private interface RetrofitProfileNetworkApi {

    @GET(value = "{uid}")
    suspend fun getProfile(
        @Path("uid") uid: String,
        @Query("lang") language: String,
    ): Profile
}
