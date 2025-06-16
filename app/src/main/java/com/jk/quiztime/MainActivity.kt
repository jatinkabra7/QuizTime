package com.jk.quiztime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.jk.quiztime.domain.model.Question
import com.jk.quiztime.domain.model.QuizTopic
import com.jk.quiztime.domain.model.UserAnswer
import com.jk.quiztime.presentation.dashboard.DashboardScreen
import com.jk.quiztime.presentation.dashboard.DashboardStates
import com.jk.quiztime.presentation.issue_report.IssueReportScreen
import com.jk.quiztime.presentation.issue_report.IssueReportState
import com.jk.quiztime.presentation.navigation.NavGraph
import com.jk.quiztime.presentation.quiz.QuizScreen
import com.jk.quiztime.presentation.quiz.QuizStates
import com.jk.quiztime.presentation.result.ResultScreen
import com.jk.quiztime.presentation.result.ResultStates
import com.jk.quiztime.presentation.theme.QuizTimeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            QuizTimeTheme {

                Scaffold {paddingValues ->

                    val navController = rememberNavController()
                    NavGraph(
                        navController = navController,
                        paddingValues = paddingValues
                    )
                }
            }
        }
    }
}
