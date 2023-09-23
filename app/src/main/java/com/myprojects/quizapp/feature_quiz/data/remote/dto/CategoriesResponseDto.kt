package com.myprojects.quizapp.feature_quiz.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CategoriesResponseDto(
    @SerializedName("trivia_categories")
    val triviaCategories: List<CategoryDto>
)