package com.dogeby.core.datastore

import androidx.test.core.app.ApplicationProvider
import com.dogeby.core.datastore.di.DataStoreModule
import com.dogeby.core.datastore.presetlistpreferences.PresetListPreferencesDataSource
import com.dogeby.core.datastore.presetlistpreferences.PresetListPreferencesDataSourceImpl
import com.dogeby.core.datastore.presetlistpreferences.PresetListPreferencesSerializer
import com.dogeby.reliccalculator.core.model.preferences.CharacterSortField
import com.dogeby.reliccalculator.core.model.preferences.PresetListPreferencesData
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class PresetListPreferencesDataSourceTest {

    private lateinit var presetListPreferencesDataSource: PresetListPreferencesDataSource
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        val dataStore = DataStoreModule.providesPresetListPreferencesDataStore(
            context = ApplicationProvider.getApplicationContext(),
            ioDispatcher = testDispatcher,
            presetListPreferencesSerializer = PresetListPreferencesSerializer(),
        )
        presetListPreferencesDataSource = PresetListPreferencesDataSourceImpl(dataStore)
    }

    @After
    fun clear() = runTest(testDispatcher) {
        presetListPreferencesDataSource.clearData()
    }

    @Test
    fun test_setFilteredData_success() = runTest(testDispatcher) {
        val inputFilteredData = PresetListPreferencesData(
            filteredRarities = setOf(4, 5),
            filteredPathIds = setOf("Rogue"),
            filteredElementIds = setOf("Fire"),
            sortField = CharacterSortField.LATEST_RELEASED,
        )
        presetListPreferencesDataSource.setFilteredData(inputFilteredData)

        Assert.assertEquals(
            inputFilteredData,
            presetListPreferencesDataSource.presetListPreferencesData.first(),
        )
    }
}