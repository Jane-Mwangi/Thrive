

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
import design.fiti.thrive.presentation.ui.theme.ThriveBlack
import design.fiti.thrive.presentation.ui.theme.ThrivePrimary
import design.fiti.thrive.presentation.ui.theme.ThriveSecondary
import design.fiti.thrive.presentation.ui.theme.ThriveTertiary

private val DarkColorScheme = darkColorScheme(
    primary = ThrivePrimary,
    tertiary = ThriveTertiary,
    secondary = ThriveSecondary,


    // Other default colors to override
    background = ThriveTertiary,
    surface = Color(0xFFFFFBFE),
    onPrimary =ThriveTertiary,
    onSecondary = ThriveTertiary,
    onTertiary = ThriveBlack,
    onBackground = ThrivePrimary,
    onSurface = ThrivePrimary,

    )

private val LightColorScheme = lightColorScheme(
    primary = ThrivePrimary,
    tertiary =ThriveTertiary,
    secondary = ThriveSecondary,

    // Other default colors to override
    background = ThriveTertiary,
    surface = Color(0xFFFFFBFE),
    onPrimary = ThriveTertiary,
    onSecondary = ThriveTertiary,
    onTertiary = ThriveBlack,
    onBackground =ThrivePrimary,
    onSurface = ThrivePrimary,

    )

@Composable
fun ThriveTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
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