package com.dogeby.reliccalculator.core.network.fake

import JvmUnitTestFakeAssetManager
import com.dogeby.reliccalculator.core.network.di.NetworkModule
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
            networkJson = NetworkModule.providesNetworkJson(),
            assets = JvmUnitTestFakeAssetManager,
        )
    }

    @Test
    fun test_get_profile_success() = runTest(testDispatcher) {
        Assert.assertEquals(
            uid,
            fakeProfileNetwork.getProfile(uid, "en").getOrThrow().player.uid,
        )
    }

    @Test
    fun test_get_profile_lang_en() = runTest(testDispatcher) {
        Assert.assertTrue(
            fakeProfileNetwork.getProfile(uid, "en").getOrThrow()
                .characters.first().path.name.matches("[A-z]+".toRegex()),
        )
    }

    @Test
    fun test_get_profile_lang_kr() = runTest(testDispatcher) {
        Assert.assertFalse(
            fakeProfileNetwork.getProfile(uid, "kr").getOrThrow()
                .characters.first().path.name.matches("[A-z]+".toRegex()),
        )
    }
}
