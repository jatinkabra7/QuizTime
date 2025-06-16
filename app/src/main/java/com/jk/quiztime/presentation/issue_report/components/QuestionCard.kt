package com.jk.quiztime.presentation.issue_report.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jk.quiztime.domain.model.Question
import com.jk.quiztime.domain.model.UserAnswer
import com.jk.quiztime.presentation.theme.customGreen

@Composable
fun QuestionCard(
    modifier: Modifier = Modifier,
    question: Question?,
    isCardExpanded : Boolean,
    onDropdownButtonClick : () -> Unit
) {

    val transition = updateTransition(targetState = isCardExpanded, label = "")

    val iconRotationDegree = transition.animateFloat(label = "") {expandedState ->
        if(expandedState) 180f else 0f
    }.value

    val questionColor = transition.animateColor(label = "") {expandedState ->
        if(expandedState) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
    }.value

    val backgroundColorAlpha = transition.animateFloat(label = "") {expandedState ->
        if(expandedState) 1f else 0.5f
    }.value

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(backgroundColorAlpha)
        )
    ) {

        SelectionContainer {

            Column(
                modifier = modifier
                    .padding(10.dp)
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {



                    Text(
                        text = question?.question.orEmpty(),
                        style = MaterialTheme.typography.titleLarge,
                        color = questionColor,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )

                    IconButton(onClick = {onDropdownButtonClick}) {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = null,
                            modifier = Modifier
                                .rotate(iconRotationDegree)
                        )
                    }
                }

                AnimatedVisibility(visible = isCardExpanded) {

                    Column {

                        question?.allOptions?.forEachIndexed { index, option ->

                            val letter =
                                when (index) {
                                    0 -> "(A)  "
                                    1 -> "(B)  "
                                    2 -> "(C)  "
                                    else -> "(D)  "
                                }

                            Text(
                                text = letter + option,
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier
                                    .padding(vertical = 10.dp)
                            )
                        }


                        Text(
                            text = "Explanation: " + question?.explanation.orEmpty(),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.secondary

                        )
                    }

                }
            }
        }

    }


}

@Preview
@Composable
private fun p() {
    val allOptions = listOf("option A", "option B", "option C", "option D")

    val questions = List(size = 20) { index ->
        Question(
            id = "$index",
            topicCode = 0,
            question = "This is the question?",
            allOptions = allOptions,
            correctAnswer = allOptions[0],
            explanation = "This is the explanation"
        )
    }

    val answers = listOf(
        UserAnswer(questionId = "1", selectedOption = "")
    )
    QuestionCard(
        question = questions[0],
        isCardExpanded = true,
        onDropdownButtonClick = {}
    )
}