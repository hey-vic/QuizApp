package com.myprojects.quizapp.feature_quiz.data.mappers

import com.myprojects.quizapp.feature_quiz.data.remote.dto.QuestionInfoDto
import com.myprojects.quizapp.feature_quiz.domain.model.QuestionInfo

fun QuestionInfoDto.toQuestionInfo(): QuestionInfo {
    return QuestionInfo(
        question = question,
        correctAnswer = correctAnswer,
        incorrectAnswers = incorrectAnswers,
        category = category,
        difficulty = difficulty
    )
}