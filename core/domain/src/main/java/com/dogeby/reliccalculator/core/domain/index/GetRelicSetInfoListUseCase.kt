package com.dogeby.reliccalculator.core.domain.index

import com.dogeby.reliccalculator.core.data.repository.GameRepository
import com.dogeby.reliccalculator.core.model.mihomo.index.RelicSetInfo
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface GetRelicSetInfoListUseCase {

    operator fun invoke(): Flow<List<RelicSetInfo>>
}

class GetRelicSetInfoListUseCaseImpl @Inject constructor(
    private val gameRepository: GameRepository,
) : GetRelicSetInfoListUseCase {

    override operator fun invoke(): Flow<List<RelicSetInfo>> {
        return gameRepository.relicSetInfoMap.map {
            it.values.toList()
        }
    }
}
