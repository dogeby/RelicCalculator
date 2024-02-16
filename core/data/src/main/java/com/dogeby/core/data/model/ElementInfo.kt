package com.dogeby.core.data.model

import com.dogeby.core.database.model.hoyo.index.ElementInfoEntity
import com.dogeby.reliccalculator.core.model.hoyo.index.ElementInfo

fun ElementInfo.toElementInfoEntity() = ElementInfoEntity(
    id = id,
    name = name,
    color = color,
    icon = icon,
)
