package com.dogeby.reliccalculator.core.domain.index

import com.dogeby.reliccalculator.core.data.repository.GameRepository
import com.dogeby.reliccalculator.core.data.repository.PreferencesRepository
import javax.inject.Inject

class UpdateGameInfoInDbUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository,
    private val gameRepository: GameRepository,
) {

    suspend operator fun invoke() {
        preferencesRepository.getGameTextLanguage().collect {
            gameRepository.updateGameInfoInDb(it)
        }
    }
}
