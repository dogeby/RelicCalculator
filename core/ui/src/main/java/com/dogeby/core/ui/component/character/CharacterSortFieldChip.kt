package com.dogeby.core.ui.component.character

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.dogeby.core.ui.R
import com.dogeby.core.ui.component.DropdownMenuChip
import com.dogeby.core.ui.theme.RelicCalculatorTheme
import com.dogeby.reliccalculator.core.model.preferences.CharacterSortField

@Composable
fun CharacterSortFieldChip(
    selectedSortField: CharacterSortField,
    onSetSortField: (CharacterSortField) -> Unit,
    modifier: Modifier = Modifier,
) {
    DropdownMenuChip(
        text = stringResource(id = R.string.sort),
        modifier = modifier,
        leadingIcon = {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Sort,
                contentDescription = null,
            )
        },
    ) {
        CharacterSortField.entries.forEach { field ->
            CharacterSortDropdownMenuItem(
                isSelected = field == selectedSortField,
                field = field,
                onClick = { onSetSortField(field) },
            )
        }
    }
}

@Composable
fun CharacterSortDropdownMenuItem(
    isSelected: Boolean,
    field: CharacterSortField,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    DropdownMenuItem(
        text = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(
                        id = when (field) {
                            CharacterSortField.ID_ASC, CharacterSortField.ID_DESC ->
                                R.string.default_order
                            CharacterSortField.NAME_ASC, CharacterSortField.NAME_DESC ->
                                R.string.name_order
                        },
                    ),
                )
                Icon(
                    imageVector = when (field) {
                        CharacterSortField.ID_ASC, CharacterSortField.NAME_ASC ->
                            Icons.Default.ArrowDropUp
                        CharacterSortField.ID_DESC, CharacterSortField.NAME_DESC ->
                            Icons.Default.ArrowDropDown
                    },
                    contentDescription = null,
                )
            }
        },
        onClick = onClick,
        modifier = modifier,
        trailingIcon = {
            if (isSelected) {
                Icon(imageVector = Icons.Default.Check, contentDescription = null)
            }
        },
    )
}

@Preview(apiLevel = 33)
@Composable
private fun PreviewCharacterSortFieldChip() {
    RelicCalculatorTheme {
        var sortField by remember {
            mutableStateOf(CharacterSortField.ID_ASC)
        }
        CharacterSortFieldChip(
            selectedSortField = sortField,
            onSetSortField = { sortField = it },
        )
    }
}

@Preview(apiLevel = 33)
@Composable
private fun PreviewCharacterSortDropdownMenuItem() {
    RelicCalculatorTheme {
        CharacterSortDropdownMenuItem(
            isSelected = true,
            field = CharacterSortField.ID_ASC,
            onClick = {},
        )
    }
}
