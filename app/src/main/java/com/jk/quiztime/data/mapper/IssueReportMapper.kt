package com.jk.quiztime.data.mapper

import com.jk.quiztime.data.remote.dto.IssueReportDto
import com.jk.quiztime.data.util.toFormattedDateTimeString
import com.jk.quiztime.domain.model.IssueReport

fun IssueReport.toIssueReportDto() = IssueReportDto(
    questionId = questionId,
    issueType = issueType,
    additionalComment = additionalComment,
    userEmail = userEmail,
    timestamp = timestampMillis.toFormattedDateTimeString()
)