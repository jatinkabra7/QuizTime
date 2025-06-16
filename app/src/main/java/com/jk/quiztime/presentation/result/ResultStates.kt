package com.jk.quiztime.presentation.result

import com.jk.quiztime.domain.model.Question
import com.jk.quiztime.domain.model.UserAnswer

data class ResultStates(
    val scorePercentage: Int = 0,
    val correctAnswerCount : Int = 0,
    val totalQuestion : Int = 0,
    val questionList: List<Question> = emptyList(),
    val userAnswers: List<UserAnswer> = emptyList()
)
