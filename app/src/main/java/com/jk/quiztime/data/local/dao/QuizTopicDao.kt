package com.jk.quiztime.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.jk.quiztime.data.local.entity.QuizTopicEntity
import com.jk.quiztime.data.remote.dto.QuizTopicDto

@Dao
interface QuizTopicDao {

    @Query("SELECT * FROM quiz_topics")
    suspend fun getAllQuizTopics() : List<QuizTopicEntity>

    @Upsert
    suspend fun insertQuizTopics(topics : List<QuizTopicEntity>)

    @Query("DELETE FROM quiz_topics")
    suspend fun clearAllQuizTopics()

    @Query("SELECT * FROM quiz_topics WHERE code = :topicCode")
    suspend fun getQuizTopicByCode(topicCode : Int) : QuizTopicEntity?
}