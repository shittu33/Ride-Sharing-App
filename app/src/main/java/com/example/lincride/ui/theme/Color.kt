package com.example.lincride.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// Base colors from Figma
val PrimaryBlue = Color(0xFF2C75FF)
val PrimaryGreen = Color(0xFF2DFFA0)
val BackgroundWhite = Color(0xFFFFFFFF)
val TextPrimary = Color(0xFF2A2A2A)
val TextSecondary = Color(0xFF656565)
val StrokeGray = Color(0xFFD1D1D1)
val StrokeGray2 = Color(0xFFB0B0B0)
val LightGray = Color(0xFFF8F8F8)
val DangerError = Color(0xFFED1C15)

@Immutable
data class CustomColorsPalette(
    val primary: Color = PrimaryBlue,
    val secondary: Color = PrimaryGreen,
    val background: Color = BackgroundWhite,
    val textPrimary: Color = TextPrimary,
    val textSecondary: Color = TextSecondary,
    val stroke: Color = StrokeGray,
    val stroke2: Color = StrokeGray2,
    val surface: Color = BackgroundWhite,
    val surfaceVariant: Color = LightGray
)

val LightCustomColorsPalette = CustomColorsPalette()

val DarkCustomColorsPalette = CustomColorsPalette(
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),
    surfaceVariant = Color(0xFF2A2A2A),
    textPrimary = Color(0xFFE1E1E1),
    textSecondary = Color(0xFFB0B0B0)
)

val LocalColors = staticCompositionLocalOf { CustomColorsPalette() }
val LincColors: CustomColorsPalette
    @Composable
    get() = LocalColors.current
