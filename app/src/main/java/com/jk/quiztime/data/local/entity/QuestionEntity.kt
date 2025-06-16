package com.jk.quiztime.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jk.quiztime.data.util.Constants

@Entity(tableName = Constants.QUIZ_QUESTIONS_TABLE_NAME)
data class QuestionEntity(
    @PrimaryKey
    val id : String,
    val topicCode : Int,
    val question : String,
    val incorrectAnswers : List<String>,
    val correctAnswer : String,
    val explanation : String
)