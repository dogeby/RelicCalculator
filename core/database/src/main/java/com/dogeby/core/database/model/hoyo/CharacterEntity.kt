package com.dogeby.core.database.model.hoyo

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.dogeby.core.database.util.AttributeListConverter
import com.dogeby.core.database.util.RelicListConverter
import com.dogeby.core.database.util.RelicSetListConverter
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
    @ColumnInfo(name = "character_name") val name: String,
    @ColumnInfo(name = "character_icon") val icon: String,
    @ColumnInfo(name = "character_preview") val preview: String,
    @ColumnInfo(name = "character_portrait") val portrait: String,
    @Embedded val path: DatabasePath,
    @Embedded val element: DatabaseElement,
    @Embedded val lightCone: DatabaseLightCone,
    @ColumnInfo(name = "character_relics") val relics: List<DatabaseRelic>,
    @ColumnInfo(name = "character_relic_sets") val relicSets: List<DatabaseRelicSet>,
    @ColumnInfo(name = "character_attributes") val attributes: List<DatabaseAttribute>,
    @ColumnInfo(name = "character_additions") val additions: List<DatabaseAttribute>,
)

@TestOnly
val sampleCharacterEntity = CharacterEntity(
    id = "1212",
    name = "Jingliu",
    icon = "",
    preview = "",
    portrait = "",
    path = DatabasePath(
        id = "Warrior",
        name = "Destruction",
        icon = "",
    ),
    element = DatabaseElement(
        id = "Ice",
        name = "Ice",
        icon = "",
    ),
    lightCone = DatabaseLightCone(
        id = "24000",
        name = "On the Fall of an Aeon",
        icon = "",
        portrait = "",
    ),
    relics = emptyList(),
    relicSets = emptyList(),
    attributes = emptyList(),
    additions = emptyList(),
)
