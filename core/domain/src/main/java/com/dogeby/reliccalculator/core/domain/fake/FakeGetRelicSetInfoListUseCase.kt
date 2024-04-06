package com.dogeby.reliccalculator.core.domain.fake

import com.dogeby.reliccalculator.core.domain.index.GetRelicSetInfoListUseCase
import com.dogeby.reliccalculator.core.model.mihomo.index.RelicSetInfo
import com.dogeby.reliccalculator.core.model.mihomo.index.sampleRelicSetInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.jetbrains.annotations.TestOnly

@TestOnly
class FakeGetRelicSetInfoListUseCase : GetRelicSetInfoListUseCase {

    override operator fun invoke(): Flow<List<RelicSetInfo>> {
        return flow {
            emit(listOf(sampleRelicSetInfo))
        }
    }
}
