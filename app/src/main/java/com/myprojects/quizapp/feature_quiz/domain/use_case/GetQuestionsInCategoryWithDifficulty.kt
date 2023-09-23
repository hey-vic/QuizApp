package com.myprojects.quizapp.feature_quiz.domain.use_case

import com.myprojects.quizapp.core.util.Status
import com.myprojects.quizapp.feature_quiz.domain.model.QuestionInfo
import com.myprojects.quizapp.feature_quiz.domain.repository.QuizRepository
import kotlinx.coroutines.flow.Flow

class GetQuestionsInCategoryWithDifficulty(
    private val repository: QuizRepository
) {

    operator fun invoke(
        amount: String,
        categoryId: String,
        difficulty: String
    ): Flow<Status<List<QuestionInfo>?>> {
        return repository.getQuestionsInCategoryWithDifficulty(amount, categoryId, difficulty)
    }
}