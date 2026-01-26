package com.parallax.client.presentation.ui

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.parallax.client.core.config.AppConfig
import com.parallax.client.domain.model.StreamState
import com.parallax.client.domain.model.UiState
import kotlin.math.roundToInt

@Composable
fun StreamScreen(
    uiState: UiState,
    onStart: () -> Unit,
    onStop: () -> Unit,
    onScaleChanged: (Float) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 28.dp, vertical = 32.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        HeaderSection(status = uiState.statusMessage)

        Crossfade(targetState = uiState.state, label = "state") { state ->
            when (state) {
                StreamState.IDLE -> IdleState(onStart = onStart)
                StreamState.CONNECTING -> LoadingState()
                StreamState.STREAMING -> StreamingState(
                    uiState = uiState,
                    onStop = onStop,
                    onScaleChanged = onScaleChanged,
                )
                StreamState.ERROR -> ErrorState(message = uiState.errorMessage ?: "Unknown error")
            }
        }
    }
}

@Composable
private fun HeaderSection(status: String) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(
            text = "Parallax",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Text(
            text = status,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
        )
    }
}

@Composable
private fun IdleState(onStart: () -> Unit) {
    Card(
        shape = RoundedCornerShape(20.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                text = "Waiting for stream…",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Button(
                onClick = onStart,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            ) {
                Text(text = "Start")
            }
        }
    }
}

@Composable
private fun LoadingState() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        CircularProgressIndicator()
        Text(text = "Connecting…", style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
private fun StreamingState(
    uiState: UiState,
    onStop: () -> Unit,
    onScaleChanged: (Float) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        VideoArea(
            width = uiState.config.remoteWidth,
            height = uiState.config.remoteHeight,
            scale = uiState.currentScale,
        )

        Text(
            text = "Streaming • ${uiState.config.targetFps} fps (simulated) • ${uiState.config.remoteWidth}×${uiState.config.remoteHeight}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
        )

        ScaleControl(
            current = uiState.currentScale,
            onScaleChanged = onScaleChanged,
        )

        Button(onClick = onStop, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)) {
            Text(text = "Stop")
        }
    }
}

@Composable
private fun VideoArea(width: Int, height: Int, scale: Float) {
    val baseWidth: Dp = 280.dp
    val scaledWidth = baseWidth * scale
    Box(
        modifier = Modifier
            .width(scaledWidth)
            .sizeIn(minWidth = 180.dp, maxWidth = 440.dp)
            .aspectRatio(width.toFloat() / height.toFloat())
            .clip(RectangleShape),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp))
                .border(BorderStroke(1.dp, MaterialTheme.colorScheme.outline), RoundedCornerShape(16.dp)),
        )
        Text(
            text = "Video area",
            modifier = Modifier.align(Alignment.Center),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
        )
    }
}

@Composable
private fun ScaleControl(current: Float, onScaleChanged: (Float) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = "Scale ${(current * 100).roundToInt()}%",
            style = MaterialTheme.typography.labelLarge,
        )
        Slider(
            value = current,
            onValueChange = onScaleChanged,
            valueRange = AppConfig.SCALE_MIN..AppConfig.SCALE_MAX,
        )
    }
}

@Composable
private fun ErrorState(message: String) {
    Text(
        text = message,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.error,
    )
}
