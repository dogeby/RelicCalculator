/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dogeby.reliccalculator.core.network.di

import android.content.Context
import com.dogeby.reliccalculator.core.network.PresetNetworkDataSource
import com.dogeby.reliccalculator.core.network.ProfileNetworkDataSource
import com.dogeby.reliccalculator.core.network.fake.FakeAssetManager
import com.dogeby.reliccalculator.core.network.retrofit.RetrofitPresetNetwork
import com.dogeby.reliccalculator.core.network.retrofit.RetrofitProfileNetwork
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesFakeAssetManager(@ApplicationContext context: Context): FakeAssetManager =
        FakeAssetManager(context.assets::open)

    @Provides
    @Singleton
    fun providesPresetNetworkDataSource(
        retrofitPresetNetwork: RetrofitPresetNetwork,
    ): PresetNetworkDataSource = retrofitPresetNetwork

    @Provides
    @Singleton
    fun providesProfileNetworkDataSource(
        retrofitProfileNetwork: RetrofitProfileNetwork,
    ): ProfileNetworkDataSource = retrofitProfileNetwork
}
