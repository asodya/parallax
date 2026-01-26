package com.parallax.client.core.logging

object Loggers {
    val default: Logger by lazy { AndroidLogger() }
}
