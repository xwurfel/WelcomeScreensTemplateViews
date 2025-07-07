package com.volpis.welcome_screen.config

import androidx.annotation.ColorInt
import androidx.annotation.Px
import kotlinx.serialization.Serializable

@Serializable
data class ButtonStyle(
    @ColorInt val backgroundColor: Int = 0xFF2196F3.toInt(),
    @ColorInt val textColor: Int = 0xFFFFFFFF.toInt(),
    @Px val textSize: Float = 48f,
    val typeface: WelcomeTypeface = WelcomeTypeface.DEFAULT,
    val isUnderlined: Boolean = false,
    val isBold: Boolean = false,
    @Px val borderWidth: Int = 0,
    @ColorInt val borderColor: Int = 0x00000000,
    @Px val cornerRadius: Float = 24f,
    @Px val paddingHorizontal: Int = 48,
    @Px val paddingVertical: Int = 24,
    val fontWeight: Int = 400
)

class ButtonStyleBuilder {
    private var backgroundColor: Int = 0xFF2196F3.toInt()
    private var textColor: Int = 0xFFFFFFFF.toInt()
    private var textSize: Float = 48f
    private var typeface: WelcomeTypeface = WelcomeTypeface.DEFAULT
    private var isUnderlined: Boolean = false
    private var isBold: Boolean = false
    private var borderWidth: Int = 0
    private var borderColor: Int = 0x00000000
    private var cornerRadius: Float = 24f
    private var paddingHorizontal: Int = 48
    private var paddingVertical: Int = 24
    private var fontWeight: Int = 400

    fun backgroundColor(@ColorInt color: Int) = apply { this.backgroundColor = color }
    fun textColor(@ColorInt color: Int) = apply { this.textColor = color }
    fun textSize(@Px size: Float) = apply { this.textSize = size }
    fun typeface(typeface: WelcomeTypeface) = apply { this.typeface = typeface }
    fun isUnderlined(underlined: Boolean) = apply { this.isUnderlined = underlined }
    fun isBold(bold: Boolean) = apply { this.isBold = bold }
    fun borderWidth(@Px width: Int) = apply { this.borderWidth = width }
    fun borderColor(@ColorInt color: Int) = apply { this.borderColor = color }
    fun cornerRadius(@Px radius: Float) = apply { this.cornerRadius = radius }
    fun paddingHorizontal(@Px padding: Int) = apply { this.paddingHorizontal = padding }
    fun paddingVertical(@Px padding: Int) = apply { this.paddingVertical = padding }
    fun fontWeight(weight: Int) = apply { this.fontWeight = weight }

    fun build() = ButtonStyle(
        backgroundColor = backgroundColor,
        textColor = textColor,
        textSize = textSize,
        typeface = typeface,
        isUnderlined = isUnderlined,
        isBold = isBold,
        borderWidth = borderWidth,
        borderColor = borderColor,
        cornerRadius = cornerRadius,
        paddingHorizontal = paddingHorizontal,
        paddingVertical = paddingVertical,
        fontWeight = fontWeight
    )
}