package com.jk.quiztime.presentation.dashboard.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.jk.quiztime.R
import java.nio.file.WatchEvent

@Composable
fun TopicCard(
    modifier: Modifier = Modifier,
    topicName : String,
    imageUrl : String,
    onClick : () -> Unit
) {

    Box {

        Card(
            modifier = modifier.clickable {onClick()}
        ) {

            Column(
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
            ) {

                Icon(
                    modifier = Modifier
                        .padding(bottom = 5.dp)
                        .size(30.dp),
                    painter = painterResource(R.drawable.baseline_play_arrow_24),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )

                Text(
                    fontSize = 16.sp,
                    text = topicName,
                    maxLines = 1,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .basicMarquee(
                            repeatDelayMillis = 0,
                            initialDelayMillis = 0,
                        )
                )
            }

        }

        TopicImage(
            imageUrl = imageUrl,
            modifier = Modifier
                .size(75.dp)
                .align(Alignment.TopEnd)
                .padding(end = 10.dp)
                .offset(y = (10).dp)
        )
    }


}

@Composable
private fun TopicImage(
    modifier: Modifier = Modifier,
    imageUrl : String
) {
    val context = LocalContext.current
    val imageRequest = ImageRequest
        .Builder(context = context)
        .data(imageUrl)
        .crossfade(enable = true)
        .build()

    AsyncImage(
        modifier = modifier,
        model = imageRequest,
        contentDescription = null,
        alignment = Alignment.TopCenter,
        placeholder = painterResource(R.drawable.yt_logo_without_play_button),
        error = painterResource(R.drawable.yt_logo_without_play_button)
    )
}

@Preview(showSystemUi = true)
@Composable
private fun p() {
    TopicCard(
        onClick = {},
        imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQAq_P6bFIFwZjrDzPNwbHvz4dRRmq8ihzADg&s",
        topicName = "New Topic",
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
    )
}