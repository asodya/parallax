package com.parallax.client.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = AccentBlue,
    onPrimary = Slate50,
    secondary = Slate700,
    onSecondary = Slate50,
    background = Slate50,
    onBackground = Slate900,
    surface = Slate50,
    onSurface = Slate900,
    outline = Slate200,
)

@Composable
fun ParallaxTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColors,
        typography = ParallaxTypography,
        content = content,
    )
}
