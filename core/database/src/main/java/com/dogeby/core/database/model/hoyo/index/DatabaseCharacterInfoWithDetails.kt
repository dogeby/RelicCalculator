package com.dogeby.core.database.model.hoyo.index

import androidx.room.Embedded
import androidx.room.Relation
import com.dogeby.reliccalculator.core.model.hoyo.index.CharacterInfoWithDetails

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
