package com.dogeby.reliccalculator.core.data.model

import com.dogeby.reliccalculator.core.database.model.hoyo.DatabasePlayer
import com.dogeby.reliccalculator.core.database.model.hoyo.DatabaseSpaceInfo
import com.dogeby.reliccalculator.core.database.model.user.UserProfileEntity
import com.dogeby.reliccalculator.core.model.user.UserProfile

fun UserProfile.toUserProfileEntity() = UserProfileEntity(
    id = id,
    player = DatabasePlayer(
        uid = player.uid,
        nickname = player.nickname,
        spaceInfo = DatabaseSpaceInfo(player.spaceInfo.achievementCount),
    ),
    characterIds = characterIds,
)
