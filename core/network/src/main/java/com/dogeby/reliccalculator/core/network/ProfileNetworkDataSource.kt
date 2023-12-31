package com.dogeby.reliccalculator.core.network

import com.dogeby.reliccalculator.core.network.model.hoyo.NetworkProfile

interface ProfileNetworkDataSource {

    suspend fun getProfile(
        uid: String,
        language: String,
    ): Result<NetworkProfile>
}
