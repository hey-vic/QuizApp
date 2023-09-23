package com.myprojects.quizapp.feature_quiz.presentation.util

sealed interface QuizEvent {
    object OnStartQuizClick : QuizEvent
    object LoadAvailableCategories : QuizEvent
    data class OnCategorySelected(val categoryName: String?) : QuizEvent
    data class OnDifficultyChecked(val index: Int, val newVal: Boolean) : QuizEvent
    object ResetQuiz : QuizEvent
    data class OnAnswerOptionClick(val index: Int) : QuizEvent
}