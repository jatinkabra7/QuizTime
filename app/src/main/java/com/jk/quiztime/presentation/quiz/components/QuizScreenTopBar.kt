package com.jk.quiztime.presentation.quiz.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreenTopBar(
    modifier: Modifier = Modifier,
    onExitQuizButton : () -> Unit,
    title : String
) {
    TopAppBar(
        windowInsets = WindowInsets(0),
        title = { Text(text = title) },
        actions = {
            IconButton(onClick = {onExitQuizButton()}) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null
                )
            }
        }
    )
}

@Preview
@Composable
private fun p() {
    QuizScreenTopBar(
        title = "New Quiz",
        onExitQuizButton = {}
    )
}