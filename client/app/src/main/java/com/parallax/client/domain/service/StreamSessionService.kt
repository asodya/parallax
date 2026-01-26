package com.parallax.client.domain.service

import com.parallax.client.core.config.AppConfig
import com.parallax.client.core.logging.Logger
import com.parallax.client.dal.local.SettingsStore
import com.parallax.client.domain.model.StreamConfig
import com.parallax.client.domain.model.StreamState
import com.parallax.client.domain.model.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class StreamSessionService(
    private val settingsStore: SettingsStore,
    private val logger: Logger,
    private val scope: CoroutineScope,
) {
    private val _uiState = MutableStateFlow(initialState())
    val uiState: StateFlow<UiState> = _uiState

    fun startStream() {
        logger.info(TAG, "Start stream requested")
        _uiState.update { it.copy(state = StreamState.CONNECTING, statusMessage = "Connecting…") }
        scope.launchDelayedTransition()
    }

    fun stopStream() {
        logger.info(TAG, "Stop stream requested")
        _uiState.update { it.copy(state = StreamState.IDLE, statusMessage = "Waiting for stream…") }
    }

    fun setScale(scale: Float) {
        settingsStore.setScale(scale)
        _uiState.update { it.copy(currentScale = scale) }
    }

    private fun CoroutineScope.launchDelayedTransition() {
        kotlinx.coroutines.launch {
            delay(CONNECT_DELAY_MS)
            _uiState.update {
                it.copy(
                    state = StreamState.STREAMING,
                    statusMessage = "Streaming • 60 fps (simulated) • ${it.config.remoteWidth}×${it.config.remoteHeight}",
                )
            }
        }
    }

    private fun initialState(): UiState {
        val storedScale = settingsStore.getScale().coerceIn(AppConfig.SCALE_MIN, AppConfig.SCALE_MAX)
        val config = StreamConfig(
            remoteWidth = AppConfig.DEFAULT_REMOTE_WIDTH,
            remoteHeight = AppConfig.DEFAULT_REMOTE_HEIGHT,
            targetFps = AppConfig.DEFAULT_FPS,
            initialScale = storedScale,
        )
        return UiState(
            state = StreamState.IDLE,
            config = config,
            currentScale = storedScale,
            statusMessage = "Waiting for stream…",
            errorMessage = null,
        )
    }

    private companion object {
        const val TAG = "StreamSession"
        const val CONNECT_DELAY_MS = 1500L
    }
}
