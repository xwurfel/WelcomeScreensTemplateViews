package com.volpis.welcome_screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class WelcomeScreenData(
    val imageRes: Int? = null,
    val imageVector: ImageVector? = null,
    val imageUrl: String? = null,
    val title: String,
    val description: String,
    val additionalContent: (@Composable () -> Unit)? = null,

    val backgroundColor: Color? = null,
    val textColor: Color? = null,

    val contentDescription: String? = null,
)

class WelcomeScreenDataBuilder {
    private var imageRes: Int? = null
    private var imageVector: ImageVector? = null
    private var imageUrl: String? = null
    private var title: String = ""
    private var description: String = ""
    private var subtitle: String? = null
    private var additionalContent: (@Composable () -> Unit)? = null
    private var backgroundColor: Color? = null
    private var textColor: Color? = null
    private var tags: List<String> = emptyList()
    private var contentDescription: String? = null
    private var semanticsLabel: String? = null

    fun imageRes(imageRes: Int) = apply { this.imageRes = imageRes }
    fun imageVector(imageVector: ImageVector) = apply { this.imageVector = imageVector }
    fun imageUrl(imageUrl: String) = apply { this.imageUrl = imageUrl }
    fun title(title: String) = apply { this.title = title }
    fun description(description: String) = apply { this.description = description }
    fun additionalContent(content: @Composable () -> Unit) =
        apply { this.additionalContent = content }

    fun backgroundColor(color: Color) =
        apply { this.backgroundColor = color }

    fun textColor(color: Color) = apply { this.textColor = color }
    fun contentDescription(description: String) = apply { this.contentDescription = description }

    fun build() = WelcomeScreenData(
        imageRes = imageRes,
        imageVector = imageVector,
        imageUrl = imageUrl,
        title = title,
        description = description,
        additionalContent = additionalContent,
        backgroundColor = backgroundColor,
        textColor = textColor,
        contentDescription = contentDescription,
    )
}

fun welcomeScreenData(builder: WelcomeScreenDataBuilder.() -> Unit): WelcomeScreenData {
    return WelcomeScreenDataBuilder().apply(builder).build()
}