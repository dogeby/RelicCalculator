package com.dogeby.core.storage.di

import com.dogeby.core.storage.InternalStorageManager
import com.dogeby.core.storage.StorageManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
abstract class StorageManagerModule {

    @Qualifier
    @Retention
    annotation class InternalStorage

    @InternalStorage
    @Binds
    abstract fun bindsInternalStorageManager(
        internalStorageManager: InternalStorageManager,
    ): StorageManager
}
