package com.halill.halill2.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Gray700,
    primaryVariant = Teal200,
    secondary = Purple200,
    background = Gray900,
    onSurface = Color.DarkGray
)

private val LightColorPalette = lightColors(
    primary = Teal700,
    primaryVariant = Teal900,
    secondary = Purple500,
    background = Color.White,
    onSurface = Color.White
)

@Composable
fun HalIll_AndroidTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}