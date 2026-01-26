package com.parallax.client.core.logging

import android.util.Log

class AndroidLogger : Logger {
    override fun debug(tag: String, message: String, data: Map<String, Any?>) {
        Log.d(tag, format(message, data))
    }

    override fun info(tag: String, message: String, data: Map<String, Any?>) {
        Log.i(tag, format(message, data))
    }

    override fun warn(tag: String, message: String, data: Map<String, Any?>) {
        Log.w(tag, format(message, data))
    }

    override fun error(tag: String, message: String, throwable: Throwable?, data: Map<String, Any?>) {
        if (throwable != null) {
            Log.e(tag, format(message, data), throwable)
        } else {
            Log.e(tag, format(message, data))
        }
    }

    private fun format(message: String, data: Map<String, Any?>): String {
        if (data.isEmpty()) return message
        val formatted = data.entries.joinToString(prefix = "{", postfix = "}") { (key, value) ->
            "\"$key\":\"$value\""
        }
        return "$message $formatted"
    }
}
