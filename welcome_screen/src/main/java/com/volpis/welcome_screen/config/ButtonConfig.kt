package com.volpis.welcome_screen.config

data class ButtonConfig(
    val isVisible: Boolean = true,
    val text: String = "",
    val placement: ButtonPlacement = ButtonPlacement.BOTTOM_CENTER,
    val style: ButtonStyle = ButtonStyle()
)