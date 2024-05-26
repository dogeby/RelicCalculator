package com.dogeby.reliccalculator.core.model.user

import com.dogeby.reliccalculator.core.model.mihomo.Player

data class UserProfile(
    val player: Player,
    val characterIds: List<String>,
)
