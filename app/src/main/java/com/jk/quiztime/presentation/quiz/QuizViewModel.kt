package com.jk.quiztime.presentation.quiz

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.jk.quiztime.domain.model.UserAnswer
import com.jk.quiztime.domain.repository.QuizQuestionRepository
import com.jk.quiztime.domain.repository.QuizTopicRepository
import com.jk.quiztime.domain.repository.UserPreferencesRepository
import com.jk.quiztime.domain.util.onFailure
import com.jk.quiztime.domain.util.onSuccess
import com.jk.quiztime.presentation.navigation.Route
import com.jk.quiztime.presentation.util.getErrorMessage
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QuizViewModel(
    private val repository: QuizQuestionRepository,
    savedStateHandle: SavedStateHandle,
    private val topicRepository: QuizTopicRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _state = MutableStateFlow(QuizStates())
    val state = _state.asStateFlow()

    private val _event = Channel<QuizEvent>()
    val event = _event.receiveAsFlow()

    private val topicCode = savedStateHandle.toRoute<Route.QuizScreen>().topicCode

    init {
        setupQuiz()
    }

    private suspend fun getQuizQuestions(topicCode: Int) {
        repository.fetchAndSaveQuizQuestions(topicCode)
            .onSuccess {questions ->
                _state.update {
                    it.copy(questions = questions, errorMessage = null, isLoading = false)
                }
            }
            .onFailure {error ->

                _state.update {
                    it.copy(
                        questions = emptyList(),
                        errorMessage = error.getErrorMessage(),
                        isLoading = false
                    )
                }

            }
    }

    private suspend fun getQuizTopicName(topicCode : Int) {
        topicRepository.getQuizTopicByCode(topicCode)
            .onSuccess {topic ->
                _state.update {
                    it.copy(topBarTitle = topic.name + " Quiz")
                }
            }
            .onFailure {error ->
                _event.send(QuizEvent.ShowToast(error.getErrorMessage()))
            }
    }

    private fun setupQuiz() {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true, loadingMessage = "Loading Quiz...")
            }

            getQuizTopicName(topicCode)
            getQuizQuestions(topicCode)

            _state.update {
                it.copy(isLoading = false, loadingMessage = null)
            }
        }
    }

    private fun submitQuiz() {
        viewModelScope.launch {

            _state.update { it.copy(isLoading = true, loadingMessage = "Submitting Quiz...") }

            saveUserAnswers()
            updateScore()

            _state.update { it.copy(isLoading = false, loadingMessage = null) }

            _event.send(QuizEvent.NavigateToResultScreen)
        }
    }

    private suspend fun saveUserAnswers() {
        repository.saveUserAnswers(state.value.answers)
            .onFailure {
                _event.send(QuizEvent.ShowToast(it.getErrorMessage()))
            }
    }

    private suspend fun updateScore() {
        val quizQuestions = state.value.questions
        val userAnswers = state.value.answers

        val correctAnswersCount = userAnswers.count { answer ->
            val question = quizQuestions.find { it.id == answer.questionId }
            question?.correctAnswer == answer.selectedOption
        }

        val previousAttempted = userPreferencesRepository.getQuestionsAttempted().first()
        val previousCorrect = userPreferencesRepository.getCorrectAnswers().first()

        val totalAttempted = previousAttempted + userAnswers.size
        val totalCorrect = previousCorrect + correctAnswersCount

        userPreferencesRepository.saveScore(
            questionsAttempted = totalAttempted,
            correctAnswers = totalCorrect
        )
    }

    fun onAction(action: QuizAction) {
        when(action) {
            QuizAction.ForwardQuestionButtonClick -> {

                val newIndex = (state.value.currentQuestionIndex + 1).coerceAtMost(state.value.questions.lastIndex)

                _state.update {
                    it.copy(currentQuestionIndex = newIndex)
                }
            }
            QuizAction.PrevQuestionButtonClick -> {
                val newIndex = (state.value.currentQuestionIndex - 1).coerceAtLeast(0)

                _state.update {
                    it.copy(currentQuestionIndex = newIndex)
                }
            }

            is QuizAction.TabButtonClick -> {
                val newIndex = action.index

                _state.update {
                    it.copy(currentQuestionIndex = newIndex)
                }
            }

            is QuizAction.OnOptionSelected -> {
                val newAnswer = UserAnswer(action.questionId, action.option)
                val currentAnswers = state.value.answers.toMutableList()

                val existingAnswerIndex = currentAnswers.indexOfFirst { it.questionId == action.questionId }

                if(existingAnswerIndex != -1) {

                    currentAnswers[existingAnswerIndex] = newAnswer
                }
                else {
                    currentAnswers.add(newAnswer)
                }


                _state.update {
                    it.copy(answers = currentAnswers)
                }
            }

            QuizAction.ExitButtonClick -> {
                _state.update {
                    it.copy(isExitQuizDialogOpen = true)
                }
            }
            QuizAction.ExitConfirmButtonClick -> {
                _state.update {
                    it.copy(isExitQuizDialogOpen = false)
                }
                _event.trySend(QuizEvent.NavigateToDashboardScreen)
            }
            QuizAction.ExitDialogDismiss -> {
                _state.update {
                    it.copy(isExitQuizDialogOpen = false)
                }
            }
            QuizAction.SubmitButtonClick -> {
                _state.update {
                    it.copy(isSubmitDialogOpen = true)
                }
            }
            QuizAction.SubmitConfirmButtonClick -> {
                _state.update { it.copy(isSubmitDialogOpen = false) }
                submitQuiz()
                _event.trySend(QuizEvent.NavigateToResultScreen)
            }
            QuizAction.SubmitDialogDismiss -> {
                _state.update { it.copy(isSubmitDialogOpen = false) }
            }

            QuizAction.Refresh -> {
                setupQuiz()
            }
        }

    }
}