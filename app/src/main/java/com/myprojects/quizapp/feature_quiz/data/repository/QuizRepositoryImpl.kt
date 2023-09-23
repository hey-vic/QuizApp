package com.myprojects.quizapp.feature_quiz.data.repository

import com.myprojects.quizapp.core.util.ErrorMessages
import com.myprojects.quizapp.core.util.Status
import com.myprojects.quizapp.feature_quiz.data.remote.QuizApi
import com.myprojects.quizapp.feature_quiz.domain.model.Category
import com.myprojects.quizapp.feature_quiz.domain.model.QuestionInfo
import com.myprojects.quizapp.feature_quiz.domain.repository.QuizRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class QuizRepositoryImpl(
    private val api: QuizApi
) : QuizRepository {

    override fun getQuestionsInAnyCategory(amount: String): Flow<Status<List<QuestionInfo>?>> =
        flow {
            emit(Status.Loading())
            try {
                val response = api.getQuestionsInAnyCategory(amount).toResponse()
                when (response.responseCode) {
                    0 -> {
                        emit(Status.Success(response.questions))
                    }

                    else -> {
                        emit(Status.Error(message = ErrorMessages.UNEXPECTED_SERVER_RESPONSE))
                    }
                }
            } catch (e: HttpException) {
                emit(
                    Status.Error(
                        message = e.localizedMessage ?: ErrorMessages.UNEXPECTED_ERROR
                    )
                )
            } catch (e: IOException) {
                emit(Status.Error(message = ErrorMessages.NO_INTERNET_CONNECTION))
            }
        }

    override fun getQuestionsInAnyCategoryWithDifficulty(
        amount: String,
        difficulty: String
    ): Flow<Status<List<QuestionInfo>?>> = flow {
        emit(Status.Loading())
        try {
            val response =
                api.getQuestionsInAnyCategoryWithDifficulty(amount, difficulty).toResponse()
            when (response.responseCode) {
                0 -> {
                    emit(Status.Success(response.questions))
                }

                1 -> {
                    emit(Status.Error(message = ErrorMessages.NOT_ENOUGH_QUESTIONS))
                }

                2 -> {
                    emit(Status.Error(message = ErrorMessages.INVALID_SEARCH_PARAMS))
                }

                else -> {
                    emit(Status.Error(message = ErrorMessages.UNEXPECTED_SERVER_RESPONSE))
                }
            }
        } catch (e: HttpException) {
            emit(
                Status.Error(
                    message = e.localizedMessage ?: ErrorMessages.UNEXPECTED_ERROR
                )
            )
        } catch (e: IOException) {
            emit(Status.Error(message = ErrorMessages.NO_INTERNET_CONNECTION))
        }
    }

    override fun getQuestionsInCategory(
        amount: String,
        categoryId: String
    ): Flow<Status<List<QuestionInfo>?>> = flow {
        emit(Status.Loading())
        try {
            val response = api.getQuestionsInCategory(amount, categoryId).toResponse()
            when (response.responseCode) {
                0 -> {
                    emit(Status.Success(response.questions))
                }

                1 -> {
                    emit(Status.Error(message = ErrorMessages.NOT_ENOUGH_QUESTIONS))
                }

                2 -> {
                    emit(Status.Error(message = ErrorMessages.INVALID_SEARCH_PARAMS))
                }

                else -> {
                    emit(Status.Error(message = ErrorMessages.UNEXPECTED_SERVER_RESPONSE))
                }
            }
        } catch (e: HttpException) {
            emit(
                Status.Error(
                    message = e.localizedMessage ?: ErrorMessages.UNEXPECTED_ERROR
                )
            )
        } catch (e: IOException) {
            emit(Status.Error(message = ErrorMessages.NO_INTERNET_CONNECTION))
        }
    }

    override fun getQuestionsInCategoryWithDifficulty(
        amount: String,
        categoryId: String,
        difficulty: String
    ): Flow<Status<List<QuestionInfo>?>> = flow {
        emit(Status.Loading())
        try {
            val response = api.getQuestionsInCategoryWithDifficulty(amount, categoryId, difficulty)
                .toResponse()
            when (response.responseCode) {
                0 -> {
                    emit(Status.Success(response.questions))
                }

                1 -> {
                    emit(Status.Error(message = ErrorMessages.NOT_ENOUGH_QUESTIONS))
                }

                2 -> {
                    emit(Status.Error(message = ErrorMessages.INVALID_SEARCH_PARAMS))
                }

                else -> {
                    emit(Status.Error(message = ErrorMessages.UNEXPECTED_SERVER_RESPONSE))
                }
            }
        } catch (e: HttpException) {
            emit(
                Status.Error(
                    message = e.localizedMessage ?: ErrorMessages.UNEXPECTED_ERROR
                )
            )
        } catch (e: IOException) {
            emit(Status.Error(message = ErrorMessages.NO_INTERNET_CONNECTION))
        }
    }

    override fun getAllCategories(): Flow<Status<List<Category>>> = flow {
        emit(Status.Loading())
        try {
            val categories = api.getAllCategories().triviaCategories.map { it.toCategory() }
            emit(Status.Success(categories))
        } catch (e: HttpException) {
            emit(
                Status.Error(
                    message = e.localizedMessage ?: ErrorMessages.UNEXPECTED_ERROR
                )
            )
        } catch (e: IOException) {
            emit(Status.Error(message = ErrorMessages.NO_INTERNET_CONNECTION))
        }
    }
}