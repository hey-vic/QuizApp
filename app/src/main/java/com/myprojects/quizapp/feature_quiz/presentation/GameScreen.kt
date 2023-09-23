package com.myprojects.quizapp.feature_quiz.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import com.myprojects.quizapp.core.util.UIEvent
import com.myprojects.quizapp.feature_quiz.presentation.components.AnswerOption
import com.myprojects.quizapp.feature_quiz.presentation.ui.theme.White20
import com.myprojects.quizapp.feature_quiz.presentation.ui.theme.White70
import com.myprojects.quizapp.feature_quiz.presentation.ui.theme.White90
import com.myprojects.quizapp.feature_quiz.presentation.ui.theme.rubicFF
import com.myprojects.quizapp.feature_quiz.presentation.util.QuizEvent
import kotlinx.coroutines.flow.collectLatest

@Composable
fun GameScreen(
    viewModel: QuizViewModel,
    onPopBackStack: () -> Unit,
    onNavigate: (String) -> Unit
) {

    BackHandler(
        enabled = true
    ) {
        onPopBackStack()
    }

    val state = viewModel.state.value
    val currentQuestion = if (state.questions.isNotEmpty()) {
        state.questions[state.currentQuestionIndex]
    } else null
    val backgroundColorTop = remember {
        Animatable(viewModel.randomColorTop.value)
    }
    val backgroundColorBottom = remember {
        Animatable(viewModel.randomColorBottom.value)
    }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UIEvent.Navigate -> {
                    onNavigate(event.route)
                }

                else -> Unit
            }
        }
    }

    LaunchedEffect(key1 = viewModel.randomColorTop.value) {
        backgroundColorTop.animateTo(
            viewModel.randomColorTop.value,
            animationSpec = tween(
                durationMillis = 500
            )
        )
    }

    LaunchedEffect(key1 = viewModel.randomColorBottom.value) {
        backgroundColorBottom.animateTo(
            viewModel.randomColorBottom.value,
            animationSpec = tween(
                durationMillis = 500
            )
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    listOf(backgroundColorTop.value, backgroundColorBottom.value)
                )
            )
            .padding(16.dp)
    ) {
        currentQuestion?.let { question ->
            Column(
                modifier = Modifier.align(Alignment.TopCenter)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = HtmlCompat.fromHtml(
                            question.category,
                            HtmlCompat.FROM_HTML_MODE_LEGACY
                        ).toString(),
                        fontFamily = rubicFF,
                        fontSize = 14.sp,
                        color = White70
                    )
                    Text(
                        text = HtmlCompat.fromHtml(
                            question.difficulty,
                            HtmlCompat.FROM_HTML_MODE_LEGACY
                        ).toString(),
                        fontFamily = rubicFF,
                        fontSize = 14.sp,
                        color = White70
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    for (i in 0 until state.currentQuestionIndex) {
                        Box(
                            modifier = Modifier
                                .height(2.dp)
                                .weight(1f)
                                .background(White70)
                        )
                    }
                    Box(
                        modifier = Modifier
                            .height(2.dp)
                            .weight(1f)
                            .background(White20)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth(state.currentTimerMillis / state.maxTimerMillis)
                                .background(White70)
                                .align(Alignment.CenterStart)
                        )
                    }
                    for (i in state.currentQuestionIndex + 1..state.questions.lastIndex) {
                        Box(
                            modifier = Modifier
                                .height(2.dp)
                                .weight(1f)
                                .background(White20)
                        )
                    }
                }
            }


            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
                    .padding(vertical = 40.dp),
                text = HtmlCompat.fromHtml(
                    question.question,
                    HtmlCompat.FROM_HTML_MODE_LEGACY
                ).toString(),
                fontFamily = rubicFF,
                fontSize = 24.sp,
                color = White90,
                textAlign = TextAlign.Center
            )


            if (state.currentAnswers.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.height(intrinsicSize = IntrinsicSize.Max)
                    ) {
                        AnswerOption(
                            text = state.currentAnswers[0],
                            state = state.currentAnswerOptionStates[0],
                            onClick = {
                                viewModel.onEvent(QuizEvent.OnAnswerOptionClick(0))
                            },
                            modifier = Modifier.weight(1f),
                            isClicked = state.clickedOptionIndex == 0
                        )
                        if (state.currentAnswers.size > 2) {
                            AnswerOption(
                                text = state.currentAnswers[2],
                                state = state.currentAnswerOptionStates[2],
                                onClick = {
                                    viewModel.onEvent(QuizEvent.OnAnswerOptionClick(2))
                                },
                                modifier = Modifier.weight(1f),
                                isClicked = state.clickedOptionIndex == 2
                            )
                        }
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.height(intrinsicSize = IntrinsicSize.Max)
                    ) {
                        AnswerOption(
                            text = state.currentAnswers[1],
                            state = state.currentAnswerOptionStates[1],
                            onClick = {
                                viewModel.onEvent(QuizEvent.OnAnswerOptionClick(1))
                            },
                            modifier = Modifier.weight(1f),
                            isClicked = state.clickedOptionIndex == 1
                        )
                        if (state.currentAnswers.size > 3) {
                            AnswerOption(
                                text = state.currentAnswers[3],
                                state = state.currentAnswerOptionStates[3],
                                onClick = {
                                    viewModel.onEvent(QuizEvent.OnAnswerOptionClick(3))
                                },
                                modifier = Modifier.weight(1f),
                                isClicked = state.clickedOptionIndex == 3
                            )
                        }
                    }
                }
            }
        }
    }
}