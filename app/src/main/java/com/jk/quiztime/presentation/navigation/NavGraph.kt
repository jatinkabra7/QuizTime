package com.jk.quiztime.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import androidx.navigation.ui.navigateUp
import com.jk.quiztime.domain.model.Question
import com.jk.quiztime.domain.model.QuizTopic
import com.jk.quiztime.domain.model.UserAnswer
import com.jk.quiztime.presentation.dashboard.DashboardScreen
import com.jk.quiztime.presentation.dashboard.DashboardStates
import com.jk.quiztime.presentation.dashboard.DashboardViewModel
import com.jk.quiztime.presentation.issue_report.IssueReportScreen
import com.jk.quiztime.presentation.issue_report.IssueReportState
import com.jk.quiztime.presentation.issue_report.IssueReportViewModel
import com.jk.quiztime.presentation.quiz.QuizScreen
import com.jk.quiztime.presentation.quiz.QuizStates
import com.jk.quiztime.presentation.quiz.QuizViewModel
import com.jk.quiztime.presentation.result.ResultScreen
import com.jk.quiztime.presentation.result.ResultStates
import com.jk.quiztime.presentation.result.ResultViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues
) {


    val allOptions = listOf("option A", "option B", "option C", "option D")

    val questions = List(size = 20) { index ->
        Question(
            id = "$index",
            topicCode = 0,
            question = "This is the question?",
            allOptions = allOptions,
            correctAnswer = allOptions[0],
            explanation = ""
        )
    }

    val answers = listOf(
        UserAnswer(questionId = "1", selectedOption = "")
    )

    NavHost(
        modifier = Modifier.padding(paddingValues),
        navController = navController,
        startDestination = Route.DashboardScreen
    ) {

        composable<Route.DashboardScreen> {

            val viewModel = koinViewModel<DashboardViewModel>()

            val state = viewModel.state.collectAsStateWithLifecycle().value

            DashboardScreen(
                states = state,
                onTopicCardClick = { topicCode ->

                    navController.navigate(Route.QuizScreen(topicCode = topicCode))

                }
            )
        }

        composable<Route.QuizScreen> {

            val viewModel = koinViewModel<QuizViewModel>()

            val state = viewModel.state.collectAsState().value

            QuizScreen(
                state = state,
                onAction = {viewModel.onAction(it)},
                navigateToResultScreen = {
                    navController.navigate(Route.ResultScreen) {
                        popUpTo<Route.QuizScreen> {inclusive = true}
                    }
                },
                navigateToDashboardScreen = {
                    navController.navigateUp()
                },
                event = viewModel.event
            )
        }

        composable<Route.ResultScreen> {

            val viewModel = koinViewModel<ResultViewModel>()

            val state = viewModel.state.collectAsStateWithLifecycle().value

            ResultScreen(
                state = state,
                onReportButtonClick = { questionId ->
                    navController.navigate(Route.IssueReportScreen(questionId))
                },
                onStartNewQuiz = {
                    navController.navigate(Route.DashboardScreen) {
                        popUpTo<Route.ResultScreen> { inclusive = true }
                    }
                },
                event = viewModel.event
            )
        }

        composable<Route.IssueReportScreen> {

            val viewModel = koinViewModel<IssueReportViewModel>()

            val state = viewModel.state.collectAsStateWithLifecycle().value

            val event = viewModel.event

            IssueReportScreen(
                state = state,
                onBackButtonClick = {
                    navController.navigateUp()
                },
                onAction = { viewModel.onAction(it) },
                event = event
            )
        }

    }
}