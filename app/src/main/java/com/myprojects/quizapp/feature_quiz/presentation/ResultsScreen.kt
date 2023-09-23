package com.myprojects.quizapp.feature_quiz.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myprojects.quizapp.feature_quiz.presentation.ui.theme.BackgroundBlue
import com.myprojects.quizapp.feature_quiz.presentation.ui.theme.BackgroundOrange
import com.myprojects.quizapp.feature_quiz.presentation.ui.theme.Black50
import com.myprojects.quizapp.feature_quiz.presentation.ui.theme.White70
import com.myprojects.quizapp.feature_quiz.presentation.ui.theme.White90
import com.myprojects.quizapp.feature_quiz.presentation.ui.theme.rubicFF

@Composable
fun ResultsScreen(
    viewModel: QuizViewModel,
    onBackToMenu: () -> Unit
) {

    BackHandler(
        enabled = true
    ) {
        onBackToMenu()
    }

    val state = viewModel.state.value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    listOf(BackgroundOrange, BackgroundBlue)
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(40.dp)
                .align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "You answered correctly ${state.guessedCount} " +
                        "time${if (state.guessedCount != 1) "s" else ""} " +
                        "out of 10.",
                fontFamily = rubicFF,
                fontWeight = FontWeight.Medium,
                fontSize = 24.sp,
                color = White90,
                textAlign = TextAlign.Center,
                lineHeight = 30.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = when (state.guessedCount) {
                    in 0..3 -> "Try better next time!"
                    in 4..6 -> "Practice makes perfect!"
                    in 7..9 -> "That's a great result!"
                    10 -> "You're a champion!"
                    else -> ""
                },
                fontFamily = rubicFF,
                fontSize = 16.sp,
                color = White70,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(48.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                ElevatedButton(
                    onClick = {
                        onBackToMenu()
//                        viewModel.onEvent(QuizEvent.ResetQuiz)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Black50,
                        contentColor = White90
                    ),
                    elevation = ButtonDefaults.elevatedButtonElevation(4.dp)
                ) {
                    Text(
                        text = "Try Again",
                        fontFamily = rubicFF,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}