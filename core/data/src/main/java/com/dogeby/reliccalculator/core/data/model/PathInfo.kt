package com.dogeby.reliccalculator.core.data.model

import com.dogeby.reliccalculator.core.database.model.hoyo.index.PathInfoEntity
import com.dogeby.reliccalculator.core.model.mihomo.index.PathInfo

fun PathInfo.toPathInfoEntity() = PathInfoEntity(
    id = id,
    name = name,
    icon = icon,
)
