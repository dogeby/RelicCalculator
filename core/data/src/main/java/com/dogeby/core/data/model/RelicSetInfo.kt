package com.dogeby.core.data.model

import com.dogeby.core.database.model.hoyo.index.RelicSetInfoEntity
import com.dogeby.reliccalculator.core.model.hoyo.index.RelicSetInfo

fun RelicSetInfo.toRelicSetInfoEntity() = RelicSetInfoEntity(
    id = id,
    name = name,
    desc = desc,
    icon = icon,
)
