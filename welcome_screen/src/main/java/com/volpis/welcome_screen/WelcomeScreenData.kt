package com.volpis.welcome_screen

import android.graphics.drawable.Drawable

data class WelcomeScreenData(
    val imageRes: Int? = null,
    val imageDrawable: Drawable? = null,
    val imageUrl: String? = null,
    val title: String,
    val description: String,
    val backgroundColor: Int? = null,
    val textColor: Int? = null,
    val contentDescription: String? = null,
)

class WelcomeScreenDataBuilder {
    private var imageRes: Int? = null
    private var imageDrawable: Drawable? = null
    private var imageUrl: String? = null
    private var title: String = ""
    private var description: String = ""
    private var backgroundColor: Int? = null
    private var textColor: Int? = null
    private var contentDescription: String? = null

    fun imageRes(imageRes: Int) = apply { this.imageRes = imageRes }
    fun imageDrawable(imageDrawable: Drawable) = apply { this.imageDrawable = imageDrawable }
    fun imageUrl(imageUrl: String) = apply { this.imageUrl = imageUrl }
    fun title(title: String) = apply { this.title = title }
    fun description(description: String) = apply { this.description = description }
    fun backgroundColor(color: Int) = apply { this.backgroundColor = color }
    fun textColor(color: Int) = apply { this.textColor = color }
    fun contentDescription(description: String) = apply { this.contentDescription = description }

    fun build() = WelcomeScreenData(
        imageRes = imageRes,
        imageDrawable = imageDrawable,
        imageUrl = imageUrl,
        title = title,
        description = description,
        backgroundColor = backgroundColor,
        textColor = textColor,
        contentDescription = contentDescription,
    )
}


fun welcomeScreenData(builder: WelcomeScreenDataBuilder.() -> Unit): WelcomeScreenData {
    return WelcomeScreenDataBuilder().apply(builder).build()
}