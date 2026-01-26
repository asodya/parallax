package com.parallax.client.domain.module

import com.parallax.client.domain.service.StreamSessionService

class StartStreamUseCase(private val service: StreamSessionService) {
    operator fun invoke() {
        service.startStream()
    }
}
