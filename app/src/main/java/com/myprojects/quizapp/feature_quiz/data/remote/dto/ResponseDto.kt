package com.myprojects.quizapp.feature_quiz.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ResponseDto(
    @SerializedName("response_code")
    val responseCode: Int,
    @SerializedName("results")
    val questions: List<QuestionInfoDto>?
)