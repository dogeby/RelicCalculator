package com.dogeby.core.database.model.hoyo.index

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.dogeby.core.database.util.StringListConverter
import com.dogeby.reliccalculator.core.model.hoyo.index.RelicSetInfo

@Entity(tableName = "relicSetsInfo")
@TypeConverters(StringListConverter::class)
data class RelicSetInfoEntity(
    @PrimaryKey val id: String,
    val name: String,
    val desc: List<String>,
    val icon: String,
)

fun RelicSetInfoEntity.toRelicSetInfo() = RelicSetInfo(
    id = id,
    name = name,
    desc = desc,
    icon = icon,
)

val sampleRelicSetInfoEntity = RelicSetInfoEntity(
    id = "104",
    name = "혹한 밀림의 사냥꾼",
    desc = listOf(
        "얼음 속성 피해 10% 증가",
        "장착한 캐릭터는 필살기 발동 시 치명타 피해가 25% 증가한다. 지속 시간: 2턴",
    ),
    icon = "icon/relic/104.png",
)
