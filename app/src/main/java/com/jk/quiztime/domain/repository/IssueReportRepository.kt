package com.jk.quiztime.domain.repository

import com.jk.quiztime.domain.model.IssueReport
import com.jk.quiztime.domain.util.DataError
import com.jk.quiztime.domain.util.Result

interface IssueReportRepository {
    suspend fun insertIssueReport(report: IssueReport) : Result<Unit, DataError>
}