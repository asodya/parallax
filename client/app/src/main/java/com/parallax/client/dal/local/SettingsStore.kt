package com.parallax.client.dal.local

interface SettingsStore {
    fun getScale(): Float
    fun setScale(value: Float)
}
