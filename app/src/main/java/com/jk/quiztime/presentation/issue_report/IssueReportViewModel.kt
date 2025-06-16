package com.jk.quiztime.presentation.issue_report

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.jk.quiztime.domain.model.IssueReport
import com.jk.quiztime.domain.repository.IssueReportRepository
import com.jk.quiztime.domain.repository.QuizQuestionRepository
import com.jk.quiztime.domain.util.onFailure
import com.jk.quiztime.domain.util.onSuccess
import com.jk.quiztime.presentation.navigation.Route
import com.jk.quiztime.presentation.result.ResultStates
import com.jk.quiztime.presentation.util.getErrorMessage
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

class IssueReportViewModel(
    private val questionRepository: QuizQuestionRepository,
    savedStateHandle: SavedStateHandle,
    private val issueReportRepository: IssueReportRepository
) : ViewModel() {


    private val _state = MutableStateFlow(IssueReportState())
    val state = _state.asStateFlow()

    private val _event = Channel<IssueReportEvent>()
    val event = _event.receiveAsFlow()

    val questionId = savedStateHandle.toRoute<Route.IssueReportScreen>().questionId

    init {
        getQuestionById(questionId)
    }

    private fun getQuestionById(questionId : String) {
        viewModelScope.launch {
            questionRepository.getQuizQuestionById(questionId)
                .onSuccess { question ->
                    _state.update {
                        it.copy(
                            question = question
                        )
                    }
                }
                .onFailure {
                    _event.send(IssueReportEvent.ShowToast(it.getErrorMessage()))
                }
        }
    }

    private fun submitReport() {

        viewModelScope.launch {

            val report = IssueReport(
                questionId = questionId,
                issueType = state.value.selectedIssueType.text,
                additionalComment = state.value.additionalComment,
                userEmail = state.value.emailForFollowUp,
                timestampMillis = System.currentTimeMillis(),
            )
            issueReportRepository.insertIssueReport(report)
                .onSuccess {
                    _event.send(IssueReportEvent.ShowToast("Report Submitted"))
                    _event.send(IssueReportEvent.NavigateUp)
                }
                .onFailure {
                    _event.send(IssueReportEvent.ShowToast(it.getErrorMessage()))
                }
        }

    }

    fun onAction(action : IssueReportAction) {
        when(action) {
            IssueReportAction.OnCardExpandButtonClick -> {

                _state.update {
                    it.copy(isCardExpanded = !it.isCardExpanded)
                }
            }

            is IssueReportAction.SetAdditionalComment -> {
                _state.update { it.copy(additionalComment = action.additionalComment) }
            }
            is IssueReportAction.SetEmailForFollowUp -> {
                _state.update { it.copy(emailForFollowUp = action.email) }
            }
            is IssueReportAction.SetIssueReportType -> {
                _state.update { it.copy(selectedIssueType = action.issueType) }
            }
            is IssueReportAction.SetOtherIssueText -> {
                _state.update { it.copy(otherIssueText = action.otherIssueText) }
            }

            IssueReportAction.SubmitReportButtonClick -> {
                submitReport()
            }
        }
    }

}