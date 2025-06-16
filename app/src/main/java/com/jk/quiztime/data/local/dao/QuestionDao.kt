package com.jk.quiztime.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.jk.quiztime.data.local.entity.QuestionEntity

@Dao
interface QuestionDao {

    @Query("SELECT * FROM quiz_questions")
    suspend fun getAllQuestions() : List<QuestionEntity>

    @Upsert
    suspend fun insertQuestions(questions : List<QuestionEntity>)

    @Query("DELETE FROM quiz_questions")
    suspend fun clearAllQuestions()

    @Query("SELECT * FROM quiz_questions WHERE id = :questionId")
    suspend fun getQuestionById(questionId : String) : QuestionEntity?
}