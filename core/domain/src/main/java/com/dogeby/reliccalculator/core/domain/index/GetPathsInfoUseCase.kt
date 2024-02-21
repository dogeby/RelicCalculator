package com.dogeby.reliccalculator.core.domain.index

import com.dogeby.core.data.repository.GameRepository
import javax.inject.Inject

class GetPathsInfoUseCase @Inject constructor(
    private val gameRepository: GameRepository,
) {

    operator fun invoke() = gameRepository.pathsInfo
}