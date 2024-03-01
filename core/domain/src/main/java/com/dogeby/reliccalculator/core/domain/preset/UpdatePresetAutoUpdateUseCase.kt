package com.dogeby.reliccalculator.core.domain.preset

import com.dogeby.reliccalculator.core.data.repository.PresetRepository
import javax.inject.Inject

class UpdatePresetAutoUpdateUseCase @Inject constructor(
    private val presetRepository: PresetRepository,
) {

    suspend operator fun invoke(
        ids: Set<String>,
        isAutoUpdate: Boolean,
    ): Result<Int> {
        return presetRepository.updatePresetsAutoUpdate(ids, isAutoUpdate)
    }
}
