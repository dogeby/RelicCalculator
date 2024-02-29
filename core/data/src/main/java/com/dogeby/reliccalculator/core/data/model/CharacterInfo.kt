package com.dogeby.reliccalculator.core.data.model

import com.dogeby.reliccalculator.core.database.model.hoyo.index.CharacterInfoEntity
import com.dogeby.reliccalculator.core.model.hoyo.index.CharacterInfo

fun CharacterInfo.toCharacterInfoEntity() = CharacterInfoEntity(
    id = id,
    name = name,
    tag = tag,
    rarity = rarity,
    path = path,
    element = element,
    maxSp = maxSp,
    icon = icon,
    preview = preview,
    portrait = portrait,
)
