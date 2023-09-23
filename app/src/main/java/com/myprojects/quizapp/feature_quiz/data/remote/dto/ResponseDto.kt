package com.myprojects.quizapp.feature_quiz.data.remote.dto


import com.google.gson.annotations.SerializedName
import com.myprojects.quizapp.feature_quiz.domain.model.Response

data class ResponseDto(
    @SerializedName("response_code")
    val responseCode: Int,
    @SerializedName("results")
    val questions: List<QuestionInfoDto>?
) {
    fun toResponse(): Response {
        return Response(
            responseCode = responseCode,
            questions = questions?.map { it.toQuestionInfo() }
        )
    }
}