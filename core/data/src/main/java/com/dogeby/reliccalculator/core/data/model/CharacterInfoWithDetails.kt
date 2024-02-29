package com.dogeby.reliccalculator.core.data.model

import com.dogeby.reliccalculator.core.database.model.hoyo.index.DatabaseCharacterInfoWithDetails
import com.dogeby.reliccalculator.core.model.hoyo.index.CharacterInfoWithDetails

fun CharacterInfoWithDetails.toDatabaseCharacterInfoWithDetails() =
    DatabaseCharacterInfoWithDetails(
        characterInfo = characterInfo.toCharacterInfoEntity(),
        pathInfo = pathInfo.toPathInfoEntity(),
        elementInfo = elementInfo.toElementInfoEntity(),
    )
