package fr.xeross.betterui.extensions

import androidx.compose.ui.graphics.Color

object ColorExtension {

    fun Color.transparent(percent: Float): Color {
        val clampedPercent = percent.coerceIn(0f, 100f) / 100f
        return copy(alpha = clampedPercent)
    }

}