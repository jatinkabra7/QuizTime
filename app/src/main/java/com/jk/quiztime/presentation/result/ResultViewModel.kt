package com.jk.quiztime.presentation.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jk.quiztime.domain.repository.QuizQuestionRepository
import com.jk.quiztime.domain.util.onFailure
import com.jk.quiztime.domain.util.onSuccess
import com.jk.quiztime.presentation.util.getErrorMessage
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ResultViewModel(
    private val questionRepository: QuizQuestionRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ResultStates())
    val state = _state.asStateFlow()

    private val _event = Channel<ResultEvent>()
    val event = _event.receiveAsFlow()

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            getAllQuestions()
            getUserAnswers()
            updateResult()
        }
    }

    private suspend fun getAllQuestions() {
        questionRepository.getQuizQuestions()
            .onSuccess {questions ->

                _state.update { it.copy(
                    questionList = questions,
                    totalQuestion = questions.size
                ) }
            }
            .onFailure {error ->
                _event.send(ResultEvent.ShowToast(error.getErrorMessage()))
            }
    }

    private suspend fun getUserAnswers() {

        questionRepository.getUserAnswers()
            .onSuccess {answers ->
                _state.update {
                    it.copy(
                        userAnswers = answers
                    )
                }
            }
            .onFailure { error ->
                _event.send(ResultEvent.ShowToast(error.getErrorMessage()))
            }
    }

    private fun updateResult() {

        val questions = state.value.questionList
        val answers = state.value.userAnswers

        val correctAnswersCount = answers.count {answer ->

            val question = questions.find {it.id == answer.questionId}

            question?.correctAnswer == answer.selectedOption
        }

        val scorePercentage = if(questions.isNotEmpty()) {
            (correctAnswersCount*100) / questions.size
        } else 0


        _state.update {
            it.copy(
                correctAnswerCount = correctAnswersCount,
                scorePercentage = scorePercentage,
            )
        }
    }
}