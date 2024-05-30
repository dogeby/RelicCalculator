package com.dogeby.reliccalculator.core.datastore.gamepreferences

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.dogeby.core.datastore.GamePreferences
import com.dogeby.reliccalculator.core.model.GameTextLanguage
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GamePreferencesSerializer @Inject constructor() : Serializer<GamePreferences> {

    override val defaultValue: GamePreferences
        get() = GamePreferences.getDefaultInstance()
            .toBuilder()
            .setGameTextLanguage(GameTextLanguage.EN.code)
            .build()

    override suspend fun readFrom(input: InputStream): GamePreferences {
        return try {
            GamePreferences.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(
        t: GamePreferences,
        output: OutputStream,
    ) {
        return t.writeTo(output)
    }
}
