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

package com.dogeby.reliccalculator.core.network.fake

import JvmUnitTestFakeAssetManager
import com.dogeby.reliccalculator.core.common.dispatcher.Dispatcher
import com.dogeby.reliccalculator.core.common.dispatcher.RcDispatchers
import com.dogeby.reliccalculator.core.model.preset.PresetData
import com.dogeby.reliccalculator.core.network.PresetNetworkDataSource
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import org.jetbrains.annotations.TestOnly

@TestOnly
class FakePresetNetworkDataSource @Inject constructor(
    @Dispatcher(RcDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val networkJson: Json,
    private val assets: FakeAssetManager = JvmUnitTestFakeAssetManager,
) : PresetNetworkDataSource {

    @OptIn(ExperimentalSerializationApi::class)
    override suspend fun getDefaultPreset(): Result<PresetData> {
        return Result.success(
            withContext(ioDispatcher) {
                assets.open(DEFAULT_PRESET_ASSET).use(networkJson::decodeFromStream)
            },
        )
    }

    companion object {
        private const val DEFAULT_PRESET_ASSET = "star_rail_default_preset.json"
    }
}
