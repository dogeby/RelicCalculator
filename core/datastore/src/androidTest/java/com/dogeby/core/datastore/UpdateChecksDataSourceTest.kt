package com.dogeby.core.datastore

import androidx.test.core.app.ApplicationProvider
import com.dogeby.core.datastore.di.DataStoreModule
import com.dogeby.core.datastore.updatechecks.UpdateChecksDataSource
import com.dogeby.core.datastore.updatechecks.UpdateChecksDataSourceImpl
import com.dogeby.core.datastore.updatechecks.UpdateChecksSerializer
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class UpdateChecksDataSourceTest {

    private lateinit var updateChecksDataSource: UpdateChecksDataSource
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        val dataStore = DataStoreModule.providesUpdateChecksDataStore(
            context = ApplicationProvider.getApplicationContext(),
            ioDispatcher = testDispatcher,
            updateChecksSerializer = UpdateChecksSerializer(),
        )
        updateChecksDataSource = UpdateChecksDataSourceImpl(
            dataStore,
        )
    }

    @Test
    fun test_getUpdateChecksData_success() = runTest(testDispatcher) {
        Assert.assertEquals(
            86_400,
            updateChecksDataSource.updateChecksData.first().defaultPresetCheckIntervalSecond,
        )
    }
}
