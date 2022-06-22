package com.darkmoose117.gather.ui.theme

import androidx.compose.material.Colors
import androidx.compose.material.LocalElevationOverlay
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.unit.Dp

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)
val Yellow900 = Color(0xFFF47E17)
val Yellow900Light = Color(0xFFFFAF4C)
val Yellow900Dark = Color(0xFFBB5000)
val LightBlue300 = Color(0xFF4FC3F7)
val LightBlue300Light = Color(0xFF8BF6FF)
val LightBlue300Dark = Color(0xFF0093C4)

val MtgGreenBg = Color(196, 211, 202)
val MtgGreenFg = Color(0, 115, 62)
val MtgBlueBg = Color(179, 206, 234)
val MtgBlueFg = Color(14, 104, 171)
val MtgRedBg = Color(235, 159, 130)
val MtgRedFg = Color(211, 32, 42)
val MtgWhiteBg = Color(248, 231, 185)
val MtgWhiteFg = Color(249, 250, 244)
val MtgBlackBg = Color(166, 159, 157)
val MtgBlackFg = Color(21, 11, 0)


/**
 * Return the fully opaque color that results from compositing [onSurface] atop [surface] with the
 * given [alpha]. Useful for situations where semi-transparent colors are undesirable.
 */
@Composable
fun Colors.compositedOnSurface(alpha: Float): Color {
    return onSurface.copy(alpha = alpha).compositeOver(surface)
}

/**
 * Calculates the color of an elevated `surface` in dark mode. Returns `surface` in light mode.
 */
@Composable
fun Colors.elevatedSurface(elevation: Dp): Color {
    return LocalElevationOverlay.current?.apply(
        color = this.surface,
        elevation = elevation
    ) ?: this.surface
}