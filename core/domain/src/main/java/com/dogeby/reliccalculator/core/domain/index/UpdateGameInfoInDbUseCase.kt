package com.dogeby.reliccalculator.core.domain.index

import com.dogeby.reliccalculator.core.data.repository.GameRepository
import com.dogeby.reliccalculator.core.data.repository.PreferencesRepository
import com.dogeby.reliccalculator.core.model.GameTextLanguage
import javax.inject.Inject
import kotlinx.coroutines.flow.firstOrNull

class UpdateGameInfoInDbUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository,
    private val gameRepository: GameRepository,
) {

    suspend operator fun invoke(): Result<GameTextLanguage> = runCatching {
        val gameTextLanguage = preferencesRepository
            .getGameTextLanguage()
            .firstOrNull()
            ?: throw IllegalStateException("Game text language not found")
        gameRepository.updateGameInfoInDb(gameTextLanguage)
        gameTextLanguage
    }
}
