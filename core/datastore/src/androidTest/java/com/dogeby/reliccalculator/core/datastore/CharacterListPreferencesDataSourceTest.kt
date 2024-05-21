package com.dogeby.reliccalculator.core.datastore

import androidx.test.core.app.ApplicationProvider
import com.dogeby.reliccalculator.core.datastore.characterlistpreferences.CharacterListPreferencesDataSource
import com.dogeby.reliccalculator.core.datastore.characterlistpreferences.CharacterListPreferencesDataSourceImpl
import com.dogeby.reliccalculator.core.datastore.characterlistpreferences.CharacterListPreferencesSerializer
import com.dogeby.reliccalculator.core.datastore.di.DataStoreModule
import com.dogeby.reliccalculator.core.model.preferences.CharacterListPreferencesData
import com.dogeby.reliccalculator.core.model.preferences.CharacterSortField
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CharacterListPreferencesDataSourceTest {

    private lateinit var characterListPreferencesDataSource: CharacterListPreferencesDataSource
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        val dataStore = DataStoreModule.providesCharacterListPreferencesDataStore(
            context = ApplicationProvider.getApplicationContext(),
            ioDispatcher = testDispatcher,
            characterListPreferencesSerializer = CharacterListPreferencesSerializer(),
        )
        characterListPreferencesDataSource = CharacterListPreferencesDataSourceImpl(dataStore)
    }

    @After
    fun clear() = runTest(testDispatcher) {
        characterListPreferencesDataSource.clearData()
    }

    @Test
    fun test_setFilteredData_success() = runTest(testDispatcher) {
        val inputFilteredData = CharacterListPreferencesData(
            filteredRarities = setOf(4, 5),
            filteredPathIds = setOf("Rogue"),
            filteredElementIds = setOf("Fire"),
            sortField = CharacterSortField.ID_ASC,
        )
        characterListPreferencesDataSource.setCharacterListPreferencesData(inputFilteredData)

        Assert.assertEquals(
            inputFilteredData,
            characterListPreferencesDataSource.characterListPreferencesData.first(),
        )
    }
}
