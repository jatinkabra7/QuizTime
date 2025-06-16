package com.jk.quiztime.data.repository

import com.jk.quiztime.data.local.dao.QuizTopicDao
import com.jk.quiztime.data.mapper.entityToQuizTopic
import com.jk.quiztime.data.mapper.entityToQuizTopics
import com.jk.quiztime.data.mapper.toQuizTopics
import com.jk.quiztime.data.mapper.toQuizTopicsEntity
import com.jk.quiztime.data.remote.QuizRemoteDataSource
import com.jk.quiztime.domain.model.QuizTopic
import com.jk.quiztime.domain.repository.QuizTopicRepository
import com.jk.quiztime.domain.util.DataError
import com.jk.quiztime.domain.util.Result



class QuizTopicRepositoryImpl(
    private val remoteDataSource: QuizRemoteDataSource,
    private val topicDao: QuizTopicDao
) : QuizTopicRepository {

    override suspend fun getQuizTopics(): Result<List<QuizTopic>, DataError> {
        return when (val result = remoteDataSource.getQuizTopics()) {
            is Result.Success -> {
                val quizTopicsDto = result.data
                topicDao.clearAllQuizTopics()
                topicDao.insertQuizTopics(quizTopicsDto.toQuizTopicsEntity())
                Result.Success(quizTopicsDto.toQuizTopics())
            }

            is Result.Failure -> {
                val cachedTopic = topicDao.getAllQuizTopics()
                if (cachedTopic.isNotEmpty()) {
                    Result.Success(cachedTopic.entityToQuizTopics())
                } else {
                    result
                }
            }


        }
    }

    override suspend fun getQuizTopicByCode(topicCode: Int): Result<QuizTopic, DataError> {
        return try {
            val topicEntity = topicDao.getQuizTopicByCode(topicCode)
            if (topicEntity != null) {
                Result.Success(topicEntity.entityToQuizTopic())
            } else {
                Result.Failure(DataError.Unknown(errorMessage = "Quiz Topic not found."))
            }
        } catch (e: Exception) {
            Result.Failure(DataError.Unknown(e.message))
        }
    }
}
