package com.myprojects.quizapp.feature_quiz.presentation

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myprojects.quizapp.core.util.UIEvent
import com.myprojects.quizapp.feature_quiz.presentation.components.DifficultySelectionSection
import com.myprojects.quizapp.feature_quiz.presentation.components.DropDownWithSearch
import com.myprojects.quizapp.feature_quiz.presentation.ui.theme.BackgroundBlue
import com.myprojects.quizapp.feature_quiz.presentation.ui.theme.BackgroundViolet
import com.myprojects.quizapp.feature_quiz.presentation.ui.theme.Black50
import com.myprojects.quizapp.feature_quiz.presentation.ui.theme.White20
import com.myprojects.quizapp.feature_quiz.presentation.ui.theme.White70
import com.myprojects.quizapp.feature_quiz.presentation.ui.theme.White90
import com.myprojects.quizapp.feature_quiz.presentation.ui.theme.rubicFF
import com.myprojects.quizapp.feature_quiz.presentation.util.QuizEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(
    viewModel: QuizViewModel,
    onNavigate: (String) -> Unit
) {
    val state = viewModel.state.value
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val infiniteTransition = rememberInfiniteTransition(label = "infinite_transition")
    val animatedWhite by infiniteTransition.animateColor(
        initialValue = White90,
        targetValue = White20,
        animationSpec = infiniteRepeatable(
            animation = tween(750, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "loading_animation"
    )

    LaunchedEffect(key1 = true) {
        viewModel.onEvent(QuizEvent.ResetQuiz)
        viewModel.eventFlow.collect { event ->
            when (event) {
                is UIEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(message = event.message)
                }

                is UIEvent.Navigate -> {
                    onNavigate(event.route)
                }
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.linearGradient(
                        listOf(BackgroundBlue, BackgroundViolet)
                    )
                )
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.Center),
                verticalArrangement = Arrangement.Center
            ) {
                DropDownWithSearch(
                    options = state.allCategories.map { it.name },
                    onOptionSelected = { option ->
                        viewModel.onEvent(QuizEvent.OnCategorySelected(option))
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(30.dp))
                Text(
                    text = "Set difficulty levels",
                    fontFamily = rubicFF,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = White70,
                    modifier = Modifier.padding(start = 12.dp, bottom = 8.dp)
                )

                DifficultySelectionSection(
                    options = viewModel.difficultyOptions,
                    checked = state.checkedDifficulties,
                    onCheckedChange = { i, newVal ->
                        viewModel.onEvent(QuizEvent.OnDifficultyChecked(i, newVal))
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(48.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    ElevatedButton(
                        onClick = {
                            viewModel.onEvent(QuizEvent.OnStartQuizClick)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Black50,
                            contentColor = White90
                        ),
                        elevation = ButtonDefaults.elevatedButtonElevation(4.dp)
                    ) {
                        Text(
                            text = "Start Quiz",
                            fontFamily = rubicFF,
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp
                        )
                    }
                }
            }

            if (state.isLoading) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 80.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Loading...",
                        color = animatedWhite,
                        fontFamily = rubicFF
                    )
                }
            }
        }
    }
}