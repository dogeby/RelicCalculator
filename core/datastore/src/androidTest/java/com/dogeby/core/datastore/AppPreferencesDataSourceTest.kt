package com.dogeby.core.datastore

import androidx.test.core.app.ApplicationProvider
import com.dogeby.core.datastore.apppreferences.AppPreferencesDataSource
import com.dogeby.core.datastore.apppreferences.AppPreferencesDataSourceImpl
import com.dogeby.core.datastore.apppreferences.AppPreferencesSerializer
import com.dogeby.core.datastore.di.DataStoreModule
import com.dogeby.reliccalculator.core.model.GameTextLanguage
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class AppPreferencesDataSourceTest {

    private lateinit var appPreferencesDataSource: AppPreferencesDataSource
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        val dataStore = DataStoreModule.providesAppPreferencesDataStore(
            context = ApplicationProvider.getApplicationContext(),
            ioDispatcher = testDispatcher,
            appPreferencesSerializer = AppPreferencesSerializer(),
        )
        appPreferencesDataSource = AppPreferencesDataSourceImpl(
            dataStore,
        )
    }

    @Test
    fun test_setGameTextLanguage_success() = runTest(testDispatcher) {
        val initLang = appPreferencesDataSource.appPreferencesData.first().gameTextLanguage

        Assert.assertEquals(GameTextLanguage.EN, initLang)

        val inputLang = GameTextLanguage.KR
        appPreferencesDataSource.setGameTextLanguage(inputLang)
        val newLang = appPreferencesDataSource.appPreferencesData.first().gameTextLanguage

        Assert.assertEquals(inputLang, newLang)
    }
}
