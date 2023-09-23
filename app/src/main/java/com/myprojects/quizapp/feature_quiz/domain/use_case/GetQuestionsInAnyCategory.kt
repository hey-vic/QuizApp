package com.myprojects.quizapp.feature_quiz.domain.use_case

import com.myprojects.quizapp.core.util.Status
import com.myprojects.quizapp.feature_quiz.domain.model.QuestionInfo
import com.myprojects.quizapp.feature_quiz.domain.repository.QuizRepository
import kotlinx.coroutines.flow.Flow

class GetQuestionsInAnyCategory(
    private val repository: QuizRepository
) {

    operator fun invoke(amount: String): Flow<Status<List<QuestionInfo>?>> {
        return repository.getQuestionsInAnyCategory(amount)
    }
}