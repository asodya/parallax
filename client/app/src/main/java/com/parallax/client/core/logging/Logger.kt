package com.parallax.client.core.logging

interface Logger {
    fun debug(tag: String, message: String, data: Map<String, Any?> = emptyMap())
    fun info(tag: String, message: String, data: Map<String, Any?> = emptyMap())
    fun warn(tag: String, message: String, data: Map<String, Any?> = emptyMap())
    fun error(tag: String, message: String, throwable: Throwable? = null, data: Map<String, Any?> = emptyMap())
}
