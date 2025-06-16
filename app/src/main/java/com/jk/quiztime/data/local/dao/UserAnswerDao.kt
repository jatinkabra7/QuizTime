package com.jk.quiztime.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.jk.quiztime.data.local.entity.UserAnswerEntity

@Dao
interface UserAnswerDao {

    @Query("SELECT * FROM user_answers")
    suspend fun getAllUserAnswers() : List<UserAnswerEntity>

    @Upsert
    suspend fun insertUserAnswers(answers : List<UserAnswerEntity>)

    @Query("DELETE FROM user_answers")
    suspend fun clearAllAnswers()
}