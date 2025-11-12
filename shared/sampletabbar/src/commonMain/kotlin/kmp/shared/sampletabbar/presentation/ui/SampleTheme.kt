package kmp.shared.sampletabbar.presentation.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import kmp.shared.samplecomposemultiplatform.presentation.common.Values

// https://coolors.co/f5ab00-b8a422-9aa133-7b9d44-d95700-e0e0e0-f0f0f0
val LightColorScheme = lightColorScheme(
    primary = Color(0xFFF5AB00),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFB8A422),
    onPrimaryContainer = Color(0xFFFFFFFF),

    secondary = Color(0xFF7B9D44),
    onSecondary = Color(0xFF000000),
    secondaryContainer = Color(0xFF9AA133),
    onSecondaryContainer = Color(0xFF000000),

    background = Color(0xFFF0F0F0),
    onBackground = Color(0xFF000000),

    surface = Color(0xFFE0E0E0),
    onSurface = Color(0xFF000000),

    error = Color(0xFFD95700),
    onError = Color(0xFF000000),

    // Optional M3 roles — fill with best approximations:
    surfaceVariant = Color(0xFFE0E0E0),
    onSurfaceVariant = Color(0xFF000000),
    outline = Color(0xFF9AA133),
    outlineVariant = Color(0xFFB8A422),
    scrim = Color(0xFF000000),
    inverseSurface = Color(0xFF000000),
    inverseOnSurface = Color(0xFFE0E0E0),
    inversePrimary = Color(0xFFB8A422),
)

// https://coolors.co/f5ab00-b8a422-9aa133-7b9d44-d95700-1f1f1f-141414
val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFF5AB00),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFB8A422),
    onPrimaryContainer = Color(0xFFFFFFFF),

    secondary = Color(0xFF7B9D44),
    onSecondary = Color(0xFF000000),
    secondaryContainer = Color(0xFF9AA133),
    onSecondaryContainer = Color(0xFF000000),

    background = Color(0xFF141414),
    onBackground = Color(0xFFFFFFFF),

    surface = Color(0xFF1F1F1F),
    onSurface = Color(0xFFFFFFFF),

    error = Color(0xFFD95700),
    onError = Color(0xFFFFFFFF),

    // Optional roles
    surfaceVariant = Color(0xFF1F1F1F),
    onSurfaceVariant = Color(0xFFFFFFFF),
    outline = Color(0xFF9AA133),
    outlineVariant = Color(0xFFB8A422),
    scrim = Color(0xFF000000),
    inverseSurface = Color(0xFFE0E0E0),
    inverseOnSurface = Color(0xFF000000),
    inversePrimary = Color(0xFFB8A422),
)

val typography = Typography(
    // Define typohraphy
)

val shapes = Shapes(
    small = RoundedCornerShape(Values.Radius.large),
    medium = RoundedCornerShape(Values.Radius.medium),
    large = RoundedCornerShape(Values.Radius.small),
)

@Composable
fun SampleTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = colors,
        typography = typography,
        shapes = shapes,
        content = content,
    )
}
