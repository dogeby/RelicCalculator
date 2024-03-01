package com.dogeby.reliccalculator.core.domain.preset

import com.dogeby.reliccalculator.core.data.repository.PreferencesRepository
import com.dogeby.reliccalculator.core.data.repository.PresetRepository
import com.dogeby.reliccalculator.core.domain.model.DefaultPresetUpdateResult
import javax.inject.Inject
import kotlinx.coroutines.flow.first
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.until

class UpdateDefaultPresetsUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository,
    private val presetRepository: PresetRepository,
) {

    suspend operator fun invoke(): Result<DefaultPresetUpdateResult?> = runCatching {
        val updateChecksData = preferencesRepository.getUpdateChecksData().first()
        val diffInSecond = updateChecksData.defaultPresetLastCheckDate.until(
            other = Clock.System.now(),
            unit = DateTimeUnit.SECOND,
            timeZone = TimeZone.UTC,
        )
        if (diffInSecond < updateChecksData.defaultPresetCheckIntervalSecond) {
            return@runCatching null
        }
        val defaultPresetData = presetRepository.downloadDefaultPresetData().getOrThrow()
        presetRepository.updateDefaultPresetDataInStorage(defaultPresetData).mapCatching {
            val updatedPresetCount = presetRepository
                .updateDefaultPresetsInDb(defaultPresetData.presets)
                .getOrThrow()
            DefaultPresetUpdateResult(
                updateDate = it,
                updatedPresetCount = updatedPresetCount,
            )
        }.getOrThrow()
    }
}
