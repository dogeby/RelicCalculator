package com.dogeby.reliccalculator.core.datastore

import androidx.test.core.app.ApplicationProvider
import com.dogeby.reliccalculator.core.datastore.apppreferences.AppPreferencesDataSource
import com.dogeby.reliccalculator.core.datastore.apppreferences.AppPreferencesDataSourceImpl
import com.dogeby.reliccalculator.core.datastore.apppreferences.AppPreferencesSerializer
import com.dogeby.reliccalculator.core.datastore.di.DataStoreModule
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
        val inputLang1 = GameTextLanguage.KR
        appPreferencesDataSource.setGameTextLanguage(inputLang1)
        val newLang1 = appPreferencesDataSource.appPreferencesData.first().gameTextLanguage

        Assert.assertEquals(inputLang1, newLang1)

        val inputLang2 = GameTextLanguage.EN
        appPreferencesDataSource.setGameTextLanguage(inputLang2)
        val newLang2 = appPreferencesDataSource.appPreferencesData.first().gameTextLanguage

        Assert.assertEquals(inputLang2, newLang2)
    }
}
