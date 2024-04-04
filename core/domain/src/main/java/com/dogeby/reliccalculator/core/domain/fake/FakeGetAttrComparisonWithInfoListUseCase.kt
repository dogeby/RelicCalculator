package com.dogeby.reliccalculator.core.domain.fake

import com.dogeby.reliccalculator.core.domain.model.AttrComparisonWithInfo
import com.dogeby.reliccalculator.core.domain.preset.GetAttrComparisonWithInfoListUseCase
import com.dogeby.reliccalculator.core.model.mihomo.index.samplePropertyInfo
import com.dogeby.reliccalculator.core.model.preset.sampleAttrComparison
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.jetbrains.annotations.TestOnly

@TestOnly
class FakeGetAttrComparisonWithInfoListUseCase : GetAttrComparisonWithInfoListUseCase {

    override operator fun invoke(): Flow<List<AttrComparisonWithInfo>> {
        return flow {
            emit(
                listOf(
                    AttrComparisonWithInfo(
                        attrComparison = sampleAttrComparison,
                        propertyInfo = samplePropertyInfo,
                    ),
                ),
            )
        }
    }
}
