package com.dogeby.core.common.dispatcher.di

import com.dogeby.core.common.dispatcher.Dispatcher
import com.dogeby.core.common.dispatcher.RcDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object DispatchersModule {

    @Provides
    @Dispatcher(RcDispatchers.IO)
    fun providesIODispatcher() = Dispatchers.IO
}
