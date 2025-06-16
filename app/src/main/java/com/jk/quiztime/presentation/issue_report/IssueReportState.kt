package com.jk.quiztime.presentation.issue_report

import com.jk.quiztime.domain.model.Question

data class IssueReportState(
    val question : Question? = null,
    val isCardExpanded : Boolean = false,
    val selectedIssueType: IssueType = IssueType.OTHER,
    val otherIssueText : String = "",
    val additionalComment : String = "",
    val emailForFollowUp : String = ""
)

enum class IssueType(val text : String) {
    INCORRECT_ANSWER(text = "Incorrect Answer"),
    UNCLEAR_QUESTION(text = "Unclear Question"),
    TYPO_OR_GRAMMAR(text = "Typo or Grammar Mistake"),
    OTHER(text = "Other")
}
