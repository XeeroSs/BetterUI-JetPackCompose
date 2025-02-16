package fr.xeross.betterui.extensions

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import kotlin.math.max
import kotlin.math.min

object ColorExtension {

    fun Color.transparent(percent: Float): Color {
        val clampedPercent = percent.coerceIn(0f, 100f) / 100f
        return copy(alpha = clampedPercent)
    }

    fun Color.darker(factor: Float = 0.8f): Color {
        val argb = this.toArgb()
        val r = (argb shr 16 and 0xFF)
        val g = (argb shr 8 and 0xFF)
        val b = (argb and 0xFF)

        val newR = max((r * factor).toInt(), 0)
        val newG = max((g * factor).toInt(), 0)
        val newB = max((b * factor).toInt(), 0)

        return Color(newR, newG, newB)
    }

    fun Color.lighter(factor: Float = 1.2f): Color {
        val argb = this.toArgb()
        val r = (argb shr 16 and 0xFF)
        val g = (argb shr 8 and 0xFF)
        val b = (argb and 0xFF)

        val newR = min((r * factor).toInt(), 255)
        val newG = min((g * factor).toInt(), 255)
        val newB = min((b * factor).toInt(), 255)

        return Color(newR, newG, newB)
    }

}