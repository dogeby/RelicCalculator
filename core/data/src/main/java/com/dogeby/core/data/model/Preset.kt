package com.dogeby.core.data.model

import com.dogeby.core.database.model.preset.PresetEntity
import com.dogeby.reliccalculator.core.model.data.preset.AffixWeight
import com.dogeby.reliccalculator.core.model.data.preset.Preset

fun Preset.toPresetEntity() = PresetEntity(
    characterId = characterId,
    relicSetIds = relicSetIds,
    pieceMainAffixWeights = pieceMainAffixWeights.mapValues {
        it.value.map(AffixWeight::toDatabaseAffixWeight)
    },
    subAffixWeights = subAffixWeights.map(AffixWeight::toDatabaseAffixWeight),
    isAutoUpdate = isAutoUpdate,
)
