package com.jk.quiztime.presentation.dashboard.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NameEditDialog(
    modifier: Modifier = Modifier,
    isOpen : Boolean,
    usernameError : String?,
    title: String = "Edit your name",
    textFieldValue : String,
    onDismissRequest: () -> Unit,
    confirmButtonText: String = "Rename",
    dismissButtonText: String = "Cancel",
    onConfirmButtonClick: () -> Unit,
    onValueChange : (String) -> Unit
) {
    if(isOpen) {

        AlertDialog(
            modifier = modifier,
            title = { Text(text = title) },
            text = {
                OutlinedTextField(
                    value = textFieldValue,
                    onValueChange = {onValueChange},
                    singleLine = true,
                    isError = usernameError != null && textFieldValue.isNotBlank(),
                    supportingText = {Text(text = usernameError.orEmpty())}
                )
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