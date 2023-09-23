package com.myprojects.quizapp.core.util

sealed interface UIEvent {
    data class ShowSnackbar(val message: String) : UIEvent
    data class Navigate(val route: String) : UIEvent
}
