package com.dogeby.core.datastore.updatechecks

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.dogeby.core.datastore.UpdateChecks
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus

@Singleton
class UpdateChecksSerializer @Inject constructor() : Serializer<UpdateChecks> {

    override val defaultValue: UpdateChecks
        get() = UpdateChecks.getDefaultInstance()
            .toBuilder()
            .setDefaultPresetLastCheckDate(
                Clock.System.now()
                    .minus(
                        DEFAULT_PRESET_CHECK_INTERVAL_SECOND,
                        DateTimeUnit.SECOND,
                        TimeZone.UTC,
                    )
                    .toString(),
            )
            .setDefaultPresetCheckIntervalSecond(DEFAULT_PRESET_CHECK_INTERVAL_SECOND)
            .build()

    override suspend fun readFrom(input: InputStream): UpdateChecks {
        return try {
            UpdateChecks.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(
        t: UpdateChecks,
        output: OutputStream,
    ) {
        return t.writeTo(output)
    }

    companion object {

        private const val DEFAULT_PRESET_CHECK_INTERVAL_SECOND = 86_400
    }
}
