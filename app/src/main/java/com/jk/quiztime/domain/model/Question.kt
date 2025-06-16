package com.jk.quiztime.domain.model

data class Question(
    val id : String,
    val topicCode : Int,
    val question : String,
    val allOptions : List<String>,
    val correctAnswer : String,
    val explanation : String
)
