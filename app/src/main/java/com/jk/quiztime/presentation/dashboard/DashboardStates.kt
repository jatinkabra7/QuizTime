package com.jk.quiztime.presentation.dashboard

import com.jk.quiztime.domain.model.QuizTopic

data class DashboardStates(
    val username : String = "Username",
    val questionsAttempted : Int = 0,
    val questionsCorrect : Int = 0,
    val quizTopics : List<QuizTopic> = emptyList(),
    val isTopicLoading : Boolean = false,
    val errorMessage : String? = null,
    val isNameEditDialogOpen : Boolean = false,
    val usernameTextFieldValue : String = "",
    val usernameError : String? = null
)