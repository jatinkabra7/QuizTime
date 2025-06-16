package com.jk.quiztime.presentation.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import com.jk.quiztime.domain.model.QuizTopic
import com.jk.quiztime.presentation.common_component.ErrorScreen
import com.jk.quiztime.presentation.dashboard.components.NameEditDialog
import com.jk.quiztime.presentation.dashboard.components.ShimmerEffect
import com.jk.quiztime.presentation.dashboard.components.TopicCard
import com.jk.quiztime.presentation.dashboard.components.UserStatisticsCard

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    states: DashboardStates,
    onTopicCardClick: (Int) -> Unit
) {

    NameEditDialog(
        isOpen = states.isNameEditDialogOpen,
        usernameError = states.usernameError,
        textFieldValue = states.usernameTextFieldValue,
        onDismissRequest = {},
        onConfirmButtonClick = {},
        onValueChange = {},
    )
    
    Column(

        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(top = 40.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
    ) {

        HeaderSection(
            modifier = Modifier.fillMaxWidth(),
            username = states.username,
            questionsCorrect = states.questionsCorrect,
            questionsAttempted = states.questionsAttempted,
            onEditNameClick = { }
        )

        Spacer(Modifier.height(10.dp))

        QuizTopicSection(
            errorMessage = states.errorMessage,
            onRefreshClick = {},
            isTopicLoading = states.isTopicLoading,
            quizTopics = states.quizTopics,
            modifier = Modifier
                .fillMaxWidth(),
            onTopicCardClick = {onTopicCardClick(it)}
        )

    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun HeaderSection(
    modifier: Modifier = Modifier,
    questionsAttempted: Int,
    questionsCorrect: Int,
    username: String,
    onEditNameClick: () -> Unit
) {

    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Column(
            modifier = Modifier
        ) {
            Text(
                text = "Hello",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground


            )

            Row {

                Text(
                    text = username,
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onBackground

                )

                IconButton(
                    modifier = Modifier
                        .offset(y = (-20).dp, x = (-10).dp),
                    onClick = {
                        onEditNameClick()
                    }
                ) {
                    Icon(
                        modifier = Modifier
                            .size(20.dp),
                        imageVector = Icons.Default.Edit,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.surfaceTint
                    )
                }
            }

        }

        UserStatisticsCard(
            modifier = Modifier
                .widthIn(max = 400.dp),
            questionsCorrect = questionsCorrect,
            questionsAttempted = questionsAttempted
        )
    }

}

@Composable
private fun QuizTopicSection(
    modifier: Modifier = Modifier,
    quizTopics: List<QuizTopic>,
    isTopicLoading : Boolean,
    errorMessage : String?,
    onRefreshClick : () -> Unit,
    onTopicCardClick : (Int) -> Unit
) {

    Column(
        modifier = modifier
    ) {
        Text(
            text = "Choose a topic",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(Modifier.height(10.dp))

        if(errorMessage != null) {
            ErrorScreen(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                errorMessage = errorMessage,
                onRefreshClick = {onRefreshClick()}
            )
        } else {

            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 150.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                if(isTopicLoading) {

                    items(quizTopics.size) {
                        ShimmerEffect(
                            modifier = Modifier
                                .clip(MaterialTheme.shapes.small)
                                .height(120.dp)
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                        )
                    }

                } else {

                    items(quizTopics) { topic ->
                        TopicCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp),
                            topicName = topic.name,
                            imageUrl = topic.imageUrl,
                            onClick = {onTopicCardClick(topic.code)}
                        )
                    }
                }

            }
        }

    }

}

@PreviewScreenSizes
@Composable
private fun p() {

    val dummyList = List<QuizTopic>(size = 20) { index ->
        QuizTopic(
            id = index.toString(),
            name = "New Topic $index",
            imageUrl = "",
            code = 0
        )
    }

    val state =
        DashboardStates(
            username = "Jatin Kabra",
            questionsCorrect = 5,
            questionsAttempted = 10,
            quizTopics = dummyList,
            isTopicLoading = true
        )
    DashboardScreen(
        states = state,
        onTopicCardClick = {}
    )

}