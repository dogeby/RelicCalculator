package com.dogeby.reliccalculator.core.database.model.user

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.dogeby.reliccalculator.core.database.model.hoyo.DatabasePlayer
import com.dogeby.reliccalculator.core.database.model.hoyo.sampleDatabasePlayer
import com.dogeby.reliccalculator.core.database.util.StringListConverter
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
