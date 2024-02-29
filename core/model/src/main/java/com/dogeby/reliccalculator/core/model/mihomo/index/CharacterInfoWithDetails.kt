package com.dogeby.reliccalculator.core.model.mihomo.index

import org.jetbrains.annotations.TestOnly

data class CharacterInfoWithDetails(
    val characterInfo: CharacterInfo,
    val pathInfo: PathInfo,
    val elementInfo: ElementInfo,
)

@TestOnly
val sampleCharacterInfoWithDetails = CharacterInfoWithDetails(
    characterInfo = sampleCharacterInfo,
    pathInfo = samplePathInfo,
    elementInfo = sampleElementInfo,
)
