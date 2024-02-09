package com.dogeby.core.storage.di

import com.dogeby.core.storage.InternalStorageManager
import com.dogeby.core.storage.StorageManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class StorageManagerModule {

    @Qualifier
    @Retention
    annotation class InternalStorage

    @InternalStorage
    @Binds
    @Singleton
    abstract fun bindsInternalStorageManager(
        internalStorageManager: InternalStorageManager,
    ): StorageManager
}
