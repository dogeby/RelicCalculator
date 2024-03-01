package com.dogeby.reliccalculator.core.datastore.apppreferences

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.dogeby.core.datastore.AppPreferences
import com.dogeby.reliccalculator.core.model.GameTextLanguage
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppPreferencesSerializer @Inject constructor() : Serializer<AppPreferences> {

    override val defaultValue: AppPreferences
        get() = AppPreferences.getDefaultInstance()
            .toBuilder()
            .setGameTextLanguage(GameTextLanguage.EN.code)
            .build()

    override suspend fun readFrom(input: InputStream): AppPreferences {
        return try {
            AppPreferences.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(
        t: AppPreferences,
        output: OutputStream,
    ) {
        return t.writeTo(output)
    }
}
