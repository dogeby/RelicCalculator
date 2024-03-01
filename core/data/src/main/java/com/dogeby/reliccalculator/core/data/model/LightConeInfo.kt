package com.dogeby.reliccalculator.core.data.model

import com.dogeby.reliccalculator.core.database.model.hoyo.index.LightConeInfoEntity
import com.dogeby.reliccalculator.core.model.mihomo.index.LightConeInfo

fun LightConeInfo.toLightConeInfoEntity() = LightConeInfoEntity(
    id = id,
    name = name,
    rarity = rarity,
    path = path,
    portrait = portrait,
)
