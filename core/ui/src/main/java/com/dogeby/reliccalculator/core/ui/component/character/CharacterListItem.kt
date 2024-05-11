package com.dogeby.reliccalculator.core.ui.component.character

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dogeby.reliccalculator.core.ui.component.image.GameImage
import com.dogeby.reliccalculator.core.ui.theme.RelicCalculatorTheme

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
                top = 12.dp,
                end = 4.dp,
                bottom = 12.dp,
            ),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
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
