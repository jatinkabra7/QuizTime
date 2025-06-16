package com.jk.quiztime.presentation.quiz

sealed interface QuizAction {
    data object PrevQuestionButtonClick : QuizAction
    data object ForwardQuestionButtonClick : QuizAction
    data class TabButtonClick(val index : Int) : QuizAction
    data class OnOptionSelected(val questionId : String, val option : String) : QuizAction
    data object SubmitButtonClick : QuizAction
    data object SubmitDialogDismiss : QuizAction
    data object SubmitConfirmButtonClick : QuizAction
    data object ExitButtonClick : QuizAction
    data object ExitDialogDismiss : QuizAction
    data object ExitConfirmButtonClick : QuizAction
    data object Refresh : QuizAction
}