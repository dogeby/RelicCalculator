package com.dogeby.reliccalculator.core.datastore.gamepreferences

import androidx.datastore.core.DataStore
import com.dogeby.core.datastore.GamePreferences
import com.dogeby.core.datastore.copy
import com.dogeby.reliccalculator.core.model.GameTextLanguage
import com.dogeby.reliccalculator.core.model.preferences.GamePreferencesData
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Singleton
class GamePreferencesDataSourceImpl @Inject constructor(
    private val gamePreferencesDataStore: DataStore<GamePreferences>,
) : GamePreferencesDataSource {

    override val gamePreferencesData: Flow<GamePreferencesData> =
        gamePreferencesDataStore.data.map { gamePreferences ->
            GamePreferencesData(
                gameTextLanguage = GameTextLanguage
                    .entries
                    .find {
                        it.code == gamePreferences.gameTextLanguage
                    } ?: GameTextLanguage.EN,
                uid = gamePreferences.uid,
            )
        }

    override suspend fun setGameTextLanguage(lang: GameTextLanguage): Result<Unit> = runCatching {
        gamePreferencesDataStore.updateData {
            it.copy {
                this.gameTextLanguage = lang.code
            }
        }
    }

    override suspend fun setUid(uid: String): Result<Unit> = runCatching {
        gamePreferencesDataStore.updateData {
            it.copy {
                this.uid = uid
            }
        }
    }
}
