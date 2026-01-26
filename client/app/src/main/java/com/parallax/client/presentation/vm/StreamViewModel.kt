package com.parallax.client.presentation.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.parallax.client.core.logging.Loggers
import com.parallax.client.dal.local.SharedPreferencesSettingsStore
import com.parallax.client.domain.module.SetScaleUseCase
import com.parallax.client.domain.module.StartStreamUseCase
import com.parallax.client.domain.module.StopStreamUseCase
import com.parallax.client.domain.service.StreamSessionService
import kotlinx.coroutines.flow.StateFlow

class StreamViewModel(application: Application) : AndroidViewModel(application) {
    private val settingsStore = SharedPreferencesSettingsStore(application)
    private val service = StreamSessionService(
        settingsStore = settingsStore,
        logger = Loggers.default,
        scope = viewModelScope,
    )

    private val startStreamUseCase = StartStreamUseCase(service)
    private val stopStreamUseCase = StopStreamUseCase(service)
    private val setScaleUseCase = SetScaleUseCase(service)

    val uiState: StateFlow<com.parallax.client.domain.model.UiState> = service.uiState

    fun onStartClicked() {
        startStreamUseCase()
    }

    fun onStopClicked() {
        stopStreamUseCase()
    }

    fun onScaleChanged(value: Float) {
        setScaleUseCase(value)
    }
}
