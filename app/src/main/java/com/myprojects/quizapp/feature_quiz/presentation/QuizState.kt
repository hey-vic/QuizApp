package com.myprojects.quizapp.feature_quiz.presentation

import com.myprojects.quizapp.feature_quiz.domain.model.Category
import com.myprojects.quizapp.feature_quiz.domain.model.QuestionInfo
import com.myprojects.quizapp.feature_quiz.presentation.util.AnswerOptionState

data class QuizState(
    val allCategories: List<Category> = emptyList(),
    val selectedCategory: Category? = null,
    val checkedDifficulties: List<Boolean> = List(3) { true },
    val questions: List<QuestionInfo> = emptyList(),
    val isLoading: Boolean = false,
    val currentQuestionIndex: Int = 0,
    val currentAnswers: List<String> = emptyList(),
    val currentAnswerOptionStates: List<AnswerOptionState> = emptyList(),
    val currentTimerMillis: Float = 0f,
    val maxTimerMillis: Float = 10_000f,
    val timerActive: Boolean = false,
    val clickedOptionIndex: Int = -1,
    val guessedCount: Int = 0
)