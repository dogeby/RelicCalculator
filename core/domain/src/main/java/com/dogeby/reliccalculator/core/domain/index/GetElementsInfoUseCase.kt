package com.dogeby.reliccalculator.core.domain.index

import com.dogeby.reliccalculator.core.data.repository.GameRepository
import javax.inject.Inject

class GetElementsInfoUseCase @Inject constructor(
    private val gameRepository: GameRepository,
) {

    operator fun invoke() = gameRepository.elementsInfo
}
