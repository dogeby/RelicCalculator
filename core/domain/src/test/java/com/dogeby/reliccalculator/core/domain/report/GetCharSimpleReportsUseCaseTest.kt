package com.dogeby.reliccalculator.core.domain.report

import com.dogeby.reliccalculator.core.data.fake.FakeCharacterReportRepository
import com.dogeby.reliccalculator.core.data.fake.FakeGameRepository
import com.dogeby.reliccalculator.core.domain.model.CharacterSimpleReport
import com.dogeby.reliccalculator.core.domain.model.toSimpleRelicRatingReport
import com.dogeby.reliccalculator.core.model.mihomo.Character
import com.dogeby.reliccalculator.core.model.mihomo.LightCone
import com.dogeby.reliccalculator.core.model.mihomo.index.RelicInfo
import com.dogeby.reliccalculator.core.model.mihomo.index.RelicPiece
import com.dogeby.reliccalculator.core.model.mihomo.index.sampleCharacterInfo
import com.dogeby.reliccalculator.core.model.mihomo.index.sampleCharacterInfoWithDetails
import com.dogeby.reliccalculator.core.model.preferences.CharacterSortField
import com.dogeby.reliccalculator.core.model.preset.Preset
import com.dogeby.reliccalculator.core.model.report.AffixReport
import com.dogeby.reliccalculator.core.model.report.CharacterReport
import com.dogeby.reliccalculator.core.model.report.RelicReport
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.minus
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetCharSimpleReportsUseCaseTest {

    private lateinit var getCharSimpleReportsUseCase: GetCharSimpleReportsUseCase
    private lateinit var gameRepository: FakeGameRepository
    private lateinit var characterReportRepository: FakeCharacterReportRepository

    private val characterSample = createCharacterSample()
    private val relicInfoSample = createRelicInfoSample()
    private val characterReportSample = createCharacterReportSample()

    @Before
    fun setUp() {
        gameRepository = FakeGameRepository()
        characterReportRepository = FakeCharacterReportRepository()

        getCharSimpleReportsUseCase = GetCharSimpleReportsUseCase(
            characterReportRepository = characterReportRepository,
            gameRepository = gameRepository,
        )
    }

    @Test
    fun test_getCharSimpleReportListUseCase_success() = runTest {
        gameRepository.apply {
            sendCharacterInfoWithDetailsList(generateSampleCharacterInfoWithDetailsList(3))
            sendRelicInfoMap(generateRelicInfoMap())
        }

        val latestCharacterReports = generateLatestCharacterReports(3)
        val allCharacterReports = latestCharacterReports +
            generateOldCharacterReports(latestCharacterReports)
        val expectedCharacterSimpleReports =
            generateExpectedCharacterSimpleReports(latestCharacterReports)

        characterReportRepository.sendCharacterReports(allCharacterReports)

        val actualCharacterSimpleReports = getCharSimpleReportsUseCase(
            filteredRarities = emptySet(),
            filteredPathIds = emptySet(),
            filteredElementIds = emptySet(),
            sortField = CharacterSortField.ID_ASC,
        ).first()

        Assert.assertEquals(expectedCharacterSimpleReports, actualCharacterSimpleReports)
    }

    private fun createCharacterSample() = Character(
        id = "1212",
        lightCone = LightCone("", 5, 1, 80),
        relics = emptyList(),
        relicSets = emptyList(),
        attributes = emptyList(),
        additions = emptyList(),
    )

    private fun createRelicInfoSample() = RelicInfo(
        id = "1",
        setId = "104",
        name = "Hunter's Artaius Hood",
        rarity = 5,
        type = RelicPiece.HEAD,
        maxLevel = 15,
        mainAffixId = "51",
        subAffixId = "5",
        icon = "icon/relic/104_0.png",
    )

    private fun createCharacterReportSample() = CharacterReport(
        id = 0,
        character = characterSample,
        preset = Preset(
            characterId = "1212",
            emptyList(),
            emptyMap(),
            emptyList(),
            attrComparisons = emptyList(),
        ),
        score = 5.0f,
        relicReports = listOf(
            createRelicReport("1"),
            createRelicReport("2"),
        ),
        attrComparisonReports = emptyList(),
        validAffixCounts = emptyList(),
        generationTime = Instant.parse("2024-02-11T00:04:07.553347500Z"),
    )

    private fun createRelicReport(id: String) = RelicReport(
        id = id,
        score = 1f,
        mainAffixReport = AffixReport(
            type = "1",
            score = 1f,
        ),
        subAffixReports = List(3) {
            AffixReport(
                type = "type$it",
                score = it.toFloat(),
            )
        },
    )

    private fun generateSampleCharacterInfoWithDetailsList(size: Int) = List(size) {
        sampleCharacterInfoWithDetails.copy(
            characterInfo = sampleCharacterInfo.copy(id = "$it"),
        )
    }

    private fun generateRelicInfoMap() = mapOf(
        "1" to relicInfoSample.copy(id = "1"),
        "2" to relicInfoSample.copy(
            id = "2",
            type = RelicPiece.OBJECT,
        ),
    )

    private fun generateLatestCharacterReports(size: Int) = List(size) { index ->
        characterReportSample.copy(
            id = index,
            character = characterSample.copy(id = "$index"),
        )
    }

    private fun generateOldCharacterReports(reports: List<CharacterReport>) = reports.map {
        it.copy(
            generationTime = it.generationTime.minus(1, DateTimeUnit.HOUR),
        )
    }

    private fun generateExpectedCharacterSimpleReports(reports: List<CharacterReport>) =
        reports.map { characterReport ->
            CharacterSimpleReport(
                id = characterReport.id,
                characterId = characterReport.character.id,
                name = "경류",
                icon = "icon/character/1212.png",
                updatedDate = characterReport.generationTime,
                totalScore = characterReport.score,
                cavernRelics = listOf(
                    characterReport.relicReports.first().toSimpleRelicRatingReport(
                        relicInfoSample.icon,
                    ),
                ),
                planarOrnaments = listOf(
                    characterReport.relicReports.last().toSimpleRelicRatingReport(
                        relicInfoSample.icon,
                    ),
                ),
            )
        }
}
