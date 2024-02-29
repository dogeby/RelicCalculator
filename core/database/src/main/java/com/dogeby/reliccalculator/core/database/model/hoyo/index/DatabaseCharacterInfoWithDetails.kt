package com.dogeby.reliccalculator.core.database.model.hoyo.index

import androidx.room.Embedded
import androidx.room.Relation
import com.dogeby.reliccalculator.core.model.mihomo.index.CharacterInfoWithDetails

data class DatabaseCharacterInfoWithDetails(
    @Embedded val characterInfo: CharacterInfoEntity,
    @Relation(
        parentColumn = "path",
        entityColumn = "id",
    )
    val pathInfo: PathInfoEntity,
    @Relation(
        parentColumn = "element",
        entityColumn = "id",
    )
    val elementInfo: ElementInfoEntity,
)

fun DatabaseCharacterInfoWithDetails.toCharacterInfoWithDetails() = CharacterInfoWithDetails(
    characterInfo = characterInfo.toCharacterInfo(),
    pathInfo = pathInfo.toPathInfo(),
    elementInfo = elementInfo.toElementInfo(),
)
