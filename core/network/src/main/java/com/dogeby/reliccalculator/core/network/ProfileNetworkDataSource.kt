package com.dogeby.reliccalculator.core.network

import com.dogeby.reliccalculator.core.model.hoyo.Profile

interface ProfileNetworkDataSource {

    suspend fun getProfile(
        uid: String,
        language: String,
    ): Result<Profile>
}
