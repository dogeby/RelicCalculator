package com.dogeby.reliccalculator.feature.characterreport

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dogeby.reliccalculator.core.domain.model.AffixWeightWithInfo
import com.dogeby.reliccalculator.core.domain.model.AttrComparisonWithInfo
import com.dogeby.reliccalculator.core.domain.model.PresetWithDetails
import com.dogeby.reliccalculator.core.model.mihomo.index.RelicPiece
import com.dogeby.reliccalculator.core.model.mihomo.index.sampleCharacterInfo
import com.dogeby.reliccalculator.core.model.mihomo.index.sampleElementInfo
import com.dogeby.reliccalculator.core.model.mihomo.index.samplePathInfo
import com.dogeby.reliccalculator.core.model.mihomo.index.samplePropertyInfo
import com.dogeby.reliccalculator.core.model.mihomo.index.sampleRelicSetInfo
import com.dogeby.reliccalculator.core.model.preset.AffixWeight
import com.dogeby.reliccalculator.core.model.preset.AttrComparison
import com.dogeby.reliccalculator.core.model.preset.ComparisonOperator
import com.dogeby.reliccalculator.core.ui.component.LoadingState
import com.dogeby.reliccalculator.core.ui.component.history.CharReportItemUiState
import com.dogeby.reliccalculator.core.ui.component.history.CharReportRecordCard
import com.dogeby.reliccalculator.core.ui.component.history.CharReportRecordCardUiState
import com.dogeby.reliccalculator.core.ui.component.lightcone.LightConeCard
import com.dogeby.reliccalculator.core.ui.component.lightcone.LightConeCardUiState
import com.dogeby.reliccalculator.core.ui.component.preset.CharSimplePresetCard
import com.dogeby.reliccalculator.core.ui.component.relic.RelicReportCardUiState
import com.dogeby.reliccalculator.core.ui.component.relic.RelicReportListUiState
import com.dogeby.reliccalculator.core.ui.component.relic.relicReportList
import com.dogeby.reliccalculator.core.ui.component.stat.CharacterStatCard
import com.dogeby.reliccalculator.core.ui.component.stat.CharacterStatCardUiState
import com.dogeby.reliccalculator.core.ui.component.stat.CharacterStatListUiState
import com.dogeby.reliccalculator.core.ui.component.stat.CharacterStatRowUiState
import com.dogeby.reliccalculator.core.ui.component.stat.EffSubStatCountCard
import com.dogeby.reliccalculator.core.ui.component.stat.EffSubStatCountCardUiState
import com.dogeby.reliccalculator.core.ui.component.stat.RelicMainStatRowUiState
import com.dogeby.reliccalculator.core.ui.component.stat.RelicSubStatListUiState
import com.dogeby.reliccalculator.core.ui.component.stat.RelicSubStatRowUiState
import com.dogeby.reliccalculator.core.ui.component.stat.StatCountTagUiState
import com.dogeby.reliccalculator.core.ui.theme.RelicCalculatorTheme
import kotlinx.datetime.Instant

