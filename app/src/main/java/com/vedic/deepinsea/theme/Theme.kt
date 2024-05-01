package com.vedic.deepinsea.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    /* primary = Purple80,
     secondary = PurpleGrey80,
     tertiary = Pink80*/


    primary = Purple80,
    tertiary = Blue80,
    onTertiary = Blue20,
    scrim = White,
    primaryContainer = DarkPurpleGray20,
    tertiaryContainer = Blue30,
    onTertiaryContainer = Blue90,
    error = Red80,
    onError = Red20,
    errorContainer = Red30,
    onErrorContainer = Red90,
    background = DarkPurpleGray10,
    onBackground = DarkPurpleGray90,
    surface = DarkPurpleGray10,
    onSurface = DarkPurpleGray90,
    surfaceVariant = PurpleGray30,
    onSurfaceVariant = PurpleGray80,
    inverseSurface = DarkPurpleGray90,
    inverseOnSurface = DarkPurpleGray10,
    outline = PurpleGray60,
)

private val LightColorScheme = lightColorScheme(
    /*primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40*/

    primary = Green40,
    onPrimary = Color.White,
    primaryContainer = Teal800,
    onPrimaryContainer = Teal40,
    secondary = DarkGreen40,
    onSecondary = Color.White,
    secondaryContainer = DarkGreen90,
    onSecondaryContainer = DarkGreen10,
    tertiary = Teal40,
    onTertiary = Color.White,
    tertiaryContainer = Teal90,
    onTertiaryContainer = Teal10,
    error = Red40,
    onError = Color.White,
    errorContainer = Red90,
    onErrorContainer = Red10,
    background = DarkGreenGray99,
    onBackground = DarkGreenGray10,
    surface = DarkGreenGray99,
    onSurface = DarkGreenGray10,
    surfaceVariant = GreenGray90,
    onSurfaceVariant = GreenGray30,
    inverseSurface = DarkGreenGray20,
    inverseOnSurface = DarkGreenGray95,
    outline = GreenGray50,
)

@Composable
fun DeepInSeaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}