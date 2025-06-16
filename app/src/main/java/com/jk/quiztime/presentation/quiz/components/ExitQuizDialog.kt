package com.jk.quiztime.presentation.quiz.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExitQuizDialog(
    modifier: Modifier = Modifier,
    isOpen : Boolean,
    title: String = "Exit Quiz",
    onDismissRequest: () -> Unit,
    confirmButtonText: String = "Exit",
    dismissButtonText: String = "Cancel",
    onConfirmButtonClick: () -> Unit
) {
    if(isOpen) {

        AlertDialog(
            modifier = modifier,
            title = { Text(text = title) },
            text = {
                Text(text = "You will not be able to continue from where you left off.  ")
            },
            onDismissRequest = onDismissRequest,
            confirmButton = {
                TextButton(
                    onClick = { onConfirmButtonClick() }
                ) {
                    Text(text = confirmButtonText)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { onDismissRequest() }
                ) {
                    Text(text = dismissButtonText)
                }
            }
        )
    }
}