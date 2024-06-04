package com.dogeby.reliccalculator.core.database.model.hoyo

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.dogeby.reliccalculator.core.database.util.AttributeListConverter
import com.dogeby.reliccalculator.core.database.util.RelicListConverter
import com.dogeby.reliccalculator.core.database.util.RelicSetListConverter
import com.dogeby.reliccalculator.core.model.mihomo.Attribute
import com.dogeby.reliccalculator.core.model.mihomo.Relic
import com.dogeby.reliccalculator.core.model.mihomo.RelicSet
import org.jetbrains.annotations.TestOnly

@Entity(tableName = "characters")
@TypeConverters(
    RelicListConverter::class,
    RelicSetListConverter::class,
    AttributeListConverter::class,
)
data class CharacterEntity(
    @PrimaryKey
    @ColumnInfo(name = "character_id")
    val id: String,
    @Embedded val lightCone: DatabaseLightCone,
    @ColumnInfo(name = "character_relics") val relics: List<Relic>,
    @ColumnInfo(name = "character_relic_sets") val relicSets: List<RelicSet>,
    @ColumnInfo(name = "character_attributes") val attributes: List<Attribute>,
    @ColumnInfo(name = "character_additions") val additions: List<Attribute>,
)

@TestOnly
val sampleCharacterEntity = CharacterEntity(
    id = "1212",
    lightCone = DatabaseLightCone(
        id = "24000",
        rarity = 5,
        rank = 1,
        level = 80,
    ),
    relics = emptyList(),
    relicSets = emptyList(),
    attributes = emptyList(),
    additions = emptyList(),
)
