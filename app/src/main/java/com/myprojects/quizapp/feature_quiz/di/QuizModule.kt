package com.myprojects.quizapp.feature_quiz.di

import com.myprojects.quizapp.feature_quiz.data.remote.QuizApi
import com.myprojects.quizapp.feature_quiz.data.repository.QuizRepositoryImpl
import com.myprojects.quizapp.feature_quiz.domain.repository.QuizRepository
import com.myprojects.quizapp.feature_quiz.domain.use_case.GetAllCategories
import com.myprojects.quizapp.feature_quiz.domain.use_case.GetQuestionsInAnyCategory
import com.myprojects.quizapp.feature_quiz.domain.use_case.GetQuestionsInAnyCategoryWithDifficulty
import com.myprojects.quizapp.feature_quiz.domain.use_case.GetQuestionsInCategory
import com.myprojects.quizapp.feature_quiz.domain.use_case.GetQuestionsInCategoryWithDifficulty
import com.myprojects.quizapp.feature_quiz.domain.use_case.QuizUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object QuizModule {

    @Provides
    @Singleton
    fun provideQuizUseCases(
        repository: QuizRepository
    ): QuizUseCases {
        return QuizUseCases(
            getAllCategories = GetAllCategories(repository),
            getQuestionsInCategory = GetQuestionsInCategory(repository),
            getQuestionsInAnyCategory = GetQuestionsInAnyCategory(repository),
            getQuestionsInAnyCategoryWithDifficulty = GetQuestionsInAnyCategoryWithDifficulty(repository),
            getQuestionsInCategoryWithDifficulty = GetQuestionsInCategoryWithDifficulty(repository)
        )
    }

    @Provides
    @Singleton
    fun provideQuestionsRepository(
        api: QuizApi
    ): QuizRepository {
        return QuizRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideQuizApi(): QuizApi {
        return Retrofit.Builder()
            .baseUrl(QuizApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuizApi::class.java)
    }
}