package com.jk.quiztime.presentation.quiz.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jk.quiztime.domain.model.Question
import com.jk.quiztime.domain.model.UserAnswer

@Composable
fun QuestionNavigation(
    modifier: Modifier = Modifier,
    questions: List<Question>,
    currentQuestionIndex : Int,
    answers : List<UserAnswer>,
    onTabSelected : (Int) -> Unit
) {
    ScrollableTabRow(
        modifier = modifier,
        selectedTabIndex = currentQuestionIndex,
        edgePadding = 0.dp

    ) {
        questions.forEachIndexed { index, question ->

            val containerColor = when {
                answers.any { it.questionId == question.id } -> MaterialTheme.colorScheme.secondaryContainer
                else -> MaterialTheme.colorScheme.surface
            }

            Tab(
                modifier = Modifier.background(containerColor),
                selected = currentQuestionIndex == index,
                onClick = {onTabSelected(index)}
            ) {
                Text(text = "${index+1}", fontSize = 20.sp, modifier = Modifier.padding(10.dp))
            }
        }
    }
}