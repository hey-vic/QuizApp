package com.myprojects.quizapp.feature_quiz.data.remote.dto


import com.google.gson.annotations.SerializedName
import com.myprojects.quizapp.feature_quiz.domain.model.QuestionInfo

data class QuestionInfoDto(
    val category: String,
    @SerializedName("correct_answer")
    val correctAnswer: String,
    val difficulty: String,
    @SerializedName("incorrect_answers")
    val incorrectAnswers: List<String>,
    val question: String,
    val type: String
) {
    fun toQuestionInfo(): QuestionInfo {
        return QuestionInfo(
            question = question,
            correctAnswer = correctAnswer,
            incorrectAnswers = incorrectAnswers,
            category = category,
            difficulty = difficulty
        )
    }
}