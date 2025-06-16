package com.jk.quiztime.presentation.dashboard.components

import android.R
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.nio.file.WatchEvent

@Composable
fun ShimmerEffect(
    modifier: Modifier = Modifier
) {

    val shimmerColors = listOf(
        MaterialTheme.colorScheme.surface.copy(0.6f),
        MaterialTheme.colorScheme.surface.copy(0.2f),
        MaterialTheme.colorScheme.surface.copy(0.6f)
    )

    val transition = rememberInfiniteTransition().animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(animation = tween(1000), repeatMode = RepeatMode.Restart)
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = transition.value, y = transition.value)
    )


    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {

        Box(
            modifier = Modifier
                .matchParentSize()
                .background(brush)
        ) {

        }

    }
}

@Preview
@Composable
private fun p() {

    ShimmerEffect(
        modifier = Modifier.fillMaxWidth().height(250.dp)
    )

}