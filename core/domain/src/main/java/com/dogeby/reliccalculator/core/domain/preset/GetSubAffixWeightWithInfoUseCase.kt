package com.dogeby.reliccalculator.core.domain.preset

import com.dogeby.reliccalculator.core.data.repository.GameRepository
import com.dogeby.reliccalculator.core.domain.model.AffixWeightWithInfo
import com.dogeby.reliccalculator.core.model.mihomo.index.AffixData
import com.dogeby.reliccalculator.core.model.preset.AffixWeight
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

interface GetSubAffixWeightWithInfoUseCase {

    operator fun invoke(): Flow<List<AffixWeightWithInfo>>
}

class GetSubAffixWeightWithInfoUseCaseImpl @Inject constructor(
    private val gameRepository: GameRepository,
) : GetSubAffixWeightWithInfoUseCase {

    override operator fun invoke(): Flow<List<AffixWeightWithInfo>> {
        return gameRepository
            .relicAffixInfoMap
            .combine(gameRepository.propertyInfoMap) { relicAffixInfoMap, propertyInfoMap ->
                relicAffixInfoMap.getOrDefault("5", AffixData("5", emptyMap()))
                    .affixes
                    .values
                    .mapNotNull { affixInfo ->
                        val affixWeight = AffixWeight(
                            affixId = affixInfo.affixId,
                            type = affixInfo.property,
                            weight = 1.0f,
                        )
                        val propertyInfo = propertyInfoMap.getOrElse(affixInfo.property) {
                            return@mapNotNull null
                        }
                        AffixWeightWithInfo(
                            affixWeight = affixWeight,
                            propertyInfo = propertyInfo,
                        )
                    }
            }
    }
}
