package com.jk.quiztime.presentation.result

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.material3.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jk.quiztime.R
import com.jk.quiztime.domain.model.Question
import com.jk.quiztime.domain.model.UserAnswer
import com.jk.quiztime.presentation.theme.customGreen
import kotlinx.coroutines.flow.Flow

@Composable
fun ResultScreen(
    modifier: Modifier = Modifier,
    state: ResultStates,
    onReportButtonClick: (String) -> Unit,
    onStartNewQuiz : () -> Unit,
    event : Flow<ResultEvent>
) {

    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        event.collect {
            when(it) {
                is ResultEvent.ShowToast -> {
                    Toast.makeText(context,it.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        LazyColumn(
            modifier = Modifier
                .weight(1f),
            contentPadding = PaddingValues(10.dp)
        ) {
            item {
                ScoreCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 80.dp, horizontal = 10.dp),
                    scorePercentage = state.scorePercentage,
                    correctAnswerCount = state.correctAnswerCount,
                    totalQuestion = state.totalQuestion
                )
            }

            item {
                Text(
                    text = "Summary",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.tertiary,
                    textDecoration = TextDecoration.Underline,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 20.dp)
                )
            }

            items(state.questionList) { question ->

                val userSelectedAnswer = state.userAnswers.find {
                    it.questionId == question.id
                }?.selectedOption

                QuestionItem(
                    question = question,
                    userSelectedAnswer = userSelectedAnswer,
                    onReportButtonClick = {onReportButtonClick(question.id)}
                )

                Spacer(Modifier.height(15.dp))

                HorizontalDivider(
                    thickness = 2.dp
                )

                Spacer(Modifier.height(15.dp))
            }
        }

        Button(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 10.dp),
            onClick = {onStartNewQuiz()}
        ) {
            Text(
                text = "Start New Quiz",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.surface
            )
        }
    }

}

@Composable
private fun ScoreCard(
    modifier: Modifier = Modifier,
    scorePercentage: Int,
    correctAnswerCount: Int,
    totalQuestion: Int
) {

    val resultText =
        when (scorePercentage) {
            in 71..100 -> "Congratulations!\n Great performance."
            in 41..70 -> "You did well, \n but try harder next time."
            else -> "You may have struggled this time,\nbut mistakes are a part of learning."
        }

    val image =
        when (scorePercentage) {
            in 71..100 -> R.drawable.ic_laugh
            in 41..70 -> R.drawable.ic_smiley
            else -> R.drawable.ic_sad
        }

    Card(
        modifier = modifier
    ) {

        Image(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(20.dp)
                .size(100.dp),
            painter = painterResource(image),
            contentDescription = null
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(10.dp),
            text = "Score: $correctAnswerCount/$totalQuestion",
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(10.dp),
            text = resultText,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun QuestionItem(
    modifier: Modifier = Modifier,
    question: Question,
    userSelectedAnswer: String?,
    onReportButtonClick : () -> Unit
) {

    Column(
        modifier = modifier
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Q: " + question.question,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )

            IconButton(onClick = {onReportButtonClick()}) {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = "Report",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }




        question.allOptions.forEachIndexed { index, option ->
            val optionColor =
                when (option) {
                    question.correctAnswer -> customGreen
                    userSelectedAnswer -> MaterialTheme.colorScheme.error
                    else -> MaterialTheme.colorScheme.onBackground
                }
            val letter =
                when (index) {
                    0 -> "(A)  "
                    1 -> "(B)  "
                    2 -> "(C)  "
                    else -> "(D)  "
                }

            Text(
                text = letter + option,
                style = MaterialTheme.typography.titleLarge,
                color = optionColor,
                modifier = Modifier
                    .padding(vertical = 10.dp)
            )
        }

        Text(
            text = "Explanation: " + question.explanation,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.secondary

        )
    }

}