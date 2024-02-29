package com.dogeby.reliccalculator.core.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.dogeby.core.datastore.AppPreferences
import com.dogeby.core.datastore.PresetListPreferences
import com.dogeby.core.datastore.UpdateChecks
import com.dogeby.reliccalculator.core.common.dispatcher.Dispatcher
import com.dogeby.reliccalculator.core.common.dispatcher.RcDispatchers
import com.dogeby.reliccalculator.core.datastore.apppreferences.AppPreferencesDataSource
import com.dogeby.reliccalculator.core.datastore.apppreferences.AppPreferencesDataSourceImpl
import com.dogeby.reliccalculator.core.datastore.apppreferences.AppPreferencesSerializer
import com.dogeby.reliccalculator.core.datastore.presetlistpreferences.PresetListPreferencesDataSource
import com.dogeby.reliccalculator.core.datastore.presetlistpreferences.PresetListPreferencesDataSourceImpl
import com.dogeby.reliccalculator.core.datastore.presetlistpreferences.PresetListPreferencesSerializer
import com.dogeby.reliccalculator.core.datastore.updatechecks.UpdateChecksDataSource
import com.dogeby.reliccalculator.core.datastore.updatechecks.UpdateChecksDataSourceImpl
import com.dogeby.reliccalculator.core.datastore.updatechecks.UpdateChecksSerializer
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
    private const val APP_PREFERENCES_DATA_STORE_FILE = "app_preferences.pb"
    private const val PRESET_LIST_PREFERENCES_DATA_STORE_FILE = "preset_list_preferences.pb"

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

    @Provides
    @Singleton
    fun providesAppPreferencesDataStore(
        @ApplicationContext context: Context,
        @Dispatcher(RcDispatchers.IO) ioDispatcher: CoroutineDispatcher,
        appPreferencesSerializer: AppPreferencesSerializer,
    ): DataStore<AppPreferences> = DataStoreFactory.create(
        serializer = appPreferencesSerializer,
        scope = CoroutineScope(ioDispatcher + SupervisorJob()),
    ) {
        context.dataStoreFile(APP_PREFERENCES_DATA_STORE_FILE)
    }

    @Provides
    @Singleton
    fun providesAppPreferencesDataSource(
        appPreferencesDataSourceImpl: AppPreferencesDataSourceImpl,
    ): AppPreferencesDataSource = appPreferencesDataSourceImpl

    @Provides
    @Singleton
    fun providesPresetListPreferencesDataStore(
        @ApplicationContext context: Context,
        @Dispatcher(RcDispatchers.IO) ioDispatcher: CoroutineDispatcher,
        presetListPreferencesSerializer: PresetListPreferencesSerializer,
    ): DataStore<PresetListPreferences> = DataStoreFactory.create(
        serializer = presetListPreferencesSerializer,
        scope = CoroutineScope(ioDispatcher + SupervisorJob()),
    ) {
        context.dataStoreFile(PRESET_LIST_PREFERENCES_DATA_STORE_FILE)
    }

    @Provides
    @Singleton
    fun providesPresetListPreferencesDataSource(
        preferencesDataSourceImpl: PresetListPreferencesDataSourceImpl,
    ): PresetListPreferencesDataSource = preferencesDataSourceImpl
}
