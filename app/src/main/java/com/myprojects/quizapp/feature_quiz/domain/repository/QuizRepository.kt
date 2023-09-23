package com.myprojects.quizapp.feature_quiz.domain.repository

import com.myprojects.quizapp.core.util.Status
import com.myprojects.quizapp.feature_quiz.domain.model.Category
import com.myprojects.quizapp.feature_quiz.domain.model.QuestionInfo
import kotlinx.coroutines.flow.Flow

interface QuizRepository {

    fun getQuestionsInAnyCategory(amount: String): Flow<Status<List<QuestionInfo>?>>

    fun getQuestionsInAnyCategoryWithDifficulty(
        amount: String,
        difficulty: String
    ): Flow<Status<List<QuestionInfo>?>>

    fun getQuestionsInCategory(
        amount: String,
        categoryId: String
    ): Flow<Status<List<QuestionInfo>?>>

    fun getQuestionsInCategoryWithDifficulty(
        amount: String,
        categoryId: String,
        difficulty: String
    ): Flow<Status<List<QuestionInfo>?>>

    fun getAllCategories(): Flow<Status<List<Category>>>
}