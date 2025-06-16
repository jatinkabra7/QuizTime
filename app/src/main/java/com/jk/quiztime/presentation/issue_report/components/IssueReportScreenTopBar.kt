package com.jk.quiztime.presentation.issue_report.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IssueReportScreenTopBar(
    modifier: Modifier = Modifier,
    onBackButtonClick : () -> Unit,
    title : String
) {
    TopAppBar(
        windowInsets = WindowInsets(0),
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = {onBackButtonClick}) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = null
                )
            }
        }
    )
}

@Preview
@Composable
private fun p() {



    IssueReportScreenTopBar(
        title = "Report an issue",
        onBackButtonClick = {}
    )
}