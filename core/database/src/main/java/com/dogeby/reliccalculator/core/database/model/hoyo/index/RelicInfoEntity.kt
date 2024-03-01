package com.dogeby.reliccalculator.core.database.model.hoyo.index

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dogeby.reliccalculator.core.model.mihomo.index.RelicInfo
import com.dogeby.reliccalculator.core.model.mihomo.index.RelicPiece
import org.jetbrains.annotations.TestOnly

@Entity(tableName = "relicInfoTable")
data class RelicInfoEntity(
    @PrimaryKey val id: String,
    @ColumnInfo("set_id") val setId: String,
    val name: String,
    val rarity: Int,
    val type: RelicPiece,
    @ColumnInfo("max_level") val maxLevel: Int,
    @ColumnInfo("main_affix_id") val mainAffixId: String,
    @ColumnInfo("sub_affix_id") val subAffixId: String,
    val icon: String,
)

fun RelicInfoEntity.toRelicInfo() = RelicInfo(
    id = id,
    setId = setId,
    name = name,
    rarity = rarity,
    type = type,
    maxLevel = maxLevel,
    mainAffixId = mainAffixId,
    subAffixId = subAffixId,
    icon = icon,
)

@TestOnly
val sampleRelicInfoEntity = RelicInfoEntity(
    id = "61041",
    setId = "104",
    name = "Hunter's Artaius Hood",
    rarity = 5,
    type = RelicPiece.HEAD,
    maxLevel = 15,
    mainAffixId = "51",
    subAffixId = "5",
    icon = "icon/relic/104_0.png",
)
