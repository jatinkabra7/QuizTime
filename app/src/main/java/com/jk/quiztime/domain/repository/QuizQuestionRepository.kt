package com.jk.quiztime.domain.repository

import com.jk.quiztime.domain.model.Question
import com.jk.quiztime.domain.model.UserAnswer
import com.jk.quiztime.domain.util.DataError
import com.jk.quiztime.domain.util.Result

interface QuizQuestionRepository {
    suspend fun fetchAndSaveQuizQuestions(topicCode : Int) : Result<List<Question>, DataError>

    suspend fun getQuizQuestions() : Result<List<Question>, DataError>

    suspend fun getQuizQuestionById(questionId : String) : Result<Question, DataError>

    suspend fun saveUserAnswers(userAnswers : List<UserAnswer>) : Result<Unit, DataError>

    suspend fun getUserAnswers() : Result<List<UserAnswer>, DataError>
}