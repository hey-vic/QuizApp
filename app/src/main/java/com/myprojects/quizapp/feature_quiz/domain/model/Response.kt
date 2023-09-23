package com.myprojects.quizapp.feature_quiz.domain.model

data class Response(
    val responseCode: Int,
    val questions: List<QuestionInfo>?
)
