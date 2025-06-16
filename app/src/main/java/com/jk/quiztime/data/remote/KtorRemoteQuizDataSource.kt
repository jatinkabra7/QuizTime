package com.jk.quiztime.data.remote

import com.jk.quiztime.data.remote.dto.IssueReportDto
import com.jk.quiztime.data.remote.dto.QuestionDto
import com.jk.quiztime.data.remote.dto.QuizTopicDto
import com.jk.quiztime.data.util.Constants
import com.jk.quiztime.domain.model.Question
import com.jk.quiztime.domain.util.DataError
import com.jk.quiztime.domain.util.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.serialization.JsonConvertException
import io.ktor.util.network.UnresolvedAddressException
import java.net.UnknownHostException

class KtorRemoteQuizDataSource(
    private val httpClient : HttpClient
) : QuizRemoteDataSource {

    override suspend fun getQuizTopics() : Result<List<QuizTopicDto>, DataError> {
        return safeCall<List<QuizTopicDto>> {
            httpClient.get(urlString = "${Constants.BASE_URL}/quiz/topics")
        }
    }

    override suspend fun getQuizQuestions(topicCode : Int) : Result<List<QuestionDto>, DataError> {
        return safeCall<List<QuestionDto>> {
            httpClient.get(urlString = "${Constants.BASE_URL}/quiz/questions/random") {
                parameter("topicCode", topicCode)
            }
        }
    }

    override suspend fun insertIssueReport(report: IssueReportDto): Result<Unit, DataError> {
        return safeCall<Unit> {
            httpClient.post(urlString = "${Constants.BASE_URL}/report/issues") {
                setBody(report)
            }
        }
    }
}