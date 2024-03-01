package com.dogeby.reliccalculator.core.network

import com.dogeby.reliccalculator.core.model.mihomo.Profile

interface ProfileNetworkDataSource {

    suspend fun getProfile(
        uid: String,
        language: String,
    ): Result<Profile>
}
