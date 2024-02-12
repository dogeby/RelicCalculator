package com.dogeby.core.datastore.apppreferences

import com.dogeby.reliccalculator.core.model.GameTextLanguage
import com.dogeby.reliccalculator.core.model.preferences.AppPreferencesData
import kotlinx.coroutines.flow.Flow

interface AppPreferencesDataSource {

    val appPreferencesData: Flow<AppPreferencesData>

    suspend fun setGameTextLanguage(lang: GameTextLanguage): Result<Unit>
}
