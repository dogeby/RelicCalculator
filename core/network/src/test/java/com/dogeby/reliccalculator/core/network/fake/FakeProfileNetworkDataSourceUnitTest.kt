package com.dogeby.reliccalculator.core.network.fake

import JvmUnitTestFakeAssetManager
import com.dogeby.reliccalculator.core.common.di.CommonModule
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class FakeProfileNetworkDataSourceUnitTest {

    private lateinit var fakeProfileNetwork: FakeProfileNetworkDataSource

    private val testDispatcher = StandardTestDispatcher()
    private val uid = "800000000"

    @Before
    fun setUp() {
        fakeProfileNetwork = FakeProfileNetworkDataSource(
            ioDispatcher = testDispatcher,
            networkJson = CommonModule.providesJson(),
            assets = JvmUnitTestFakeAssetManager,
        )
    }

    @Test
    fun test_getProfile_success() = runTest(testDispatcher) {
        Assert.assertEquals(
            uid,
            fakeProfileNetwork.getProfile(uid, "en").getOrThrow().player.uid,
        )
    }
}
