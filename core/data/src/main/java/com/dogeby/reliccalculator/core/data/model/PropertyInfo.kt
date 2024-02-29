package com.dogeby.reliccalculator.core.data.model

import com.dogeby.reliccalculator.core.database.model.hoyo.index.PropertyInfoEntity
import com.dogeby.reliccalculator.core.model.mihomo.index.PropertyInfo

fun PropertyInfo.toPropertyInfoEntity() = PropertyInfoEntity(
    type = type,
    name = name,
    field = field,
    affix = affix,
    ratio = ratio,
    percent = percent,
    order = order,
    icon = icon,
)
