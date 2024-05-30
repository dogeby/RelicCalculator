package com.dogeby.reliccalculator.core.data.repository

import com.dogeby.reliccalculator.core.model.GameTextLanguage
import com.dogeby.reliccalculator.core.model.user.UserProfile
import kotlinx.coroutines.flow.Flow

interface UserProfileRepository {

    fun getAllUserProfiles(): Flow<List<UserProfile>>

    fun getUserProfiles(ids: Set<String>): Flow<List<UserProfile>>

    suspend fun fetchUserProfile(
        uid: String,
        language: GameTextLanguage,
    ): Result<UserProfile>

    suspend fun insertUserProfiles(profiles: List<UserProfile>): Result<List<Long>>

    suspend fun updateUserProfiles(profiles: List<UserProfile>): Result<Int>
}