@Composable
fun CharacterReportScreen(
    characterReportScreenUiState: CharacterReportScreenUiState,
    onCharReportItemClick: (id: Int, characterId: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (characterReportScreenUiState) {
        CharacterReportScreenUiState.Loading -> LoadingState()
        is CharacterReportScreenUiState.Success -> {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(420.dp),
                modifier = modifier,
                contentPadding = PaddingValues(8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                with(characterReportScreenUiState) {
                    item {
                        Column {
                            CharacterStatCard(
                                characterStatCardUiState = characterStatCardUiState,
                                modifier = Modifier.heightIn(max = 290.dp),
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            LightConeCard(lightConeCardUiState = lightConeCardUiState)
                            Spacer(modifier = Modifier.height(16.dp))
                            EffSubStatCountCard(
                                effSubStatCountCardUiState = effSubStatCountCardUiState,
                            )
                        }
                    }
                    item {
                        Column {
                            CharSimplePresetCard(presetWithDetails = presetWithDetails)
                            Spacer(modifier = Modifier.height(16.dp))
                            CharReportRecordCard(
                                charReportRecordCardUiState = charReportRecordCardUiState,
                                onCharReportItemClick = onCharReportItemClick,
                            )
                        }
                    }
                    relicReportList(relicReportListUiState)
                }
            }
        }
    }
}

sealed interface CharacterReportScreenUiState {

    data object Loading : CharacterReportScreenUiState

    data class Success(
        val characterStatCardUiState: CharacterStatCardUiState,
        val lightConeCardUiState: LightConeCardUiState,
        val effSubStatCountCardUiState: EffSubStatCountCardUiState,
        val charReportRecordCardUiState: CharReportRecordCardUiState,
        val relicReportListUiState: RelicReportListUiState,
        val presetWithDetails: PresetWithDetails,
    ) : CharacterReportScreenUiState
}

@Preview
@Composable
fun PreviewCharacterReportScreen() {
    RelicCalculatorTheme {
        val characterReportScreenUiState = CharacterReportScreenUiState.Success(
            characterStatCardUiState = CharacterStatCardUiState(
                characterName = "name",
                characterIconSrc = "icon/character/1107.png",
                updatedDate = Instant.parse("2023-06-01T22:19:44.475Z"),
                characterScore = 4.5f,
                characterStatListUiState = CharacterStatListUiState.Success(
                    characterStats = List(10) {
                        CharacterStatRowUiState(
                            iconSrc = "icon/property/IconCriticalChance.png",
                            name = "CRIT Rate",
                            display = "5.0%",
                            comparedDisplay = "3.0",
                            comparisonOperatorSymbol = ">=",
                            isComparisonPass = when (it % 3) {
                                1 -> true
                                2 -> false
                                else -> null
                            },
                        )
                    },
                ),
            ),
            lightConeCardUiState = LightConeCardUiState(
                name = "On the Fall of an Aeon",
                iconSrc = "icon/light_cone/24000.png",
                rarity = 5,
                rank = 5,
                level = 80,
            ),
            effSubStatCountCardUiState = EffSubStatCountCardUiState(
                totalCount = 15,
                statCountTags = List(8) {
                    StatCountTagUiState(
                        iconSrc = "icon/property/IconCriticalChance.png",
                        name = "CRIT Rate",
                        count = 3,
                    )
                },
            ),
            charReportRecordCardUiState = CharReportRecordCardUiState(
                characterId = "test",
                characterName = "name",
                characterIcon = "icon/character/1107.png",
                charReportItems = List(4) {
                    CharReportItemUiState(
                        id = it,
                        updatedDate = Instant.parse("2023-06-01T22:19:44.475Z"),
                        score = 4.5f,
                    )
                },
            ),
            relicReportListUiState = RelicReportListUiState.Success(
                relicReports = List(6) {
                    RelicReportCardUiState(
                        relicName = "Taikiyan's Arclight Race Track",
                        relicIconSrc = "icon/relic/309_1.png",
                        level = 15,
                        score = 5.0f,
                        relicMainStatRowUiState = RelicMainStatRowUiState(
                            iconSrc = "icon/property/IconCriticalChance.png",
                            name = "CRIT Rate",
                            display = "100%",
                            weight = 0.5f,
                        ),
                        relicSubStatListUiState = RelicSubStatListUiState.Success(
                            relicSubStats = List(4) {
                                RelicSubStatRowUiState(
                                    iconSrc = "icon/property/IconCriticalChance.png",
                                    name = "CRIT Rate",
                                    display = "100%",
                                    weight = 0.25f * it,
                                    count = 3,
                                )
                            },
                        ),
                    )
                },
            ),
            presetWithDetails = PresetWithDetails(
                characterId = "",
                characterInfo = sampleCharacterInfo,
                pathInfo = samplePathInfo,
                elementInfo = sampleElementInfo,
                relicSets = List(3) {
                    sampleRelicSetInfo.copy(id = "$it")
                },
                pieceMainAffixWeightsWithInfo = RelicPiece.entries.associateWith {
                    List(3) {
                        AffixWeightWithInfo(
                            AffixWeight(affixId = "1$it", type = "SpeedDelta", weight = 1.0f),
                            samplePropertyInfo,
                        )
                    }
                },
                subAffixWeightsWithInfo = List(6) {
                    AffixWeightWithInfo(
                        AffixWeight(affixId = "2$it", type = "SpeedDelta", weight = 1.0f),
                        samplePropertyInfo,
                    )
                },
                isAutoUpdate = false,
                attrComparisons = List(6) {
                    AttrComparisonWithInfo(
                        attrComparison = AttrComparison(
                            type = "SpeedDelta$it",
                            field = "spd",
                            comparedValue = 500.0f,
                            display = "500",
                            percent = false,
                            comparisonOperator = ComparisonOperator.GREATER_THAN,
                        ),
                        propertyInfo = samplePropertyInfo,
                    )
                },
            ),
        )
        CharacterReportScreen(
            characterReportScreenUiState = characterReportScreenUiState,
            onCharReportItemClick = { _, _ -> },
        )
    }
}
