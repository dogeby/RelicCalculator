package com.dogeby.reliccalculator.core.domain.preset

import com.dogeby.reliccalculator.core.data.repository.GameRepository
import com.dogeby.reliccalculator.core.domain.model.AffixWeightWithInfo
import com.dogeby.reliccalculator.core.model.mihomo.index.RelicPiece
import com.dogeby.reliccalculator.core.model.preset.AffixWeight
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

interface GetMainAffixWeightWithInfoUseCase {

    operator fun invoke(): Flow<Map<RelicPiece, List<AffixWeightWithInfo>>>
}

class GetMainAffixWeightWithInfoUseCaseImpl @Inject constructor(
    private val gameRepository: GameRepository,
) : GetMainAffixWeightWithInfoUseCase {

    override operator fun invoke(): Flow<Map<RelicPiece, List<AffixWeightWithInfo>>> {
        return gameRepository
            .relicAffixInfoMap
            .combine(gameRepository.propertyInfoMap) { relicAffixInfoMap, propertyInfoMap ->
                relicAffixInfoMap
                    .filter { it.key in "51".."56" }
                    .entries
                    .associate { (id, affixData) ->
                        val relicPiece = id.toRelicPiece()
                            ?: return@combine emptyMap<RelicPiece, List<AffixWeightWithInfo>>()
                        val affixWeights = affixData.affixes.mapNotNull { (affixId, affixInfo) ->
                            val affixWeight = AffixWeight(
                                affixId = affixId,
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
                        relicPiece to affixWeights
                    }
            }
    }

    private fun String.toRelicPiece(): RelicPiece? {
        return when (this) {
            "51" -> RelicPiece.HEAD
            "52" -> RelicPiece.HAND
            "53" -> RelicPiece.BODY
            "54" -> RelicPiece.FOOT
            "55" -> RelicPiece.NECK
            "56" -> RelicPiece.OBJECT
            else -> null
        }
    }
}
