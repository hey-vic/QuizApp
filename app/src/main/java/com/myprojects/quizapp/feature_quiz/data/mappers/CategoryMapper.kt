package com.myprojects.quizapp.feature_quiz.data.mappers

import com.myprojects.quizapp.feature_quiz.data.remote.dto.CategoryDto
import com.myprojects.quizapp.feature_quiz.domain.model.Category

fun CategoryDto.toCategory(): Category {
    return Category(
        id = id,
        name = name
    )
}