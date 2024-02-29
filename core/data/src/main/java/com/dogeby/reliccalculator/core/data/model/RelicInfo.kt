package com.dogeby.reliccalculator.core.data.model

import com.dogeby.reliccalculator.core.database.model.hoyo.index.RelicInfoEntity
import com.dogeby.reliccalculator.core.model.hoyo.index.RelicInfo

fun RelicInfo.toRelicInfoEntity() = RelicInfoEntity(
    id = id,
    setId = setId,
    name = name,
    rarity = rarity,
    type = type,
    maxLevel = maxLevel,
    mainAffixId = mainAffixId,
    subAffixId = subAffixId,
    icon = icon,
)
