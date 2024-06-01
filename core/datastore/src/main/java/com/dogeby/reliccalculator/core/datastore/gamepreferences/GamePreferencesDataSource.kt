package com.dogeby.reliccalculator.core.datastore.gamepreferences

import com.dogeby.reliccalculator.core.model.GameTextLanguage
import com.dogeby.reliccalculator.core.model.preferences.GamePreferencesData
import kotlinx.coroutines.flow.Flow

interface GamePreferencesDataSource {

    val gamePreferencesData: Flow<GamePreferencesData>

    suspend fun setGameTextLanguage(lang: GameTextLanguage): Result<Unit>

    suspend fun setUid(uid: String): Result<Unit>
}
