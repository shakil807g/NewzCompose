package com.shakil.newzcompose.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.onCommit
import androidx.compose.ui.graphics.Color
import com.shakil.newzcompose.util.SysUiController
import okhttp3.internal.wait


//private val DarkColorPalette = darkColors(
//        primary = purple200,
//        primaryVariant = purple700,
//        secondary = teal200
//)
//
//private val LightColorPalette = lightColors(
//        primary = purple500,
//        primaryVariant = purple700,
//        secondary = teal200
//
//        /* Other default colors to override
//    background = Color.White,
//    surface = Color.White,
//    onPrimary = Color.White,
//    onSecondary = Color.Black,
//    onBackground = Color.Black,
//    onSurface = Color.Black,
//    */
//)

private val DarkColorPalette = darkColors(
        primary = Blue200,
        primaryVariant = Blue400,
        onPrimary = Color.Black,
        secondary = Yellow400,
        onSecondary = Color.Black,
        background = Color.Black,
        surface =  Color.Black,
        onSurface = Color.White,
        onBackground = Color.White,
        error = Red300,
        onError = Color.Black
)

private val LightColorPalette = lightColors(
        primary = Blue500,
        primaryVariant = Blue800,
        onPrimary = Color.White,
        secondary = Yellow700,
        secondaryVariant = Yellow800,
        background = Color.White,
        surface =  Color.White,
        onSecondary = Color.Black,
        onSurface = Color.Black,
        onBackground = Color.Black,
        error = Red800,
        onError = Color.White
)

@Composable
fun NewzComposeTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    val sysUiController = SysUiController.current
    onCommit(sysUiController, colors.background) {
        sysUiController.setSystemBarsColor(
                color = colors.background.copy(alpha = AlphaNearOpaque),
                darkIcons = true
        )
    }

    MaterialTheme(
            colors = colors,
            typography = typography,
            shapes = shapes,
            content = content
    )
}