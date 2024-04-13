package com.dogeby.reliccalculator.core.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dogeby.reliccalculator.core.ui.R

@Composable
fun LoadingState(modifier: Modifier = Modifier) {
    LinearProgressIndicator(modifier.fillMaxWidth())
}

@Composable
fun EmptyState(
    modifier: Modifier = Modifier,
    text: String = stringResource(id = R.string.empty_list),
    style: TextStyle = MaterialTheme.typography.titleMedium,
    fontWeight: FontWeight? = FontWeight.Bold,
    minHeight: Dp = 72.dp,
) {
    Box(
        modifier = modifier.fillMaxWidth().heightIn(min = minHeight),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            style = style,
            fontWeight = fontWeight,
        )
    }
}
