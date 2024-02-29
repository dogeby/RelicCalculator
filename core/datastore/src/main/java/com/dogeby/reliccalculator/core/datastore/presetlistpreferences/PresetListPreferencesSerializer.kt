package com.dogeby.reliccalculator.core.datastore.presetlistpreferences

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.dogeby.core.datastore.PresetListPreferences
import com.dogeby.reliccalculator.core.model.preferences.CharacterSortField
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PresetListPreferencesSerializer @Inject constructor() : Serializer<PresetListPreferences> {

    override val defaultValue: PresetListPreferences
        get() = PresetListPreferences.getDefaultInstance()
            .toBuilder()
            .setSortField(CharacterSortField.ID_ASC.name)
            .build()

    override suspend fun readFrom(input: InputStream): PresetListPreferences {
        return try {
            PresetListPreferences.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(
        t: PresetListPreferences,
        output: OutputStream,
    ) {
        return t.writeTo(output)
    }
}
