package com.jk.quiztime.presentation.quiz

import com.jk.quiztime.domain.model.Question
import com.jk.quiztime.domain.model.UserAnswer

data class QuizStates (
    val questions: List<Question> = emptyList(),
    val answers : List<UserAnswer> = emptyList(),
    val currentQuestionIndex : Int = 0,
    val errorMessage : String? = null,
    val loadingMessage : String? = null,
    val isLoading : Boolean = false,
    val isSubmitDialogOpen : Boolean = false,
    val isExitQuizDialogOpen : Boolean = false,
    val topBarTitle : String = "New Quiz"
)