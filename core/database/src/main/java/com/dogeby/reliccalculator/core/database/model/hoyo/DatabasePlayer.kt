package com.dogeby.reliccalculator.core.database.model.hoyo

import androidx.room.Embedded
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
