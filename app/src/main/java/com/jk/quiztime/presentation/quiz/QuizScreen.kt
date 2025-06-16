package com.jk.quiztime.presentation.quiz

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.jk.quiztime.domain.model.Question
import com.jk.quiztime.domain.model.UserAnswer
import com.jk.quiztime.presentation.common_component.ErrorScreen
import com.jk.quiztime.presentation.quiz.components.ExitQuizDialog
import com.jk.quiztime.presentation.quiz.components.QuestionNavigation
import com.jk.quiztime.presentation.quiz.components.QuizLoadingScreen
import com.jk.quiztime.presentation.quiz.components.QuizScreenTopBar
import com.jk.quiztime.presentation.quiz.components.SubmitButtons
import com.jk.quiztime.presentation.quiz.components.SubmitDialog
import kotlinx.coroutines.flow.Flow

@Composable
fun QuizScreen(
    modifier: Modifier = Modifier,
    state: QuizStates,
    navigateToDashboardScreen: () -> Unit,
    navigateToResultScreen: () -> Unit,
    onAction: (QuizAction) -> Unit,
    event : Flow<QuizEvent>
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        event.collect {  event ->
            when(event) {
                is QuizEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                }

                QuizEvent.NavigateToDashboardScreen -> {navigateToDashboardScreen()}
                QuizEvent.NavigateToResultScreen -> {navigateToResultScreen()}
            }
        }
    }

    SubmitDialog(
        isOpen = state.isSubmitDialogOpen,
        onDismissRequest = { onAction(QuizAction.SubmitDialogDismiss) },
        onConfirmButtonClick = { onAction(QuizAction.SubmitConfirmButtonClick) }
    )

    ExitQuizDialog(
        isOpen = state.isExitQuizDialogOpen,
        onDismissRequest = { onAction(QuizAction.ExitDialogDismiss) },
        onConfirmButtonClick = { onAction(QuizAction.ExitConfirmButtonClick) }
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {


        QuizScreenTopBar(
            title = state.topBarTitle,
            onExitQuizButton = { onAction(QuizAction.ExitButtonClick) }
        )

        when {
            state.errorMessage != null -> {
                ErrorScreen(
                    onRefreshClick = { onAction(QuizAction.Refresh) },
                    errorMessage = state.errorMessage,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                )
            }

            state.isLoading -> {
                QuizLoadingScreen(
                    loadingMessage = state.loadingMessage,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                )
            }

            state.questions.isEmpty() -> {
                ErrorScreen(
                    onRefreshClick = { onAction(QuizAction.Refresh) },
                    errorMessage = "No quiz question available",
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                )
            }


            else -> {
                QuizScreenContent(
                    state = state,
                    onAction = {onAction(it)}
                )
            }

        }
    }

}

@Composable
private fun QuizScreenContent(
    modifier: Modifier = Modifier,
    state: QuizStates,
    onAction: (QuizAction) -> Unit
) {

    val pagerState = rememberPagerState(
        pageCount = {state.questions.size}
    )

    LaunchedEffect(key1 = pagerState) {
        snapshotFlow { pagerState.settledPage }.collect { pageIndex ->
            onAction(QuizAction.TabButtonClick(pageIndex))
        }
    }

    LaunchedEffect(key1 = state.currentQuestionIndex) {
        pagerState.animateScrollToPage(state.currentQuestionIndex)
    }

    Column(modifier.fillMaxSize()) {

        QuestionNavigation(
            questions = state.questions,
            currentQuestionIndex = state.currentQuestionIndex,
            answers = state.answers,
            onTabSelected = {index ->

                onAction(QuizAction.TabButtonClick(index))

            }
        )


        HorizontalPager(
            state = pagerState,
            modifier = modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {page ->

            QuestionItem(
                modifier = Modifier
                    .fillMaxHeight()
                ,
                questions = state.questions,
                currentQuestionIndex = page,
                answers = state.answers,
                onOptionSelected = { questionId, option ->
                    onAction(QuizAction.OnOptionSelected(questionId,option))
                }
            )
        }

        SubmitButtons(
            modifier = Modifier.fillMaxWidth(),
            isPreviousButtonEnabled = state.currentQuestionIndex != 0,
            isForwardButtonEnabled = state.currentQuestionIndex != state.questions.lastIndex,
            onPreviousButtonClick = { onAction(QuizAction.PrevQuestionButtonClick) },
            onForwardButtonClick = { onAction(QuizAction.ForwardQuestionButtonClick) },
            onSubmitButtonClick = { onAction(QuizAction.SubmitButtonClick) }
        )
    }

}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun QuestionItem(
    modifier: Modifier = Modifier,
    currentQuestionIndex: Int,
    questions: List<Question>,
    answers: List<UserAnswer>,
    onOptionSelected: (String, String) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        val currentQuestion = questions[currentQuestionIndex]

        val selectedAnswer = answers.find { it.questionId == currentQuestion.id }?.selectedOption

        Text(
            text = currentQuestion.question,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .padding(10.dp)
        )

        FlowRow(
            modifier = modifier,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            currentQuestion.allOptions.forEach { option ->
                OptionItem(
                    option = option,
                    isSelected = option == selectedAnswer,
                    onClick = { onOptionSelected(currentQuestion.id, option) },
                    modifier = Modifier
                        .widthIn(min = 400.dp, max = 400.dp)
                        .padding(10.dp)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.primary,
                            shape = MaterialTheme.shapes.small
                        )
                )
            }
        }


    }
}

@Composable
private fun OptionItem(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    option: String,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer
            else MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = isSelected,
                onClick = { onClick() }
            )

            Text(
                modifier = Modifier.padding(5.dp),
                text = option,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}