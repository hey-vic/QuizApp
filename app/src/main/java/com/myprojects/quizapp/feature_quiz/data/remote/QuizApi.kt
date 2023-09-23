package com.myprojects.quizapp.feature_quiz.data.remote

import com.myprojects.quizapp.feature_quiz.data.remote.dto.CategoriesResponseDto
import com.myprojects.quizapp.feature_quiz.data.remote.dto.ResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface QuizApi {

    companion object {
        const val BASE_URL = "https://opentdb.com/"
    }

    @GET("/api.php")
    suspend fun getQuestionsInAnyCategory(
        @Query("amount") amount: String
    ): ResponseDto

    @GET("/api.php")
    suspend fun getQuestionsInAnyCategoryWithDifficulty(
        @Query("amount") amount: String,
        @Query("difficulty") difficulty: String
    ): ResponseDto

    @GET("/api.php")
    suspend fun getQuestionsInCategory(
        @Query("amount") amount: String,
        @Query("category") categoryId: String
    ): ResponseDto

    @GET("/api.php")
    suspend fun getQuestionsInCategoryWithDifficulty(
        @Query("amount") amount: String,
        @Query("category") categoryId: String,
        @Query("difficulty") difficulty: String
    ): ResponseDto

    @GET("/api_category.php")
    suspend fun getAllCategories(): CategoriesResponseDto
}