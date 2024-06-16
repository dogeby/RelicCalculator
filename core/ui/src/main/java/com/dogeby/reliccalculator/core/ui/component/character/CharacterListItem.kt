package com.dogeby.reliccalculator.core.ui.component.character

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dogeby.reliccalculator.core.ui.R
import com.dogeby.reliccalculator.core.ui.component.DateTimeText
import com.dogeby.reliccalculator.core.ui.component.Rating
import com.dogeby.reliccalculator.core.ui.component.image.GameImage
import com.dogeby.reliccalculator.core.ui.theme.RelicCalculatorTheme
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

@Composable
fun CharacterListItem(
    characterName: String,
    characterIcon: String,
    modifier: Modifier = Modifier,
    supportingContent: @Composable (() -> Unit)? = null,
    trailingContent: @Composable (() -> Unit)? = null,
) {
    Row(
        modifier = modifier
            .padding(
                start = 16.dp,
                top = 8.dp,
                end = 24.dp,
                bottom = 8.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        GameImage(
            src = characterIcon,
            modifier = Modifier
                .size(54.dp)
                .clip(CircleShape),
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(Modifier.weight(1f)) {
            Text(
                text = characterName,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                style = MaterialTheme.typography.titleMedium,
            )
            supportingContent?.let {
                it()
            }
        }
        trailingContent?.let {
            it()
        }
    }
}

private const val UPDATED_DATE_FORMAT_PATTERN = "yy-MM-dd HH:mm"

@Composable
fun CharacterListItemWithUpdatedDate(
    characterName: String,
    characterIcon: String,
    updatedDate: Instant,
    modifier: Modifier = Modifier,
    trailingContent: @Composable (() -> Unit)? = null,
) {
    CharacterListItem(
        characterName = characterName,
        characterIcon = characterIcon,
        modifier = modifier,
        supportingContent = {
            Row {
                Text(
                    text = "${stringResource(id = R.string.updated)}: ",
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = MaterialTheme.typography.labelSmall,
                )
                DateTimeText(
                    instant = updatedDate,
                    formatPattern = UPDATED_DATE_FORMAT_PATTERN,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = MaterialTheme.typography.labelSmall,
                )
            }
        },
        trailingContent = trailingContent,
    )
}

@Composable
fun CharacterListItemWithRating(
    characterName: String,
    characterIcon: String,
    updatedDate: Instant,
    score: Float,
    modifier: Modifier = Modifier,
) {
    CharacterListItemWithUpdatedDate(
        characterName = characterName,
        characterIcon = characterIcon,
        updatedDate = updatedDate,
        modifier = modifier,
    ) {
        Rating(
            rating = score,
            color = MaterialTheme.colorScheme.primaryContainer,
        )
    }
}

@Preview(apiLevel = 33)
@Composable
private fun PreviewCharacterListItem() {
    RelicCalculatorTheme {
        CharacterListItem(
            characterName = "test",
            characterIcon = "",
            trailingContent = {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                    )
                }
            },
        )
    }
}

@Preview(apiLevel = 33)
@Composable
private fun PreviewCharacterListItemWithUpdatedDate() {
    RelicCalculatorTheme {
        CharacterListItemWithUpdatedDate(
            characterName = "test",
            characterIcon = "",
            updatedDate = Clock.System.now(),
            trailingContent = {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                    )
                }
            },
        )
    }
}

@Preview(apiLevel = 33)
@Composable
private fun PreviewCharacterListItemWithRating() {
    RelicCalculatorTheme {
        CharacterListItemWithRating(
            characterName = "test",
            characterIcon = "",
            updatedDate = Clock.System.now(),
            score = 4.5f,
            modifier = Modifier.padding(horizontal = 8.dp),
        )
    }
}
