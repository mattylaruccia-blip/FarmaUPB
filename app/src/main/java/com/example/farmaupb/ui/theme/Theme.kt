package com.example.farmaupb.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val FarmaColorScheme = lightColorScheme(
    primary = FarmaGreen,
    onPrimary = FarmaWhite,
    primaryContainer = FarmaGreenSurface,
    onPrimaryContainer = FarmaGreenDark,
    secondary = FarmaGreenLight,
    onSecondary = FarmaWhite,
    secondaryContainer = FarmaGreenSurface,
    onSecondaryContainer = FarmaGreenDark,
    tertiary = FarmaBlue,
    onTertiary = FarmaWhite,
    background = FarmaGray,
    onBackground = FarmaGrayDark,
    surface = FarmaWhite,
    onSurface = FarmaGrayDark,
    surfaceVariant = FarmaGreenSurface,
    onSurfaceVariant = FarmaGreenDark,
    error = FarmaRed,
    onError = FarmaWhite,
    outline = FarmaGrayMedium
)

@Composable
fun FarmaUPBTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = FarmaColorScheme,
        typography = Typography,
        content = content
    )
}
