package com.jk.quiztime.domain.repository

import com.jk.quiztime.domain.model.QuizTopic
import com.jk.quiztime.domain.util.DataError
import com.jk.quiztime.domain.util.Result

interface QuizTopicRepository {

    suspend fun getQuizTopics() : Result<List<QuizTopic>, DataError>
    suspend fun getQuizTopicByCode(topicCode : Int) : Result<QuizTopic, DataError>
}