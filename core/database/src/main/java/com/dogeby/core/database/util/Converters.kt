package com.dogeby.core.database.util

import androidx.room.TypeConverter
import com.dogeby.core.database.model.hoyo.DatabaseAttribute
import com.dogeby.core.database.model.hoyo.DatabaseRelic
import com.dogeby.core.database.model.hoyo.DatabaseRelicSet
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class RelicListConverter {

    @TypeConverter
    fun relicListToString(relics: List<DatabaseRelic>) = Json.encodeToString(relics)

    @TypeConverter
    fun stringToRelicList(json: String) = Json.decodeFromString<List<DatabaseRelic>>(json)
}

class RelicSetListConverter {

    @TypeConverter
    fun relicSetToString(relicSets: List<DatabaseRelicSet>) = Json.encodeToString(relicSets)

    @TypeConverter
    fun stringToRelicSet(json: String) = Json.decodeFromString<List<DatabaseRelicSet>>(json)
}

class AttributeListConverter {

    @TypeConverter
    fun attributeToString(attributes: List<DatabaseAttribute>) = Json.encodeToString(attributes)

    @TypeConverter
    fun stringToAttribute(json: String) = Json.decodeFromString<List<DatabaseAttribute>>(json)
}
