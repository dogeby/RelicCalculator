package com.dogeby.reliccalculator.core.data.model

import com.dogeby.reliccalculator.core.model.mihomo.Profile
import com.dogeby.reliccalculator.core.model.user.UserProfile

fun Profile.toUserProfile() = UserProfile(
    id = player.uid,
    player = player,
    characterIds = characters.map { it.id },
)
