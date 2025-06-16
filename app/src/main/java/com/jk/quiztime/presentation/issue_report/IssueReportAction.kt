package com.jk.quiztime.presentation.issue_report

sealed interface IssueReportAction {
    data object OnCardExpandButtonClick : IssueReportAction
    data class SetIssueReportType(val issueType: IssueType) : IssueReportAction
    data class SetOtherIssueText(val otherIssueText : String) : IssueReportAction
    data class SetAdditionalComment(val additionalComment: String) : IssueReportAction
    data class SetEmailForFollowUp(val email : String) : IssueReportAction
    data object SubmitReportButtonClick : IssueReportAction
}
