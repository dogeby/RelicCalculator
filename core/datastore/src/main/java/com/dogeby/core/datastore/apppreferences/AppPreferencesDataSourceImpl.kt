package com.dogeby.core.datastore.apppreferences

import androidx.datastore.core.DataStore
import com.dogeby.core.datastore.AppPreferences
import com.dogeby.core.datastore.copy
import com.dogeby.reliccalculator.core.model.GameTextLanguage
import com.dogeby.reliccalculator.core.model.preferences.AppPreferencesData
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Singleton
class AppPreferencesDataSourceImpl @Inject constructor(
    private val appPreferencesDataStore: DataStore<AppPreferences>,
) : AppPreferencesDataSource {

    override val appPreferencesData: Flow<AppPreferencesData> =
        appPreferencesDataStore.data.map { appPreferences ->
            AppPreferencesData(
                GameTextLanguage
                    .entries
                    .find {
                        it.code == appPreferences.gameTextLanguage
                    } ?: GameTextLanguage.EN,
            )
        }

    override suspend fun setGameTextLanguage(lang: GameTextLanguage): Result<Unit> = runCatching {
        appPreferencesDataStore.updateData {
            it.copy {
                this.gameTextLanguage = lang.code
            }
        }
    }
}
