package com.parallax.client.dal.local

import android.content.Context
import com.parallax.client.core.config.AppConfig

class SharedPreferencesSettingsStore(context: Context) : SettingsStore {
    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    override fun getScale(): Float {
        return prefs.getFloat(KEY_SCALE, AppConfig.SCALE_DEFAULT)
    }

    override fun setScale(value: Float) {
        prefs.edit().putFloat(KEY_SCALE, value).apply()
    }

    private companion object {
        const val PREFS_NAME = "parallax_settings"
        const val KEY_SCALE = "stream_scale"
    }
}
