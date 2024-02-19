package com.dogeby.reliccalculator.core.model.hoyo.index

import kotlinx.serialization.Serializable
import org.jetbrains.annotations.TestOnly

@Serializable
data class RelicSetInfo(
    val id: String,
    val name: String,
    val desc: List<String>,
    val icon: String,
)

@TestOnly
val sampleRelicSetInfo = RelicSetInfo(
    id = "104",
    name = "혹한 밀림의 사냥꾼",
    desc = listOf(
        "얼음 속성 피해 10% 증가",
        "장착한 캐릭터는 필살기 발동 시 치명타 피해가 25% 증가한다. 지속 시간: 2턴",
    ),
    icon = "icon/relic/104.png",
)
