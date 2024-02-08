package com.dogeby.core.storage

import android.content.Context
import com.dogeby.core.common.dispatcher.Dispatcher
import com.dogeby.core.common.dispatcher.RcDispatchers
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileNotFoundException
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

@Singleton
class InternalStorageManager @Inject constructor(
    @ApplicationContext private val context: Context,
    @Dispatcher(RcDispatchers.IO) private val dispatcher: CoroutineDispatcher,
) : StorageManager {

    override suspend fun saveStringToFile(
        data: String,
        fileName: String,
        path: String,
    ) = runCatching {
        File("${context.filesDir.path}$path", fileName).run {
            withContext(dispatcher) {
                parentFile?.mkdirs()
                bufferedWriter().use {
                    it.write(data)
                    it.flush()
                }
            }
        }
    }

    override suspend fun loadStringFromFile(
        fileName: String,
        path: String,
    ) = runCatching {
        val parent = "${context.filesDir.path}$path"
        File(parent, fileName).run {
            if (exists().not()) throw FileNotFoundException("$parent/$fileName")
            withContext(dispatcher) {
                bufferedReader().use {
                    it.readText()
                }
            }
        }
    }
}
