package com.dogeby.reliccalculator.core.database.model.hoyo

import androidx.room.Embedded
import com.dogeby.reliccalculator.core.model.mihomo.Player
import org.jetbrains.annotations.TestOnly

data class DatabasePlayer(
    val uid: String,
    val nickname: String,
    @Embedded val spaceInfo: DatabaseSpaceInfo,
)

@TestOnly
internal val sampleDatabasePlayer = DatabasePlayer(
    uid = "800000100",
    nickname = "testNickname",
    spaceInfo = DatabaseSpaceInfo(5),
)

fun DatabasePlayer.toPlayer() = Player(
    uid = uid,
    nickname = nickname,
    spaceInfo = spaceInfo.toSpaceInfo(),
)
