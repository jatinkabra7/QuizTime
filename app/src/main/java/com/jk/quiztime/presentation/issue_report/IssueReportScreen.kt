package com.jk.quiztime.presentation.issue_report

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jk.quiztime.domain.model.Question
import com.jk.quiztime.presentation.issue_report.components.IssueReportScreenTopBar
import com.jk.quiztime.presentation.issue_report.components.QuestionCard
import kotlinx.coroutines.flow.Flow

@Composable
fun IssueReportScreen(
    modifier: Modifier = Modifier,
    state: IssueReportState,
    onBackButtonClick : () -> Unit,
    event : Flow<IssueReportEvent>,
    onAction: (IssueReportAction) -> Unit
) {

    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        event.collect {
            when(it) {
                is IssueReportEvent.ShowToast -> {
                    Toast.makeText(context,it.message, Toast.LENGTH_LONG).show()
                }

                IssueReportEvent.NavigateUp -> {
                    onBackButtonClick()
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        IssueReportScreenTopBar(
            onBackButtonClick = {onBackButtonClick()},
            title = "Report an issue"
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {

            QuestionCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                question = state.question,
                isCardExpanded = state.isCardExpanded,
                onDropdownButtonClick = {onAction(IssueReportAction.OnCardExpandButtonClick)}
            )

            IssueTypeSection(
                modifier = Modifier
                    .padding(10.dp),
                selectedIssueType = state.selectedIssueType,
                otherIssueText = state.otherIssueText,
                onOtherIssueTextChanged = {onAction(IssueReportAction.SetOtherIssueText(it))},
                onIssueTypeClick = {onAction(IssueReportAction.SetIssueReportType(it))}
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .height(200.dp),
                value = state.additionalComment,
                onValueChange = {onAction(IssueReportAction.SetAdditionalComment(it))},
                label = { Text(text = "Additional Comments") },
                supportingText = {
                    Text(
                        text = "Describe the issue in more detail (Optional)"
                    )
                }
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                value = state.emailForFollowUp,
                onValueChange = {onAction(IssueReportAction.SetEmailForFollowUp(it))},
                label = { Text(text = "Email for follow up") },
                singleLine = true,
                supportingText = { Text(text = "(Optional)") }
            )
        }


        Button(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(10.dp),
            onClick = {onAction(IssueReportAction.SubmitReportButtonClick)}
        ) {
            Text(
                text = "Submit Report",
                modifier = Modifier.padding(horizontal = 10.dp),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun IssueTypeSection(
    modifier: Modifier = Modifier,
    selectedIssueType: IssueType,
    otherIssueText: String,
    onOtherIssueTextChanged: (String) -> Unit,
    onIssueTypeClick: (IssueType) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "ISSUE TYPE",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        FlowRow {

            IssueType.entries.forEach {
                Row(
                    modifier = Modifier
                        .widthIn(min = 250.dp)
                        .clickable { onIssueTypeClick(it) },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = it == selectedIssueType,
                        onClick = { onIssueTypeClick(it) }
                    )

                    if (it != IssueType.OTHER) {

                        Text(
                            text = it.text,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    } else {
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = otherIssueText,
                            onValueChange = { newValue -> onOtherIssueTextChanged(newValue) },
                            label = { Text(text = it.text) },
                            singleLine = true,
                            enabled = it == selectedIssueType
                        )
                    }
                }
            }
        }
    }
}