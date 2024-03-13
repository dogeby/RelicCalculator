package com.dogeby.reliccalculator.core.domain.preset

import com.dogeby.reliccalculator.core.data.repository.GameRepository
import com.dogeby.reliccalculator.core.domain.model.AttrComparisonWithInfo
import com.dogeby.reliccalculator.core.model.preset.AttrComparison
import com.dogeby.reliccalculator.core.model.preset.ComparisonOperator
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAttrComparisonWithInfoListUseCase @Inject constructor(
    private val gameRepository: GameRepository,
) {

    operator fun invoke(): Flow<List<AttrComparisonWithInfo>> {
        return gameRepository.propertyInfoMap.map { propertyInfoMap ->
            attrComparisonTypes.mapNotNull { type ->
                val propertyInfo = propertyInfoMap[type] ?: return@mapNotNull null
                val attrComparison = AttrComparison(
                    type = type,
                    field = propertyInfo.field,
                    comparedValue = 0.0f,
                    display = "0.0",
                    percent = propertyInfo.percent,
                    comparisonOperator = ComparisonOperator.GREATER_THAN_OR_EQUAL_TO,
                )

                AttrComparisonWithInfo(
                    attrComparison = attrComparison,
                    propertyInfo = propertyInfo,
                )
            }
        }
    }
}

internal val attrComparisonTypes = listOf(
    "SpeedDelta",
    "StatusProbabilityBase",
    "StatusResistanceBase",
    "BreakDamageAddedRatioBase",
    "CriticalChanceBase",
    "CriticalDamageBase",
    "HPDelta",
    "AttackDelta",
    "DefenceDelta",
)
