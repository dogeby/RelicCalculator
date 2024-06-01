package com.dogeby.reliccalculator.core.database.model.user

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.dogeby.reliccalculator.core.database.model.hoyo.DatabasePlayer
import com.dogeby.reliccalculator.core.database.model.hoyo.sampleDatabasePlayer
import com.dogeby.reliccalculator.core.database.model.hoyo.toPlayer
import com.dogeby.reliccalculator.core.database.util.StringListConverter
import com.dogeby.reliccalculator.core.model.user.UserProfile
import org.jetbrains.annotations.TestOnly

@Entity(tableName = "userProfiles")
@TypeConverters(StringListConverter::class)
data class UserProfileEntity(
    @PrimaryKey val id: String,
    @Embedded val player: DatabasePlayer,
    @ColumnInfo(name = "character_ids") val characterIds: List<String>,
)

@TestOnly
internal val sampleUserProfileEntity = UserProfileEntity(
    id = sampleDatabasePlayer.uid,
    player = sampleDatabasePlayer,
    characterIds = listOf("1308", "1005"),
)

fun UserProfileEntity.toUserProfile() = UserProfile(
    id = id,
    player = player.toPlayer(),
    characterIds = characterIds,
)
