package com.dogeby.core.data.model

import com.dogeby.core.database.model.hoyo.index.PathInfoEntity
import com.dogeby.reliccalculator.core.model.hoyo.index.PathInfo

fun PathInfo.toPathInfoEntity() = PathInfoEntity(
    id = id,
    name = name,
    icon = icon,
)
