package com.dogeby.reliccalculator.core.domain.preference

import com.dogeby.core.data.repository.PreferencesRepository
import com.dogeby.reliccalculator.core.model.preferences.PresetListPreferencesData
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetPresetListPreferencesDataUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository,
) {

    operator fun invoke(): Flow<PresetListPreferencesData> {
        return preferencesRepository.getPresetListPreferencesData()
    }
}
