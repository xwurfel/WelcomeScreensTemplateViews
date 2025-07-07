package com.volpis.welcome_screen.config

import androidx.annotation.ColorInt
import androidx.annotation.Px
import kotlinx.serialization.Serializable

@Serializable
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

class PageIndicatorConfigBuilder {
    private var isVisible: Boolean = true
    private var activeColor: Int = 0xFF2196F3.toInt()
    private var inactiveColor: Int = 0x4D9E9E9E.toInt()
    private var size: Int = 24
    private var spacing: Int = 36
    private var placement: IndicatorPlacement = IndicatorPlacement.CENTER_BELOW_DESCRIPTION
    private var animationType: IndicatorAnimation = IndicatorAnimation.SLIDE
    private var animationDuration: Int = 300
    private var strokeWidth: Int = 0
    private var strokeColor: Int = 0x00000000
    private var enableGlow: Boolean = false
    private var glowColor: Int = 0xFFFFFFFF.toInt()
    private var glowRadius: Float = 12f
    private var shape: IndicatorShape = IndicatorShape.CIRCLE
    private var customCornerRadius: Float = 12f
    private var useProgressiveSizing: Boolean = false
    private var maxSize: Int = 36
    private var minSize: Int = 18

    fun isVisible(visible: Boolean) = apply { this.isVisible = visible }
    fun activeColor(@ColorInt color: Int) = apply { this.activeColor = color }
    fun inactiveColor(@ColorInt color: Int) = apply { this.inactiveColor = color }
    fun size(@Px size: Int) = apply { this.size = size }
    fun spacing(@Px spacing: Int) = apply { this.spacing = spacing }
    fun placement(placement: IndicatorPlacement) = apply { this.placement = placement }
    fun animationType(type: IndicatorAnimation) = apply { this.animationType = type }
    fun animationDuration(duration: Int) = apply { this.animationDuration = duration }
    fun strokeWidth(@Px width: Int) = apply { this.strokeWidth = width }
    fun strokeColor(@ColorInt color: Int) = apply { this.strokeColor = color }
    fun enableGlow(enabled: Boolean) = apply { this.enableGlow = enabled }
    fun glowColor(@ColorInt color: Int) = apply { this.glowColor = color }
    fun glowRadius(@Px radius: Float) = apply { this.glowRadius = radius }
    fun shape(shape: IndicatorShape) = apply { this.shape = shape }
    fun customCornerRadius(@Px radius: Float) = apply { this.customCornerRadius = radius }
    fun useProgressiveSizing(enabled: Boolean) = apply { this.useProgressiveSizing = enabled }
    fun maxSize(@Px size: Int) = apply { this.maxSize = size }
    fun minSize(@Px size: Int) = apply { this.minSize = size }

    fun build() = PageIndicatorConfig(
        isVisible = isVisible,
        activeColor = activeColor,
        inactiveColor = inactiveColor,
        size = size,
        spacing = spacing,
        placement = placement,
        animationType = animationType,
        animationDuration = animationDuration,
        strokeWidth = strokeWidth,
        strokeColor = strokeColor,
        enableGlow = enableGlow,
        glowColor = glowColor,
        glowRadius = glowRadius,
        shape = shape,
        customCornerRadius = customCornerRadius,
        useProgressiveSizing = useProgressiveSizing,
        maxSize = maxSize,
        minSize = minSize
    )
}