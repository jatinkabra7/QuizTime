package com.jk.quiztime.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class QuestionDto(
    val id : String,
    val topicCode : Int,
    val question : String,
    val incorrectAnswers : List<String>,
    val correctAnswer : String,
    val explanation : String
)
