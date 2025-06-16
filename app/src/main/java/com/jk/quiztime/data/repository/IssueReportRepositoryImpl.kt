package com.jk.quiztime.data.repository

import com.jk.quiztime.data.mapper.toIssueReportDto
import com.jk.quiztime.data.remote.QuizRemoteDataSource
import com.jk.quiztime.domain.model.IssueReport
import com.jk.quiztime.domain.repository.IssueReportRepository
import com.jk.quiztime.domain.util.DataError
import com.jk.quiztime.domain.util.Result

class IssueReportRepositoryImpl(
    private val remoteDataSource: QuizRemoteDataSource
) : IssueReportRepository{
    override suspend fun insertIssueReport(report: IssueReport): Result<Unit, DataError> {
        return remoteDataSource.insertIssueReport(report.toIssueReportDto())
    }
}