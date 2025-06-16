package com.jk.quiztime.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jk.quiztime.data.util.Constants

@Entity(tableName = Constants.USER_ANSWERS_TABLE_NAME)
data class UserAnswerEntity(
    @PrimaryKey
    val questionId : String,
    val selectedOption : String
)
