package com.volpis.welcome_screen.config

import androidx.annotation.Px
import kotlinx.serialization.Serializable

@Serializable
data class WelcomeScreenSpacing(
    @Px val imageToTitleSpacing: Int = 96,
    @Px val titleToDescriptionSpacing: Int = 48,
    @Px val descriptionToIndicatorSpacing: Int = 72,
    @Px val indicatorToButtonSpacing: Int = 96,
    @Px val horizontalPadding: Int = 72,
    @Px val verticalPadding: Int = 96
)

class WelcomeScreenSpacingBuilder {
    private var imageToTitleSpacing: Int = 96
    private var titleToDescriptionSpacing: Int = 48
    private var descriptionToIndicatorSpacing: Int = 72
    private var indicatorToButtonSpacing: Int = 96
    private var horizontalPadding: Int = 72
    private var verticalPadding: Int = 96

    fun imageToTitleSpacing(@Px spacing: Int) = apply { this.imageToTitleSpacing = spacing }
    fun titleToDescriptionSpacing(@Px spacing: Int) =
        apply { this.titleToDescriptionSpacing = spacing }

    fun descriptionToIndicatorSpacing(@Px spacing: Int) =
        apply { this.descriptionToIndicatorSpacing = spacing }

    fun indicatorToButtonSpacing(@Px spacing: Int) =
        apply { this.indicatorToButtonSpacing = spacing }

    fun horizontalPadding(@Px padding: Int) = apply { this.horizontalPadding = padding }
    fun verticalPadding(@Px padding: Int) = apply { this.verticalPadding = padding }

    fun build() = WelcomeScreenSpacing(
        imageToTitleSpacing = imageToTitleSpacing,
        titleToDescriptionSpacing = titleToDescriptionSpacing,
        descriptionToIndicatorSpacing = descriptionToIndicatorSpacing,
        indicatorToButtonSpacing = indicatorToButtonSpacing,
        horizontalPadding = horizontalPadding,
        verticalPadding = verticalPadding
    )
}