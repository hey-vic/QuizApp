package com.myprojects.quizapp.feature_quiz.data.mappers

import com.myprojects.quizapp.feature_quiz.data.remote.dto.ResponseDto
import com.myprojects.quizapp.feature_quiz.domain.model.Response

fun ResponseDto.toResponse(): Response {
    return Response(
        responseCode = responseCode,
        questions = questions?.map { it.toQuestionInfo() }
    )
}