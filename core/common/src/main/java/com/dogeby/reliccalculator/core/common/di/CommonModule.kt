package com.dogeby.reliccalculator.core.common.di

import com.dogeby.reliccalculator.core.common.decoder.StringDecoder
import com.dogeby.reliccalculator.core.common.decoder.UriDecoder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.serialization.json.Json

@Module
@InstallIn(SingletonComponent::class)
object CommonModule {

    @Provides
    @Singleton
    fun providesJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun providesStringDecoder(): StringDecoder = UriDecoder()
}
