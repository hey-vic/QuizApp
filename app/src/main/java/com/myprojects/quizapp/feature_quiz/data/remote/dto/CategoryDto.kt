package com.myprojects.quizapp.feature_quiz.data.remote.dto

import com.myprojects.quizapp.feature_quiz.domain.model.Category


data class CategoryDto(
    val id: Int,
    val name: String
) {
    fun toCategory(): Category {
        return Category(
            id = id,
            name = name
        )
    }
}