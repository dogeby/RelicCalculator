package com.dogeby.reliccalculator.core.datastore

import androidx.test.core.app.ApplicationProvider
import com.dogeby.reliccalculator.core.datastore.di.DataStoreModule
import com.dogeby.reliccalculator.core.datastore.gamepreferences.GamePreferencesDataSource
import com.dogeby.reliccalculator.core.datastore.gamepreferences.GamePreferencesDataSourceImpl
import com.dogeby.reliccalculator.core.datastore.gamepreferences.GamePreferencesSerializer
import com.dogeby.reliccalculator.core.model.GameTextLanguage
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GamePreferencesDataSourceTest {

    private lateinit var gamePreferencesDataSource: GamePreferencesDataSource
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        val dataStore = DataStoreModule.providesGamePreferencesDataStore(
            context = ApplicationProvider.getApplicationContext(),
            ioDispatcher = testDispatcher,
            gamePreferencesSerializer = GamePreferencesSerializer(),
        )
        gamePreferencesDataSource = GamePreferencesDataSourceImpl(
            dataStore,
        )
    }

    @Test
    fun test_setGameTextLanguage_success() = runTest(testDispatcher) {
        val inputLang1 = GameTextLanguage.KR
        gamePreferencesDataSource.setGameTextLanguage(inputLang1)
        val newLang1 = gamePreferencesDataSource.gamePreferencesData.first().gameTextLanguage

        Assert.assertEquals(inputLang1, newLang1)

        val inputLang2 = GameTextLanguage.EN
        gamePreferencesDataSource.setGameTextLanguage(inputLang2)
        val newLang2 = gamePreferencesDataSource.gamePreferencesData.first().gameTextLanguage

        Assert.assertEquals(inputLang2, newLang2)
    }
}
