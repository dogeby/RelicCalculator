package com.dogeby.reliccalculator.core.data.model

import com.dogeby.reliccalculator.core.database.model.hoyo.index.ElementInfoEntity
import com.dogeby.reliccalculator.core.model.mihomo.index.ElementInfo

fun ElementInfo.toElementInfoEntity() = ElementInfoEntity(
    id = id,
    name = name,
    color = color,
    icon = icon,
)
