package com.dogeby.reliccalculator.core.data.repository

import com.dogeby.reliccalculator.core.data.model.toUserProfile
import com.dogeby.reliccalculator.core.data.model.toUserProfileEntity
import com.dogeby.reliccalculator.core.database.dao.UserProfileDao
import com.dogeby.reliccalculator.core.database.model.user.UserProfileEntity
import com.dogeby.reliccalculator.core.database.model.user.toUserProfile
import com.dogeby.reliccalculator.core.model.GameTextLanguage
import com.dogeby.reliccalculator.core.model.mihomo.Profile
import com.dogeby.reliccalculator.core.model.user.UserProfile
import com.dogeby.reliccalculator.core.network.ProfileNetworkDataSource
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Singleton
class UserProfileRepositoryImpl @Inject constructor(
    private val userProfileDao: UserProfileDao,
    private val profileNetworkDataSource: ProfileNetworkDataSource,
) : UserProfileRepository {

    override fun getAllUserProfiles(): Flow<List<UserProfile>> = userProfileDao
        .getUserProfiles()
        .map {
            it.map(UserProfileEntity::toUserProfile)
        }

    override fun getUserProfiles(ids: Set<String>): Flow<List<UserProfile>> = userProfileDao
        .getUserProfiles(ids)
        .map {
            it.map(UserProfileEntity::toUserProfile)
        }

    override suspend fun fetchProfile(
        uid: String,
        language: GameTextLanguage,
    ): Result<Profile> {
        return profileNetworkDataSource.getProfile(uid, language.code)
    }

    override suspend fun insertUserProfiles(profiles: List<Profile>): Result<List<Long>> =
        runCatching {
            userProfileDao.insertOrIgnoreUserProfiles(
                profiles
                    .map(Profile::toUserProfile)
                    .map(UserProfile::toUserProfileEntity),
            )
        }

    override suspend fun updateUserProfiles(profiles: List<Profile>): Result<Int> = runCatching {
        userProfileDao.updateUserProfiles(
            profiles
                .map(Profile::toUserProfile)
                .map(UserProfile::toUserProfileEntity),
        )
    }
}
