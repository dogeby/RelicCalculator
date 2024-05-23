package com.dogeby.reliccalculator.core.domain.report

import com.dogeby.reliccalculator.core.data.repository.CharacterReportRepository
import com.dogeby.reliccalculator.core.data.repository.GameRepository
import com.dogeby.reliccalculator.core.domain.model.CharacterSimpleReport
import com.dogeby.reliccalculator.core.domain.model.toSimpleRelicRatingReport
import com.dogeby.reliccalculator.core.model.mihomo.index.RelicInfo
import com.dogeby.reliccalculator.core.model.mihomo.index.RelicPiece
import com.dogeby.reliccalculator.core.model.preferences.CharacterSortField
import com.dogeby.reliccalculator.core.model.report.RelicReport
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetCharSimpleReportsUseCase @Inject constructor(
    private val characterReportRepository: CharacterReportRepository,
    private val gameRepository: GameRepository,
) {

    operator fun invoke(
        filteredRarities: Set<Int>,
        filteredPathIds: Set<String>,
        filteredElementIds: Set<String>,
        sortField: CharacterSortField,
    ): Flow<List<CharacterSimpleReport>> {
        return combine(
            characterReportRepository.getLatestCharacterReports(),
            gameRepository.characterInfoWithDetailsList,
            gameRepository.relicInfoMap,
        ) { reports, characterInfoWithDetailsList, relicInfoMap ->
            val filteredCharacters = characterInfoWithDetailsList
                .filter { details ->
                    details.characterInfo.rarity.isMatching(filteredRarities) &&
                        details.pathInfo.id.isMatching(filteredPathIds) &&
                        details.elementInfo.id.isMatching(filteredElementIds)
                }
                .associateBy { it.characterInfo.id }

            reports.mapNotNull { characterReport ->
                filteredCharacters[characterReport.character.id]?.let { details ->
                    val (cavernRelics, planarOrnaments) = divideRelicReports(
                        relicReports = characterReport.relicReports,
                        relicInfoMap = relicInfoMap,
                    )
                    CharacterSimpleReport(
                        id = characterReport.id,
                        characterId = characterReport.character.id,
                        name = details.characterInfo.name,
                        icon = details.characterInfo.icon,
                        updatedDate = characterReport.generationTime,
                        totalScore = characterReport.score,
                        cavernRelics = cavernRelics.map {
                            it.toSimpleRelicRatingReport(relicInfoMap[it.id]?.icon ?: "")
                        },
                        planarOrnaments = planarOrnaments.map {
                            it.toSimpleRelicRatingReport(relicInfoMap[it.id]?.icon ?: "")
                        },
                    )
                }
            }.sortedBy(sortField)
        }
    }

    private fun <T> T.isMatching(list: Set<T>) = list.isEmpty() or (this in list)

    private data class DividedRelicReports(
        val cavernRelics: List<RelicReport>,
        val planarOrnaments: List<RelicReport>,
    )

    private fun divideRelicReports(
        relicReports: List<RelicReport>,
        relicInfoMap: Map<String, RelicInfo>,
    ): DividedRelicReports {
        val cavernRelics = mutableListOf<RelicReport>()
        val planarOrnaments = mutableListOf<RelicReport>()

        for (relicReport in relicReports) {
            when (relicInfoMap[relicReport.id]?.type) {
                RelicPiece.HEAD, RelicPiece.HAND,
                RelicPiece.BODY, RelicPiece.FOOT,
                -> cavernRelics.add(
                    relicReport,
                )
                RelicPiece.NECK, RelicPiece.OBJECT -> planarOrnaments.add(relicReport)
                null -> Unit
            }
        }

        return DividedRelicReports(cavernRelics, planarOrnaments)
    }

    private fun String.toAdjustedId(): Int =
        toIntOrNull()?.let { if (it >= TRAILBLAZER_ID) it - TRAILBLAZER_ID else it }
            ?: 0

    private fun List<CharacterSimpleReport>.sortedBy(
        characterSortField: CharacterSortField,
    ): List<CharacterSimpleReport> {
        return when (characterSortField) {
            CharacterSortField.ID_ASC -> sortedWith(compareBy { it.characterId.toAdjustedId() })
            CharacterSortField.ID_DESC -> sortedWith(
                compareByDescending { it.characterId.toAdjustedId() },
            )
            CharacterSortField.NAME_ASC -> sortedBy { it.name }
            CharacterSortField.NAME_DESC -> sortedByDescending { it.name }
        }
    }

    private companion object {
        const val TRAILBLAZER_ID = 8001
    }
}
