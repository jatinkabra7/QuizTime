package com.jk.quiztime.data.remote

import com.jk.quiztime.data.remote.dto.IssueReportDto
import com.jk.quiztime.data.remote.dto.QuestionDto
import com.jk.quiztime.data.remote.dto.QuizTopicDto
import com.jk.quiztime.domain.util.DataError
import com.jk.quiztime.domain.util.Result

interface QuizRemoteDataSource {

    suspend fun getQuizTopics() : Result<List<QuizTopicDto>, DataError>

    suspend fun getQuizQuestions(topicCode : Int) : Result<List<QuestionDto>, DataError>

    suspend fun insertIssueReport(report : IssueReportDto) : Result<Unit, DataError>
}