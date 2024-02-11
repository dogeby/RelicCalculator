package com.dogeby.core.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.dogeby.core.common.dispatcher.Dispatcher
import com.dogeby.core.common.dispatcher.RcDispatchers
import com.dogeby.core.datastore.UpdateChecks
import com.dogeby.core.datastore.updatechecks.UpdateChecksDataSource
import com.dogeby.core.datastore.updatechecks.UpdateChecksDataSourceImpl
import com.dogeby.core.datastore.updatechecks.UpdateChecksSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    private const val UPDATE_CHECKS_DATA_STORE_FILE = "update_checks.pb"

    @Provides
    @Singleton
    fun providesUpdateChecksDataStore(
        @ApplicationContext context: Context,
        @Dispatcher(RcDispatchers.IO) ioDispatcher: CoroutineDispatcher,
        updateChecksSerializer: UpdateChecksSerializer,
    ): DataStore<UpdateChecks> = DataStoreFactory.create(
        serializer = updateChecksSerializer,
        scope = CoroutineScope(ioDispatcher + SupervisorJob()),
    ) {
        context.dataStoreFile(UPDATE_CHECKS_DATA_STORE_FILE)
    }

    @Provides
    @Singleton
    fun providesUpdateChecksDataSource(
        updateChecksDataSourceImpl: UpdateChecksDataSourceImpl,
    ): UpdateChecksDataSource = updateChecksDataSourceImpl
}
