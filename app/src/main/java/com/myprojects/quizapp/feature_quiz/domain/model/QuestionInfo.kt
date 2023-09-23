package com.myprojects.quizapp.feature_quiz.domain.model

data class QuestionInfo(
    val question: String,
    val correctAnswer: String,
    val incorrectAnswers: List<String>,
    val category: String,
    val difficulty: String
)