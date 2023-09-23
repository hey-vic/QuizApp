package com.myprojects.quizapp.feature_quiz.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalMinimumTouchTargetEnforcement
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.myprojects.quizapp.feature_quiz.presentation.ui.theme.Black90
import com.myprojects.quizapp.feature_quiz.presentation.ui.theme.White20
import com.myprojects.quizapp.feature_quiz.presentation.ui.theme.White70
import com.myprojects.quizapp.feature_quiz.presentation.ui.theme.White90
import com.myprojects.quizapp.feature_quiz.presentation.ui.theme.rubicFF

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckBoxItem(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    text: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(CircleShape)
            .background(
                if (isChecked) White70 else White20
            )
            .clickable {
                onCheckedChange(!isChecked)
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        CompositionLocalProvider(LocalMinimumTouchTargetEnforcement provides false) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = onCheckedChange,
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp, end = 8.dp),
                colors = CheckboxDefaults.colors(
                    checkedColor = Color.Transparent,
                    uncheckedColor = White20,
                    checkmarkColor = Black90
                )
            )
        }
        Text(
            text = text,
            modifier = Modifier.padding(end = 4.dp),
            color = if (isChecked) Black90 else White90,
            fontFamily = rubicFF,
            fontWeight = FontWeight.Normal
        )
    }
}