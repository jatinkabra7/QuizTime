package com.jk.quiztime.data.repository

import com.jk.quiztime.data.local.dao.QuestionDao
import com.jk.quiztime.data.local.dao.UserAnswerDao
import com.jk.quiztime.data.mapper.entityToQuestion
import com.jk.quiztime.data.mapper.entityToQuestions
import com.jk.quiztime.data.mapper.toQuestions
import com.jk.quiztime.data.mapper.toQuestionsEntity
import com.jk.quiztime.data.mapper.toUserAnswer
import com.jk.quiztime.data.mapper.toUserAnswerEntity
import com.jk.quiztime.data.remote.QuizRemoteDataSource
import com.jk.quiztime.domain.model.Question
import com.jk.quiztime.domain.model.UserAnswer
import com.jk.quiztime.domain.repository.QuizQuestionRepository
import com.jk.quiztime.domain.util.DataError
import com.jk.quiztime.domain.util.Result

class QuizQuestionRepositoryImpl(
    private val remoteDataSource: QuizRemoteDataSource,
    private val questionDao : QuestionDao,
    private val userAnswerDao: UserAnswerDao
) : QuizQuestionRepository {

    override suspend fun fetchAndSaveQuizQuestions(topicCode : Int): Result<List<Question>, DataError> {

        return when (val result = remoteDataSource.getQuizQuestions(topicCode)) {
            is Result.Success -> {
                val questionsDto = result.data
                userAnswerDao.clearAllAnswers()
                questionDao.clearAllQuestions()
                questionDao.insertQuestions(questionsDto.toQuestionsEntity())
                Result.Success(questionsDto.entityToQuestions())
            }

            is Result.Failure -> {
                result
            }


        }
    }

    override suspend fun getQuizQuestions(): Result<List<Question>, DataError> {
        return try {
            val questionsEntity = questionDao.getAllQuestions()

            if(questionsEntity.isNotEmpty()) {
                Result.Success(questionsEntity.toQuestions())
            }
            else {
                Result.Failure(DataError.Unknown("No questions found"))
            }

        } catch (e : Exception) {
            Result.Failure(DataError.Unknown(e.message))
        }
    }

    override suspend fun saveUserAnswers(userAnswers: List<UserAnswer>): Result<Unit, DataError> {
        return try {
            userAnswerDao.insertUserAnswers(userAnswers.map { it.toUserAnswerEntity() })

            Result.Success(Unit)

        } catch (e : Exception) {
            Result.Failure(DataError.Unknown(e.message))
        }
    }

    override suspend fun getUserAnswers(): Result<List<UserAnswer>, DataError> {
        return try {
            val userAnswers = userAnswerDao.getAllUserAnswers().map { it.toUserAnswer() }

            if(userAnswers.isNotEmpty()) {
                Result.Success(userAnswers)
            }
            else {
                Result.Failure(DataError.Unknown("No answers found"))
            }

        } catch (e : Exception) {
            Result.Failure(DataError.Unknown(e.message))
        }
    }

    override suspend fun getQuizQuestionById(questionId: String): Result<Question, DataError> {
        return try {
            val questionEntity = questionDao.getQuestionById(questionId)

            if(questionEntity != null) {
                Result.Success(questionEntity.entityToQuestion())
            }
            else {
                Result.Failure(DataError.Unknown("No question found"))
            }
        } catch (e : Exception) {
            Result.Failure(DataError.Unknown(e.message))
        }
    }
}