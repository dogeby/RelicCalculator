package com.dogeby.reliccalculator.core.network.fake

import JvmUnitTestFakeAssetManager
import com.dogeby.core.common.di.CommonModule
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class FakePresetNetworkDataSourceUnitTest {

    private lateinit var fakePresetNetworkDataSource: FakePresetNetworkDataSource

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        fakePresetNetworkDataSource = FakePresetNetworkDataSource(
            ioDispatcher = testDispatcher,
            networkJson = CommonModule.providesJson(),
            assets = JvmUnitTestFakeAssetManager,
        )
    }

    @Test
    fun test_getDefaultPreset_success() = runTest(testDispatcher) {
        val result = fakePresetNetworkDataSource.getDefaultPreset().getOrThrow()

        Assert.assertEquals(
            "2024-02-10T00:04:07.553347500Z",
            result.updateDate.toString(),
        )
    }
}
