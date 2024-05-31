package com.dogeby.reliccalculator.core.domain.report

import com.dogeby.reliccalculator.core.data.repository.CharacterReportRepository
import com.dogeby.reliccalculator.core.data.repository.GameRepository
import com.dogeby.reliccalculator.core.data.repository.PreferencesRepository
import com.dogeby.reliccalculator.core.data.repository.PresetRepository
import com.dogeby.reliccalculator.core.data.repository.UserProfileRepository
import com.dogeby.reliccalculator.game.rating.CharacterRelicCalculator
import javax.inject.Inject
import kotlinx.coroutines.flow.first

typealias RefreshCharReportCount = Int

class RefreshCharReportsUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository,
    private val userProfileRepository: UserProfileRepository,
    private val presetRepository: PresetRepository,
    private val gameRepository: GameRepository,
    private val characterRelicCalculator: CharacterRelicCalculator,
    private val characterReportRepository: CharacterReportRepository,
) {

    suspend operator fun invoke(): Result<RefreshCharReportCount> = runCatching {
        val (language, uid) = preferencesRepository.getGamePreferencesData().first()

        val profile = userProfileRepository.fetchProfile(uid, language).getOrThrow()
        val fetchedCharacterIds = profile.characters.map { it.id }.toSet()

        val relicInfoMap = gameRepository.relicInfoMap.first()
        val subAffixDataMap = gameRepository.relicAffixInfoMap.first()
        val latestReports = characterReportRepository
            .getLatestCharReportsByCharIds(fetchedCharacterIds)
            .first()

        val newReports = presetRepository
            .getPresets(fetchedCharacterIds)
            .first()
            .mapNotNull { preset ->
                profile.characters
                    .find { it.id == preset.characterId }
                    ?.let { character ->
                        val latestReport = latestReports.find { it.character.id == character.id }
                        if (
                            latestReport != null &&
                            latestReport.preset == preset &&
                            latestReport.character == character
                        ) {
                            return@mapNotNull null
                        }
                        characterRelicCalculator.calculateCharacterScore(
                            character = character,
                            preset = preset,
                            relicInfoMap = relicInfoMap,
                            subAffixDataMap = subAffixDataMap,
                        )
                    }
            }
        userProfileRepository.updateUserProfiles(listOf(profile)).getOrThrow()

        if (newReports.isEmpty()) {
            0
        } else {
            characterReportRepository.insertCharacterReports(newReports).getOrThrow().size
        }
    }
}
