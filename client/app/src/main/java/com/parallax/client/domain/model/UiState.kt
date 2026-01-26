package com.parallax.client.domain.model

data class UiState(
    val state: StreamState,
    val config: StreamConfig,
    val currentScale: Float,
    val statusMessage: String,
    val errorMessage: String? = null,
)
