package com.parallax.client.domain.model

data class StreamConfig(
    val remoteWidth: Int,
    val remoteHeight: Int,
    val targetFps: Int,
    val initialScale: Float,
)
