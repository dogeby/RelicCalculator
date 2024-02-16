package com.dogeby.core.data.model

import com.dogeby.core.database.model.hoyo.index.LightConeInfoEntity
import com.dogeby.reliccalculator.core.model.hoyo.index.LightConeInfo

fun LightConeInfo.toLightConeInfoEntity() = LightConeInfoEntity(
    id = id,
    name = name,
    rarity = rarity,
    path = path,
    portrait = portrait,
)
