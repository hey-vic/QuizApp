package com.myprojects.quizapp.feature_quiz.presentation.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import com.myprojects.quizapp.feature_quiz.presentation.ui.theme.ButtonGreen
import com.myprojects.quizapp.feature_quiz.presentation.ui.theme.ButtonGreenDark
import com.myprojects.quizapp.feature_quiz.presentation.ui.theme.ButtonRed
import com.myprojects.quizapp.feature_quiz.presentation.ui.theme.ButtonRedDark
import com.myprojects.quizapp.feature_quiz.presentation.ui.theme.White20
import com.myprojects.quizapp.feature_quiz.presentation.ui.theme.White90
import com.myprojects.quizapp.feature_quiz.presentation.ui.theme.rubicFF
import com.myprojects.quizapp.feature_quiz.presentation.util.AnswerOptionState

@Composable
fun AnswerOption(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isClicked: Boolean = false,
    state: AnswerOptionState = AnswerOptionState.NOT_CLICKED
) {
    val infiniteTransition = rememberInfiniteTransition(label = "infinite_transition")
    val animatedRed by infiniteTransition.animateColor(
        initialValue = ButtonRed,
        targetValue = ButtonRedDark,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "red_animation"
    )
    val animatedGreen by infiniteTransition.animateColor(
        initialValue = ButtonGreen,
        targetValue = ButtonGreenDark,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "green_animation"
    )

    Button(
        onClick = onClick,
        modifier = modifier.fillMaxSize(),
        enabled = state == AnswerOptionState.NOT_CLICKED,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = White20,
            disabledContainerColor = when (state) {
                AnswerOptionState.INCORRECT -> animatedRed
                AnswerOptionState.CORRECT -> animatedGreen
                else -> White20
            },
            contentColor = White90,
            disabledContentColor = White90
        ),
        border = BorderStroke(
            width = 2.dp,
            color = if (isClicked) White90 else Color.Transparent
        ),
        contentPadding = PaddingValues(8.dp)
    ) {
        Text(
            text = HtmlCompat.fromHtml(
                text,
                HtmlCompat.FROM_HTML_MODE_LEGACY
            ).toString(),
            fontFamily = rubicFF,
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp,
            modifier = Modifier.padding(vertical = 4.dp),
            textAlign = TextAlign.Center
        )
    }
}