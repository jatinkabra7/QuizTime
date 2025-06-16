package com.jk.quiztime.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jk.quiztime.data.repository.QuizTopicRepositoryImpl
import com.jk.quiztime.domain.repository.QuizTopicRepository
import com.jk.quiztime.domain.repository.UserPreferencesRepository
import com.jk.quiztime.domain.util.DataError
import com.jk.quiztime.domain.util.onFailure
import com.jk.quiztime.domain.util.onSuccess
import com.jk.quiztime.presentation.util.getErrorMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DashboardViewModel(
    val repository : QuizTopicRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {


    private val _state = MutableStateFlow(DashboardStates())
    val state = combine(
        _state,
        userPreferencesRepository.getQuestionsAttempted(),
        userPreferencesRepository.getCorrectAnswers()
    ) {state, questionsAttempted, correctAnswers ->

        state.copy(questionsAttempted = questionsAttempted, questionsCorrect = correctAnswers)

    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = _state.value
    )

    init {
        getQuizTopics()
    }

    private fun getQuizTopics() {
        viewModelScope.launch {

            _state.update { it.copy(isTopicLoading = true) }

            repository.getQuizTopics()
                .onSuccess {topics ->
                    _state.update { it.copy(quizTopics = topics, errorMessage = null, isTopicLoading = false) }
                }
                .onFailure {error ->

                    _state.update {
                        it.copy(
                            quizTopics = emptyList(),
                            errorMessage = error.getErrorMessage(),
                            isTopicLoading = false
                        )
                    }
                }

        }
    }
}