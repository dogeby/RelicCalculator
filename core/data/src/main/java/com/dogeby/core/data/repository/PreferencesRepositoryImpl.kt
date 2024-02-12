package com.dogeby.core.data.repository

import com.dogeby.core.datastore.apppreferences.AppPreferencesDataSource
import com.dogeby.core.datastore.updatechecks.UpdateChecksDataSource
import com.dogeby.reliccalculator.core.model.GameTextLanguage
import com.dogeby.reliccalculator.core.model.preferences.AppPreferencesData
import com.dogeby.reliccalculator.core.model.preferences.UpdateChecksData
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Instant

@Singleton
class PreferencesRepositoryImpl @Inject constructor(
    private val updateChecksDataSource: UpdateChecksDataSource,
    private val appPreferencesDataSource: AppPreferencesDataSource,
) : PreferencesRepository {

    override fun getUpdateChecksData(): Flow<UpdateChecksData> =
        updateChecksDataSource.updateChecksData

    override fun getAppPreferencesData(): Flow<AppPreferencesData> =
        appPreferencesDataSource.appPreferencesData

    override fun getGameTextLanguage(): Flow<GameTextLanguage> =
        appPreferencesDataSource.appPreferencesData.map { it.gameTextLanguage }

    override suspend fun setDefaultPresetLastCheckDate(instant: Instant): Result<Unit> {
        return updateChecksDataSource.setDefaultPresetLastCheckDate(instant)
    }

    override suspend fun setDefaultPresetCheckIntervalSecond(second: Int): Result<Unit> {
        return updateChecksDataSource.setDefaultPresetCheckIntervalSecond(second)
    }

    override suspend fun setGameTextLanguage(lang: GameTextLanguage): Result<Unit> {
        return appPreferencesDataSource.setGameTextLanguage(lang)
    }
}
