package com.dogeby.reliccalculator.core.storage

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import java.io.FileNotFoundException
import java.io.IOException
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class InternalStorageManagerTest {

    private lateinit var storageManager: StorageManager
    private val testDispatcher = StandardTestDispatcher()

    private val data = "test_data"
    private val fileName = "test_file"
    private val path = "test_path"

    @Before
    fun createManager() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        storageManager = InternalStorageManager(
            context = context,
            dispatcher = testDispatcher,
        )
    }

    @Test
    fun test_saveStringToFile_success(): Unit = runTest(testDispatcher) {
        val result = storageManager.saveStringToFile(
            data = data,
            fileName = fileName,
            path = path,
        )

        Assert.assertTrue(result.isSuccess)
    }

    @Test
    fun test_loadStringFromFile_success() = runTest(testDispatcher) {
        storageManager.saveStringToFile(
            data = data,
            fileName = fileName,
            path = path,
        ).also {
            Assert.assertTrue(it.isSuccess)
        }

        val result = storageManager.loadStringFromFile(
            fileName = fileName,
            path = path,
        )
        val dataInFile = result.getOrThrow()

        Assert.assertEquals(dataInFile, data)
    }

    @Test
    fun test_loadStringFromFile_incorrectFileName() = runTest(testDispatcher) {
        storageManager.saveStringToFile(
            data = data,
            fileName = fileName,
            path = path,
        ).also {
            Assert.assertTrue(it.isSuccess)
        }

        val result = storageManager.loadStringFromFile(
            fileName = "incorrect_$fileName",
            path = path,
        )

        Assert.assertThrows(IOException::class.java) {
            result.getOrThrow()
        }
    }

    @Test
    fun test_loadStringFromFile_incorrectPath() = runTest(testDispatcher) {
        storageManager.saveStringToFile(
            data = data,
            fileName = fileName,
            path = path,
        ).also {
            Assert.assertTrue(it.isSuccess)
        }

        val result = storageManager.loadStringFromFile(
            fileName = fileName,
            path = "incorrect_$path",
        )

        Assert.assertThrows(IOException::class.java) {
            result.getOrThrow()
        }
    }

    @Test
    fun test_loadStringFromFile_fileIsNotExist() = runTest(testDispatcher) {
        val result = storageManager.loadStringFromFile(
            fileName = "test_fileIsNotExist_fileName",
            path = path,
        )

        Assert.assertThrows(FileNotFoundException::class.java) {
            result.getOrThrow()
        }
    }
}
