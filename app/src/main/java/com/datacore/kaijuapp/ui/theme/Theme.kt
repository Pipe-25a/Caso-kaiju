package com.datacore.kaijuapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val KaijuColorScheme = darkColorScheme(
    primary            = KaijuPurple,
    onPrimary          = KaijuOnDark,
    primaryContainer   = KaijuDarkPurple,
    onPrimaryContainer = KaijuYellow,
    secondary          = KaijuPurpleLight,
    onSecondary        = KaijuOnDark,
    tertiary           = KaijuYellow,
    onTertiary         = KaijuBlack,
    background         = KaijuBlack,
    onBackground       = KaijuOnDark,
    surface            = KaijuDarkPurple,
    onSurface          = KaijuOnDark,
    surfaceVariant     = KaijuDarkPurple,
    onSurfaceVariant   = KaijuGrayText,
    outline            = KaijuPurpleLight,
    error              = KaijuError,
    onError            = KaijuBlack
)

@Composable
fun KaijuAppTheme(
    darkTheme: Boolean = true,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = KaijuColorScheme,
        typography = Typography,
        content = content
    )
}