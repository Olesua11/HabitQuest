package com.olesya.habitquest.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Ink = Color(0xFF0C0B14)
val Panel = Color(0xFF171524)
val PanelSoft = Color(0xFF211E31)
val Purple = Color(0xFFA78BFA)
val Violet = Color(0xFF7C5CFC)
val Mint = Color(0xFF68E0B4)
val Gold = Color(0xFFFFD166)
val Rose = Color(0xFFFF7AA2)
val TextPrimary = Color(0xFFF8F7FB)
val TextMuted = Color(0xFFAAA5BA)

private val HabitQuestColors = darkColorScheme(
    primary = Purple,
    secondary = Mint,
    tertiary = Gold,
    background = Ink,
    surface = Panel,
    surfaceVariant = PanelSoft,
    onPrimary = Ink,
    onBackground = TextPrimary,
    onSurface = TextPrimary,
    onSurfaceVariant = TextMuted
)

@Composable
fun HabitQuestTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = HabitQuestColors,
        typography = androidx.compose.material3.Typography(),
        content = content
    )
}
