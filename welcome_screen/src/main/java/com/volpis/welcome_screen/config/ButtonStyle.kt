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