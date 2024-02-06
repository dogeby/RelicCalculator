package com.dogeby.reliccalculator.core.network.fake

import JvmUnitTestFakeAssetManager
import com.dogeby.reliccalculator.core.network.di.NetworkModule
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
            networkJson = NetworkModule.providesNetworkJson(),
            assets = JvmUnitTestFakeAssetManager,
        )
    }

    @Test
    fun test_getDefaultPreset_success() = runTest(testDispatcher) {
        val result = fakePresetNetworkDataSource.getDefaultPreset().getOrThrow()

        Assert.assertEquals(
            "24:01:30:02:05:36",
            result.updateDate,
        )
    }

    @Test
    fun test_getDefaultPresetString_success() = runTest(testDispatcher) {
        val result = fakePresetNetworkDataSource.getDefaultPresetJson().getOrThrow()

        Assert.assertEquals(
            39023,
            result.length,
        )
    }
}
