package com.volpis.welcome_screen.config

import androidx.annotation.ColorInt
import androidx.annotation.Px

data class PageIndicatorConfig(
    val isVisible: Boolean = true,
    @ColorInt val activeColor: Int = 0xFF2196F3.toInt(),
    @ColorInt val inactiveColor: Int = 0x4D9E9E9E.toInt(),
    @Px val size: Int = 24,
    @Px val spacing: Int = 36,
    val placement: IndicatorPlacement = IndicatorPlacement.CENTER_BELOW_DESCRIPTION,
    val animationType: IndicatorAnimation = IndicatorAnimation.SLIDE,
    val animationDuration: Int = 300,
    @Px val strokeWidth: Int = 0,
    @ColorInt val strokeColor: Int = 0x00000000,
    val enableGlow: Boolean = false,
    @ColorInt val glowColor: Int = 0xFFFFFFFF.toInt(),
    @Px val glowRadius: Float = 12f,
    val shape: IndicatorShape = IndicatorShape.CIRCLE,
    @Px val customCornerRadius: Float = 12f,
    val useProgressiveSizing: Boolean = false,
    @Px val maxSize: Int = 36,
    @Px val minSize: Int = 18
)

enum class IndicatorShape {
    CIRCLE, ROUNDED_RECTANGLE, RECTANGLE, DIAMOND
}