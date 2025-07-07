package com.volpis.welcome_screen

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class PageIndicatorConfig(
    val isVisible: Boolean = true,
    val activeColor: Color = Color.Blue,
    val inactiveColor: Color = Color.Gray.copy(alpha = 0.3f),
    val size: Dp = 8.dp,
    val spacing: Dp = 12.dp,
    val placement: IndicatorPlacement = IndicatorPlacement.CENTER_BELOW_DESCRIPTION,
    val animationType: IndicatorAnimation = IndicatorAnimation.SLIDE,
    val animationDuration: Int = 300,

    val strokeWidth: Dp = 0.dp,
    val strokeColor: Color = Color.Transparent,
    val enableGlow: Boolean = false,
    val glowColor: Color = Color.White,
    val glowRadius: Dp = 4.dp,

    val shape: IndicatorShape = IndicatorShape.CIRCLE,
    val customCornerRadius: Dp = 4.dp,

    val useProgressiveSizing: Boolean = false,
    val maxSize: Dp = 12.dp,
    val minSize: Dp = 6.dp
)

enum class IndicatorShape {
    CIRCLE, ROUNDED_RECTANGLE, RECTANGLE, DIAMOND
}