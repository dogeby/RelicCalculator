package com.dogeby.reliccalculator.core.data.model

import com.dogeby.reliccalculator.core.database.model.hoyo.index.RelicSetInfoEntity
import com.dogeby.reliccalculator.core.model.mihomo.index.RelicSetInfo

fun RelicSetInfo.toRelicSetInfoEntity() = RelicSetInfoEntity(
    id = id,
    name = name,
    desc = desc,
    icon = icon,
)
