package com.jk.quiztime.presentation.dashboard.components

import com.jk.quiztime.R
import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradient
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jk.quiztime.presentation.theme.customBlue
import com.jk.quiztime.presentation.theme.customPink

@Composable
fun UserStatisticsCard(
    modifier: Modifier = Modifier,
    questionsAttempted: Int,
    questionsCorrect: Int
) {

    val progress = if (questionsAttempted > 0) {
        questionsCorrect.toFloat() / questionsAttempted
    } else 0f

    Card(
        modifier = modifier
    ) {

        ProgressBar(
            progress = progress,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .height(15.dp)
        )

        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            UserStatistics(
                value = questionsAttempted,
                description = "Questions Attempted",
                iconResId = R.drawable.ic_touch
            )

            UserStatistics(
                value = questionsCorrect,
                description = "Correct Answers",
                iconResId = R.drawable.ic_check_circle
            )
        }
    }

}

@Composable
private fun ProgressBar(
    modifier: Modifier = Modifier,
    gradientColors: List<Color> = listOf(customPink, customBlue),
    progress: Float
) {

    Box(
        modifier = modifier
            .clip(MaterialTheme.shapes.extraSmall)
            .background(Color.White)

    ) {

        Box(
            Modifier
                .clip(MaterialTheme.shapes.extraSmall)
                .background(Brush.linearGradient(gradientColors))
                .fillMaxWidth(progress)
                .fillMaxHeight()

        )

        Box(
            modifier = Modifier
                .padding(end = 5.dp)
                .clip(CircleShape)
                .background(Brush.linearGradient(gradientColors))
                .align(Alignment.CenterEnd)
                .size(5.dp)
        )
    }

}

@Composable
private fun UserStatistics(
    modifier: Modifier = Modifier,
    value: Int,
    description: String,
    iconResId : Int
) {

    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {



        Icon(
            modifier = modifier
                .size(40.dp),
            painter = painterResource(iconResId),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.secondary
        )

        Spacer(modifier = Modifier.width(10.dp))

        Column {
            Text(
                text = "$value",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = Bold
            )
            Text(
                text = description,
                style = MaterialTheme.typography.labelMedium
            )
        }
    }


}

@Preview
@Composable
private fun Preview() {

    UserStatisticsCard(
        questionsCorrect = 4,
        questionsAttempted = 5
    )

}