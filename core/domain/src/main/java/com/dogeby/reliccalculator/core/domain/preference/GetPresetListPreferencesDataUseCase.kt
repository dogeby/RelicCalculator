package com.dogeby.reliccalculator.core.domain.preference

import com.dogeby.reliccalculator.core.data.repository.PreferencesRepository
import com.dogeby.reliccalculator.core.model.preferences.CharacterListPreferencesData
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetPresetListPreferencesDataUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository,
) {

    operator fun invoke(): Flow<CharacterListPreferencesData> {
        return preferencesRepository.getPresetListPreferencesData()
    }
}
