package com.dogeby.reliccalculator.core.datastore.characterlistpreferences

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.dogeby.core.datastore.CharacterListPreferences
import com.dogeby.reliccalculator.core.model.preferences.CharacterSortField
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterListPreferencesSerializer @Inject constructor() :
    Serializer<CharacterListPreferences> {

    override val defaultValue: CharacterListPreferences
        get() = CharacterListPreferences.getDefaultInstance()
            .toBuilder()
            .setSortField(CharacterSortField.ID_ASC.name)
            .build()

    override suspend fun readFrom(input: InputStream): CharacterListPreferences {
        return try {
            CharacterListPreferences.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(
        t: CharacterListPreferences,
        output: OutputStream,
    ) {
        return t.writeTo(output)
    }
}
