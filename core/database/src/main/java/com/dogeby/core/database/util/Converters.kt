package com.dogeby.core.database.util

import androidx.room.TypeConverter
import com.dogeby.core.database.model.hoyo.DatabaseAttribute
import com.dogeby.core.database.model.hoyo.DatabaseRelic
import com.dogeby.core.database.model.hoyo.DatabaseRelicSet
import com.dogeby.core.database.model.preset.DatabaseRelicStatWeight
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
    fun relicSetListToString(relicSets: List<DatabaseRelicSet>) = Json.encodeToString(relicSets)

    @TypeConverter
    fun stringToRelicSetList(json: String) = Json.decodeFromString<List<DatabaseRelicSet>>(json)
}

class AttributeListConverter {

    @TypeConverter
    fun attributeListToString(attributes: List<DatabaseAttribute>) = Json.encodeToString(attributes)

    @TypeConverter
    fun stringToAttributeList(json: String) = Json.decodeFromString<List<DatabaseAttribute>>(json)
}

class RelicSetIdListConverter {

    @TypeConverter
    fun relicSetIdListToString(relicSetIds: List<String>) = Json.encodeToString(relicSetIds)

    @TypeConverter
    fun stringToRelicSetIdList(json: String) = Json.decodeFromString<List<String>>(json)
}

class RelicStatWeightListConverter {

    @TypeConverter
    fun relicStatWeightListToString(relicStatWeights: List<DatabaseRelicStatWeight>) =
        Json.encodeToString(relicStatWeights)

    @TypeConverter
    fun stringToRelicStatWeightList(json: String) =
        Json.decodeFromString<List<DatabaseRelicStatWeight>>(json)
}
