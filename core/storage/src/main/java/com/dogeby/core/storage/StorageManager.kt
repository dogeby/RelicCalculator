package com.dogeby.core.storage

interface StorageManager {

    suspend fun saveStringToFile(
        data: String,
        fileName: String,
        path: String,
    ): Result<Unit>

    suspend fun loadStringFromFile(
        fileName: String,
        path: String,
    ): Result<String>
}
