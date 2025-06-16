package com.jk.quiztime.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jk.quiztime.data.util.Constants

@Entity(tableName = Constants.QUIZ_TOPICS_TABLE_NAME)
data class QuizTopicEntity(
    @PrimaryKey
    val id : String,
    val name : String,
    val imageUrl : String,
    val code : Int
)
