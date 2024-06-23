package com.dogeby.reliccalculator.core.ui.component.history

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.dogeby.reliccalculator.core.ui.R
import com.dogeby.reliccalculator.core.ui.component.EmptyState
import com.dogeby.reliccalculator.core.ui.component.character.CharacterListItemWithRating
import com.dogeby.reliccalculator.core.ui.theme.RelicCalculatorTheme
import kotlinx.datetime.Instant

private const val CHAR_REPORT_VISIBLE_ITEMS = 3

@Composable
fun CharReportRecordCard(
    charReportRecordCardUiState: CharReportRecordCardUiState,
    onCharReportItemClick: (id: Int, characterId: String) -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = CardDefaults.shape,
    colors: CardColors = CardDefaults.cardColors(),
) {
    var openDialog by remember { mutableStateOf(false) }

    Card(
        modifier = modifier,
        shape = shape,
        colors = colors,
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            with(charReportRecordCardUiState) {
                if (charReportItems.size > CHAR_REPORT_VISIBLE_ITEMS) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                openDialog = true
                            },
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = stringResource(id = R.string.record),
                            style = MaterialTheme.typography.titleMedium,
                        )
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowForward,
                            contentDescription = null,
                        )
                    }
                } else {
                    Text(
                        text = stringResource(id = R.string.record),
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.titleLarge,
                    )
                }
                if (charReportItems.isEmpty()) {
                    EmptyState()
                } else {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        charReportItems.take(CHAR_REPORT_VISIBLE_ITEMS).forEach {
                            Surface(
                                shape = CardDefaults.shape,
                            ) {
                                CharacterListItemWithRating(
                                    characterName = characterName,
                                    characterIcon = characterIcon,
                                    updatedDate = it.updatedDate,
                                    score = it.score,
                                    modifier = Modifier.clickable {
                                        onCharReportItemClick(it.id, characterId)
                                    },
                                )
                            }
                        }
                    }
                }
                if (openDialog) {
                    CharacterReportRecordListDialog(
                        characterId = characterId,
                        characterName = characterName,
                        characterIcon = characterIcon,
                        charReportItems = charReportItems,
                        onDismissRequest = { openDialog = false },
                        onCharReportItemClick = onCharReportItemClick,
                    )
                }
            }
        }
    }
}

data class CharReportRecordCardUiState(
    val characterId: String,
    val characterName: String,
    val characterIcon: String,
    val charReportItems: List<CharReportItemUiState>,
)

data class CharReportItemUiState(
    val id: Int,
    val updatedDate: Instant,
    val score: Float,
)

@Composable
fun CharacterReportRecordListDialog(
    characterId: String,
    characterName: String,
    characterIcon: String,
    charReportItems: List<CharReportItemUiState>,
    onDismissRequest: () -> Unit,
    onCharReportItemClick: (id: Int, characterId: String) -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    IconButton(
                        onClick = onDismissRequest,
                        modifier = Modifier.size(24.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null,
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = stringResource(id = R.string.record),
                        style = MaterialTheme.typography.titleLarge,
                    )
                }
                LazyColumn(
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    items(charReportItems) {
                        Surface(
                            shape = CardDefaults.shape,
                            tonalElevation = 1.dp,
                        ) {
                            CharacterListItemWithRating(
                                characterName = characterName,
                                characterIcon = characterIcon,
                                updatedDate = it.updatedDate,
                                score = it.score,
                                modifier = Modifier.clickable {
                                    onCharReportItemClick(it.id, characterId)
                                },
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(apiLevel = 33)
@Composable
private fun PreviewCharReportRecordCard() {
    RelicCalculatorTheme {
        CharReportRecordCard(
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
            onCharReportItemClick = { _, _ -> },
            modifier = Modifier.padding(8.dp),
        )
    }
}

@Preview(apiLevel = 33)
@Composable
private fun PreviewCharacterReportRecordListDialog() {
    RelicCalculatorTheme {
        CharacterReportRecordListDialog(
            characterId = "test",
            characterName = "name",
            characterIcon = "icon/character/1107.png",
            charReportItems = List(5) {
                CharReportItemUiState(
                    id = it,
                    updatedDate = Instant.parse("2023-06-01T22:19:44.475Z"),
                    score = 4.5f,
                )
            },
            onDismissRequest = {},
            onCharReportItemClick = { _, _ -> },
        )
    }
}
