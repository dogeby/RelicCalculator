package com.dogeby.core.database.util

import androidx.room.TypeConverter
import com.dogeby.reliccalculator.core.model.hoyo.Attribute
import com.dogeby.reliccalculator.core.model.hoyo.Relic
import com.dogeby.reliccalculator.core.model.hoyo.RelicSet
import com.dogeby.reliccalculator.core.model.hoyo.index.AffixInfo
import com.dogeby.reliccalculator.core.model.hoyo.index.RelicPiece
import com.dogeby.reliccalculator.core.model.preset.AffixWeight
import com.dogeby.reliccalculator.core.model.preset.AttrComparison
import com.dogeby.reliccalculator.core.model.report.AffixCount
import com.dogeby.reliccalculator.core.model.report.AttrComparisonReport
import com.dogeby.reliccalculator.core.model.report.RelicReport
import kotlinx.datetime.Instant
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class RelicListConverter {

    @TypeConverter
    fun relicListToString(relics: List<Relic>) = Json.encodeToString(relics)

    @TypeConverter
    fun stringToRelicList(json: String) = Json.decodeFromString<List<Relic>>(json)
}

class RelicSetListConverter {

    @TypeConverter
    fun relicSetListToString(relicSets: List<RelicSet>) = Json.encodeToString(relicSets)

    @TypeConverter
    fun stringToRelicSetList(json: String) = Json.decodeFromString<List<RelicSet>>(json)
}

class AttributeListConverter {

    @TypeConverter
    fun attributeListToString(attributes: List<Attribute>) = Json.encodeToString(attributes)

    @TypeConverter
    fun stringToAttributeList(json: String) = Json.decodeFromString<List<Attribute>>(json)
}

class StringListConverter {

    @TypeConverter
    fun stringListToString(stringList: List<String>) = Json.encodeToString(stringList)

    @TypeConverter
    fun stringToStringList(json: String) = Json.decodeFromString<List<String>>(json)
}

class MainAffixWeightMapConverter {

    @TypeConverter
    fun affixWeightMapToString(affixWeights: Map<RelicPiece, List<AffixWeight>>) =
        Json.encodeToString(affixWeights)

    @TypeConverter
    fun stringToAffixWeightMap(json: String) =
        Json.decodeFromString<Map<RelicPiece, List<AffixWeight>>>(json)
}

class AffixWeightListConverter {

    @TypeConverter
    fun affixWeightListToString(affixWeights: List<AffixWeight>) = Json.encodeToString(affixWeights)

    @TypeConverter
    fun stringToAffixWeightList(json: String) = Json.decodeFromString<List<AffixWeight>>(json)
}

class RelicReportListConverter {

    @TypeConverter
    fun relicReportListToString(relicReports: List<RelicReport>) = Json.encodeToString(relicReports)

    @TypeConverter
    fun stringToRelicReportList(json: String) = Json.decodeFromString<List<RelicReport>>(json)
}

class AttrComparisonListConverter {

    @TypeConverter
    fun attrComparisonListToString(attrComparisons: List<AttrComparison>) =
        Json.encodeToString(attrComparisons)

    @TypeConverter
    fun stringToAttrComparisonList(json: String) = Json.decodeFromString<List<AttrComparison>>(json)
}

class AttrComparisonReportListConverter {

    @TypeConverter
    fun attrComparisonReportListToString(attrComparisonReports: List<AttrComparisonReport>) =
        Json.encodeToString(attrComparisonReports)

    @TypeConverter
    fun stringToAttrComparisonReportList(json: String) =
        Json.decodeFromString<List<AttrComparisonReport>>(json)
}

class InstantConverter {

    @TypeConverter
    fun instantToString(instant: Instant) = Json.encodeToString(instant)

    @TypeConverter
    fun stringToInstant(json: String) = Json.decodeFromString<Instant>(json)
}

class AffixCountListConverter {

    @TypeConverter
    fun affixCountListToString(affixCounts: List<AffixCount>) = Json.encodeToString(affixCounts)

    @TypeConverter
    fun stringToAffixCountList(json: String) = Json.decodeFromString<List<AffixCount>>(json)
}

class AffixInfoMapConverter {

    @TypeConverter
    fun affixInfoMapToString(affixes: Map<String, AffixInfo>) = Json.encodeToString(affixes)

    @TypeConverter
    fun stringToAffixInfoMap(json: String) = Json.decodeFromString<Map<String, AffixInfo>>(json)
}
