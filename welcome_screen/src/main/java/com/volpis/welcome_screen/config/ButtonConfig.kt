package com.volpis.welcome_screen.config

import kotlinx.serialization.Serializable

@Serializable
data class ButtonConfig(
    val isVisible: Boolean = true,
    val text: String = "",
    val placement: ButtonPlacement = ButtonPlacement.BOTTOM_CENTER,
    val style: ButtonStyle = ButtonStyle()
)

class ButtonConfigBuilder {
    private var isVisible: Boolean = true
    private var text: String = ""
    private var placement: ButtonPlacement = ButtonPlacement.BOTTOM_CENTER
    private var style: ButtonStyle = ButtonStyle()

    fun isVisible(visible: Boolean) = apply { this.isVisible = visible }
    fun text(text: String) = apply { this.text = text }
    fun placement(placement: ButtonPlacement) = apply { this.placement = placement }
    fun style(style: ButtonStyle) = apply { this.style = style }
    fun style(block: ButtonStyleBuilder.() -> Unit) = apply {
        this.style = ButtonStyleBuilder().apply(block).build()
    }

    fun build() = ButtonConfig(
        isVisible = isVisible,
        text = text,
        placement = placement,
        style = style
    )
}