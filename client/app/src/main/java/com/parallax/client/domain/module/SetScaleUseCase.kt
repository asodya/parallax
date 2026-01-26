package com.parallax.client.domain.module

import com.parallax.client.domain.service.StreamSessionService

class SetScaleUseCase(private val service: StreamSessionService) {
    operator fun invoke(scale: Float) {
        service.setScale(scale)
    }
}
