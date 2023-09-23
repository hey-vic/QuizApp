package com.myprojects.quizapp.feature_quiz.domain.use_case

data class QuizUseCases(
    val getAllCategories: GetAllCategories,
    val getQuestionsInCategory: GetQuestionsInCategory,
    val getQuestionsInAnyCategory: GetQuestionsInAnyCategory,
    val getQuestionsInAnyCategoryWithDifficulty: GetQuestionsInAnyCategoryWithDifficulty,
    val getQuestionsInCategoryWithDifficulty: GetQuestionsInCategoryWithDifficulty
)
