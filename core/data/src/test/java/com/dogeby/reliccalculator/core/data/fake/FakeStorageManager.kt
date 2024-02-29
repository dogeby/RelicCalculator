package com.dogeby.reliccalculator.core.data.fake

import com.dogeby.reliccalculator.core.storage.StorageManager
import java.io.FileNotFoundException
import java.io.IOException

class FakeStorageManager : StorageManager {

    private var data = ""
    private var fileName = ""
    private var path = ""

    override suspend fun saveStringToFile(
        data: String,
        fileName: String,
        path: String,
    ): Result<Unit> = runCatching {
        this.data = data
        this.fileName = fileName
        this.path = path
    }

    override suspend fun loadStringFromFile(
        fileName: String,
        path: String,
    ): Result<String> = runCatching {
        if (data.isEmpty()) throw FileNotFoundException()
        if (this.fileName != fileName || this.path != path) {
            throw IOException()
        }
        data
    }
}
