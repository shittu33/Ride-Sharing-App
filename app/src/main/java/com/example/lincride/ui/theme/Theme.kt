package com.example.lincride.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = PrimaryBlue,
    secondary = PrimaryGreen,
    tertiary = PrimaryGreen,
    background = BackgroundWhite,
    surface = BackgroundWhite,
    error = DangerError
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryBlue,
    secondary = PrimaryGreen,
    tertiary = PrimaryGreen,
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),
    error = DangerError
)

@Composable
fun LincTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val customColorsPalette = when {
        darkTheme -> DarkCustomColorsPalette
        else -> LightCustomColorsPalette
    }

    val view = LocalView.current
    if (!view.isInEditMode) {

        SideEffect {
            val window = (view.context as Activity).window

            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    CompositionLocalProvider(
        LocalColors provides customColorsPalette
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}
