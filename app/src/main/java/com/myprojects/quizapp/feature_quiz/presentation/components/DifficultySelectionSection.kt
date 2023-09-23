package com.myprojects.quizapp.feature_quiz.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DifficultySelectionSection(
    options: List<String>,
    checked: List<Boolean>,
    onCheckedChange: (Int, Boolean) -> Unit,
    modifier: Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        options.forEachIndexed { i, option ->
            CheckBoxItem(
                isChecked = checked[i],
                onCheckedChange = { newVal -> onCheckedChange(i, newVal) },
                text = option,
                modifier = Modifier.weight(1f)
            )
        }
    }
}