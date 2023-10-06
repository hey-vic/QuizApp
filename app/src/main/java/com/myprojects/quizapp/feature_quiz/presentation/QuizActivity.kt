package com.myprojects.quizapp.feature_quiz.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.myprojects.quizapp.core.util.Routes
import com.myprojects.quizapp.feature_quiz.presentation.ui.theme.QuizAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuizActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuizAppTheme {
                val viewModel: QuizViewModel = hiltViewModel()
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Routes.MENU_SCREEN) {
                    composable(Routes.MENU_SCREEN) {
                        MenuScreen(
                            viewModel = viewModel,
                            onNavigate = { route -> navController.navigate(route) }
                        )
                    }
                    composable(Routes.GAME_SCREEN) {
                        GameScreen(
                            viewModel = viewModel,
                            onPopBackStack = navController::popBackStack,
                            onNavigate = { route -> navController.navigate(route) }
                        )
                    }
                    composable(Routes.RESULTS_SCREEN) {
                        ResultsScreen(
                            viewModel = viewModel,
                            onBackToMenu = {
                                navController.popBackStack(Routes.MENU_SCREEN, false)
                            }
                        )
                    }
                }
            }
        }
    }
}