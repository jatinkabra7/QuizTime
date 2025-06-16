package com.jk.quiztime.presentation.quiz.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SubmitButtons(
    modifier: Modifier = Modifier,
    isPreviousButtonEnabled: Boolean,
    isForwardButtonEnabled: Boolean,
    onPreviousButtonClick: () -> Unit,
    onForwardButtonClick: () -> Unit,
    onSubmitButtonClick: () -> Unit
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        OutlinedIconButton(
            onClick = { onPreviousButtonClick() },
            enabled = isPreviousButtonEnabled,
            colors = IconButtonDefaults.iconButtonColors(
                disabledContainerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.onBackground,
                disabledContentColor = MaterialTheme.colorScheme.background
            )
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                contentDescription = null
            )
        }

        Button(
            modifier = Modifier.padding(horizontal = 30.dp),
            onClick = { onSubmitButtonClick() }
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 30.dp),
                text = "Submit"
            )
        }

        OutlinedIconButton(
            onClick = { onForwardButtonClick() },
            enabled = isForwardButtonEnabled,
            colors = IconButtonDefaults.iconButtonColors(
                disabledContainerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.onBackground,
                disabledContentColor = MaterialTheme.colorScheme.background
            )
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowForward,
                contentDescription = null

            )
        }
    }

}