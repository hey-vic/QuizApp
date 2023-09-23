package com.myprojects.quizapp.feature_quiz.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myprojects.quizapp.core.util.ErrorMessages
import com.myprojects.quizapp.core.util.Routes
import com.myprojects.quizapp.core.util.Status
import com.myprojects.quizapp.core.util.UIEvent
import com.myprojects.quizapp.feature_quiz.domain.model.Category
import com.myprojects.quizapp.feature_quiz.domain.use_case.QuizUseCases
import com.myprojects.quizapp.feature_quiz.presentation.ui.theme.BackgroundAqua
import com.myprojects.quizapp.feature_quiz.presentation.ui.theme.BackgroundBlue
import com.myprojects.quizapp.feature_quiz.presentation.ui.theme.BackgroundGreen
import com.myprojects.quizapp.feature_quiz.presentation.ui.theme.BackgroundOrange
import com.myprojects.quizapp.feature_quiz.presentation.ui.theme.BackgroundRed
import com.myprojects.quizapp.feature_quiz.presentation.ui.theme.BackgroundViolet
import com.myprojects.quizapp.feature_quiz.presentation.ui.theme.BackgroundYellow
import com.myprojects.quizapp.feature_quiz.presentation.util.AnswerOptionState
import com.myprojects.quizapp.feature_quiz.presentation.util.QuizEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val quizUseCases: QuizUseCases
) : ViewModel() {

    private val delayBeforeNextQuestionMillis = 3000L

    private val _state = mutableStateOf(QuizState())
    val state: State<QuizState> = _state

    private val _eventFlow = Channel<UIEvent> { }
    val eventFlow = _eventFlow.receiveAsFlow()

    val difficultyOptions = listOf("Easy", "Medium", "Hard")
    private val colorOptions = listOf(
        BackgroundBlue, BackgroundViolet, BackgroundGreen, BackgroundOrange,
        BackgroundYellow, BackgroundRed, BackgroundAqua
    )

    private val _randomColorTop = mutableStateOf(colorOptions.random())
    val randomColorTop: State<Color> = _randomColorTop

    private val _randomColorBottom = mutableStateOf(colorOptions.random())
    val randomColorBottom: State<Color> = _randomColorBottom


    init {
        onLoadCategories()
    }

    private suspend fun runTimer() {
        while (_state.value.timerActive) {
            delay(200L)
            _state.value = _state.value.copy(
                currentTimerMillis = _state.value.currentTimerMillis + 100f
            )
            if (_state.value.currentTimerMillis >= _state.value.maxTimerMillis) {
                _state.value = _state.value.copy(timerActive = false)
                showAnswer()
                delay(delayBeforeNextQuestionMillis)
                if (_state.value.currentQuestionIndex == _state.value.questions.lastIndex) {
                    _eventFlow.send(UIEvent.Navigate(Routes.RESULTS_SCREEN))
                } else {
                    showNextQuestion()
                }
            }
        }
    }

    private var currentTimerJob: Job? = null

    fun onEvent(event: QuizEvent) {
        when (event) {

            is QuizEvent.OnStartQuizClick -> {
                onStartQuiz()
            }

            is QuizEvent.LoadAvailableCategories -> {
                onLoadCategories()
            }

            is QuizEvent.OnCategorySelected -> {
                val selectedCategory = event.categoryName?.let {
                    _state.value.allCategories.firstOrNull {
                        it.name == event.categoryName
                    }
                }
                _state.value = _state.value.copy(selectedCategory = selectedCategory)
            }

            is QuizEvent.OnDifficultyChecked -> {
                val newChecked = _state.value.checkedDifficulties.toMutableList()
                newChecked[event.index] = event.newVal
                _state.value = _state.value.copy(checkedDifficulties = newChecked)
            }

            QuizEvent.ResetQuiz -> {
                currentTimerJob?.cancel()
                _state.value = QuizState(allCategories = _state.value.allCategories)
            }

            is QuizEvent.OnAnswerOptionClick -> {
                val newAnswerOptionStates = _state.value.currentAnswerOptionStates.toMutableList()
                val correctAnswer =
                    _state.value.questions[_state.value.currentQuestionIndex].correctAnswer
                val correctOptionIndex = _state.value.currentAnswers.indexOf(correctAnswer)

                for (index in _state.value.questions.indices) {
                    when (index) {
                        correctOptionIndex -> {
                            newAnswerOptionStates[index] = AnswerOptionState.CORRECT
                        }

                        event.index -> {
                            newAnswerOptionStates[index] = AnswerOptionState.INCORRECT
                        }

                        else -> {
                            newAnswerOptionStates[index] = AnswerOptionState.DISABLED
                        }
                    }
                }

                var newGuessedCount = _state.value.guessedCount
                if (event.index == correctOptionIndex) newGuessedCount++
                viewModelScope.launch {
                    _state.value = _state.value.copy(
                        currentAnswerOptionStates = newAnswerOptionStates,
                        clickedOptionIndex = event.index,
                        timerActive = false,
                        guessedCount = newGuessedCount
                    )
                    delay(delayBeforeNextQuestionMillis)
                    if (_state.value.currentQuestionIndex == _state.value.questions.lastIndex) {
                        _eventFlow.send(UIEvent.Navigate(Routes.RESULTS_SCREEN))
                    } else {
                        showNextQuestion()
                    }
                }
            }
        }
    }

    private fun showAnswer() {
        val newAnswerOptionStates = _state.value.currentAnswerOptionStates.toMutableList()
        val correctAnswer =
            _state.value.questions[_state.value.currentQuestionIndex].correctAnswer
        val correctOptionIndex = _state.value.currentAnswers.indexOf(correctAnswer)

        for (index in _state.value.questions.indices) {
            when (index) {
                correctOptionIndex -> {
                    newAnswerOptionStates[index] = AnswerOptionState.CORRECT
                }

                else -> {
                    newAnswerOptionStates[index] = AnswerOptionState.DISABLED
                }
            }
        }

        _state.value = _state.value.copy(
            currentAnswerOptionStates = newAnswerOptionStates
        )
    }

    private fun showNextQuestion() {
        val newIndex = _state.value.currentQuestionIndex + 1
        val newAnswers = _state.value.questions[newIndex].incorrectAnswers.toMutableList()
        newAnswers.add(_state.value.questions[newIndex].correctAnswer)
        newAnswers.shuffle()
        val newAnswerOptionStates = List(_state.value.questions.size) {
            AnswerOptionState.NOT_CLICKED
        }
        _state.value = _state.value.copy(
            currentTimerMillis = 0f,
            timerActive = true,
            currentQuestionIndex = newIndex,
            currentAnswers = newAnswers,
            currentAnswerOptionStates = newAnswerOptionStates,
            clickedOptionIndex = -1
        )
        currentTimerJob?.cancel()
        currentTimerJob = viewModelScope.launch { runTimer() }
        resetBackgroundColors()
    }

    private fun onLoadCategories() {
        quizUseCases.getAllCategories()
            .onEach { result ->
                when (result) {
                    is Status.Error -> handleError(errorText = result.message)
                    is Status.Loading -> {
                        _state.value = _state.value.copy(
                            isLoading = true
                        )
                    }

                    is Status.Success -> {
                        val categories = result.data?.toMutableList()
                        categories?.add(0, Category(-1, "Any Category"))
                        _state.value = _state.value.copy(
                            allCategories = categories ?: emptyList(),
                            isLoading = false
                        )
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun onStartQuiz() {

        if (_state.value.selectedCategory == null) {
            viewModelScope.launch {
                _eventFlow.send(UIEvent.ShowSnackbar("Select a category"))
            }
            return
        }
        if (_state.value.checkedDifficulties.count { it } == 0) {
            viewModelScope.launch {
                _eventFlow.send(UIEvent.ShowSnackbar("Select at least one difficulty level"))
            }
            return
        }

        _state.value.selectedCategory?.let { selectedCategory ->
            (if (selectedCategory.id == -1) {
                when (_state.value.checkedDifficulties.count { it }) {
                    3 -> {
                        quizUseCases.getQuestionsInAnyCategory(amount = "10")
                    }

                    2 -> {
                        val amount1 = Random.nextInt(1, 10)
                        val amount2 = 10 - amount1
                        val selectedOptions = difficultyOptions.filterIndexed { i, _ ->
                            _state.value.checkedDifficulties[i]
                        }
                        val f1 = quizUseCases.getQuestionsInAnyCategoryWithDifficulty(
                            amount = amount1.toString(),
                            difficulty = selectedOptions[0].lowercase()
                        )
                        val f2 = quizUseCases.getQuestionsInAnyCategoryWithDifficulty(
                            amount = amount2.toString(),
                            difficulty = selectedOptions[1].lowercase()
                        )
                        merge(f1, f2)
                    }

                    1 -> {
                        val selectedOptions = difficultyOptions.filterIndexed { i, _ ->
                            _state.value.checkedDifficulties[i]
                        }
                        quizUseCases.getQuestionsInAnyCategoryWithDifficulty(
                            amount = "10",
                            difficulty = selectedOptions[0].lowercase()
                        )
                    }

                    else -> {
                        viewModelScope.launch {
                            _eventFlow.send(UIEvent.ShowSnackbar(ErrorMessages.UNEXPECTED_ERROR))
                        }
                        return
                    }
                }
            } else {
                when (_state.value.checkedDifficulties.count { it }) {
                    3 -> {
                        quizUseCases.getQuestionsInCategory(
                            amount = "10",
                            categoryId = selectedCategory.id.toString()
                        )
                    }

                    2 -> {
                        val amount1 = Random.nextInt(1, 10)
                        val amount2 = 10 - amount1
                        val selectedOptions = difficultyOptions.filterIndexed { i, _ ->
                            _state.value.checkedDifficulties[i]
                        }
                        val f1 = quizUseCases.getQuestionsInCategoryWithDifficulty(
                            amount = amount1.toString(),
                            categoryId = selectedCategory.id.toString(),
                            difficulty = selectedOptions[0].lowercase()
                        )
                        val f2 = quizUseCases.getQuestionsInCategoryWithDifficulty(
                            amount = amount2.toString(),
                            categoryId = selectedCategory.id.toString(),
                            difficulty = selectedOptions[1].lowercase()
                        )
                        merge(f1, f2)
                    }

                    1 -> {
                        val selectedOptions = difficultyOptions.filterIndexed { i, _ ->
                            _state.value.checkedDifficulties[i]
                        }
                        quizUseCases.getQuestionsInCategoryWithDifficulty(
                            amount = "10",
                            categoryId = selectedCategory.id.toString(),
                            difficulty = selectedOptions[0].lowercase()
                        )
                    }

                    else -> {
                        viewModelScope.launch {
                            _eventFlow.send(UIEvent.ShowSnackbar(ErrorMessages.UNEXPECTED_ERROR))
                        }
                        return
                    }
                }
            }).onEach { result ->
                when (result) {
                    is Status.Error -> handleError(errorText = result.message)

                    is Status.Loading -> {
                        _state.value = _state.value.copy(
                            isLoading = true
                        )
                    }

                    is Status.Success -> {
                        val updatedQuestions = _state.value.questions.toMutableList()
                        if (result.data != null) updatedQuestions.addAll(result.data)
                        val finishLoading = updatedQuestions.size == 10
                        _state.value = _state.value.copy(
                            questions = updatedQuestions,
                            isLoading = finishLoading
                        )
                        if (finishLoading) {
                            val shuffledQuestions = _state.value.questions.shuffled()
                            val initialAnswers =
                                shuffledQuestions[0].incorrectAnswers.toMutableList()
                            initialAnswers.add(shuffledQuestions[0].correctAnswer)
                            initialAnswers.shuffle()
                            val initialAnswerOptionStates = List(_state.value.questions.size) {
                                AnswerOptionState.NOT_CLICKED
                            }
                            _state.value = _state.value.copy(
                                questions = shuffledQuestions,
                                currentAnswers = initialAnswers,
                                currentAnswerOptionStates = initialAnswerOptionStates,
                                timerActive = true
                            )
                            resetBackgroundColors()
                            currentTimerJob?.cancel()
                            currentTimerJob = viewModelScope.launch { runTimer() }
                            _eventFlow.send(UIEvent.Navigate(Routes.GAME_SCREEN))
                        }
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun resetBackgroundColors() {
        var newTopColor = colorOptions.random()
        var newBottomColor = colorOptions.random()
        while (newTopColor == _randomColorTop.value ||
            newBottomColor == _randomColorBottom.value ||
            newTopColor == newBottomColor
        ) {
            newTopColor = colorOptions.random()
            newBottomColor = colorOptions.random()
        }
        _randomColorTop.value = newTopColor
        _randomColorBottom.value = newBottomColor
    }

    private fun handleError(errorText: String?) {
        _state.value = _state.value.copy(
            isLoading = false
        )
        viewModelScope.launch {
            _eventFlow.send(UIEvent.ShowSnackbar(errorText ?: "Unknown error"))
        }
    }
}
