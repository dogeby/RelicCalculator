package com.dogeby.core.database.util

import androidx.room.TypeConverter
import com.dogeby.core.database.model.hoyo.DatabaseAttribute
import com.dogeby.core.database.model.hoyo.DatabaseRelic
import com.dogeby.core.database.model.hoyo.DatabaseRelicSet
import com.dogeby.core.database.model.preset.DatabaseAffixWeight
import com.dogeby.core.database.model.report.DatabaseRelicReport
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

class PieceMainAffixWeightMapConverter {

    @TypeConverter
    fun pieceMainAffixWeightMapToString(
        pieceMainAffixWeights: Map<Int, List<DatabaseAffixWeight>>,
    ) = Json.encodeToString(pieceMainAffixWeights)

    @TypeConverter
    fun stringToPieceMainAffixWeightMap(json: String) =
        Json.decodeFromString<Map<Int, List<DatabaseAffixWeight>>>(json)
}

class SubAffixWeightListConverter {

    @TypeConverter
    fun relicStatWeightListToString(relicStatWeights: List<DatabaseAffixWeight>) =
        Json.encodeToString(relicStatWeights)

    @TypeConverter
    fun stringToRelicStatWeightList(json: String) =
        Json.decodeFromString<List<DatabaseAffixWeight>>(json)
}

class RelicReportListConverter {

    @TypeConverter
    fun relicReportListToString(relicReports: List<DatabaseRelicReport>) =
        Json.encodeToString(relicReports)

    @TypeConverter
    fun stringToRelicReportList(json: String) =
        Json.decodeFromString<List<DatabaseRelicReport>>(json)
}
