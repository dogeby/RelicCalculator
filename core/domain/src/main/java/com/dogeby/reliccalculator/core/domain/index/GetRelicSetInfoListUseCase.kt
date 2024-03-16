package com.dogeby.reliccalculator.core.domain.index

import com.dogeby.reliccalculator.core.data.repository.GameRepository
import com.dogeby.reliccalculator.core.model.mihomo.index.RelicSetInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetRelicSetInfoListUseCase @Inject constructor(
    private val gameRepository: GameRepository,
) {

    operator fun invoke(): Flow<List<RelicSetInfo>> {
        return gameRepository.relicSetInfoMap.map {
            it.values.toList()
        }
    }
}
