package com.dogeby.reliccalculator.core.domain.preference

import com.dogeby.reliccalculator.core.data.repository.PreferencesRepository
import com.dogeby.reliccalculator.core.model.preferences.CharacterSortField
import com.dogeby.reliccalculator.core.model.preferences.PresetListPreferencesData
import javax.inject.Inject

class SetPresetListPreferencesDataUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository,
) {

    suspend operator fun invoke(
        filteredRarities: Set<Int>,
        filteredPathIds: Set<String>,
        filteredElementIds: Set<String>,
        sortField: CharacterSortField,
    ): Result<Unit> {
        return preferencesRepository.setPresetListPreferencesData(
            PresetListPreferencesData(
                filteredRarities = filteredRarities,
                filteredPathIds = filteredPathIds,
                filteredElementIds = filteredElementIds,
                sortField = sortField,
            ),
        )
    }
}
