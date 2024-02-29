package com.dogeby.reliccalculator.core.datastore

import androidx.test.core.app.ApplicationProvider
import com.dogeby.reliccalculator.core.datastore.di.DataStoreModule
import com.dogeby.reliccalculator.core.datastore.presetlistpreferences.PresetListPreferencesDataSource
import com.dogeby.reliccalculator.core.datastore.presetlistpreferences.PresetListPreferencesDataSourceImpl
import com.dogeby.reliccalculator.core.datastore.presetlistpreferences.PresetListPreferencesSerializer
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
            sortField = CharacterSortField.ID_ASC,
        )
        presetListPreferencesDataSource.setPresetListPreferencesData(inputFilteredData)

        Assert.assertEquals(
            inputFilteredData,
            presetListPreferencesDataSource.presetListPreferencesData.first(),
        )
    }
}
