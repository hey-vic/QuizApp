package com.myprojects.quizapp.feature_quiz.domain.use_case

import com.myprojects.quizapp.core.util.Status
import com.myprojects.quizapp.feature_quiz.domain.model.Category
import com.myprojects.quizapp.feature_quiz.domain.repository.QuizRepository
import kotlinx.coroutines.flow.Flow

class GetAllCategories(
    private val repository: QuizRepository
) {

    operator fun invoke(): Flow<Status<List<Category>>> {
        return repository.getAllCategories()
    }
}