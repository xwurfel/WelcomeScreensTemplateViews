package com.volpis.welcome_screen.config

import androidx.annotation.ColorInt
import androidx.annotation.Px
import kotlinx.serialization.Serializable

fun welcomeScreenConfig(block: WelcomeScreenConfig.() -> Unit): WelcomeScreenConfig {
    return WelcomeScreenConfig().apply(block)
}

@Serializable
data class WelcomeScreenConfig(
    val skipButton: ButtonConfig = ButtonConfig(
        isVisible = true,
        text = "Skip",
        placement = ButtonPlacement.TOP_RIGHT,
        style = ButtonStyle(
            backgroundColor = 0x00000000,
            textColor = 0xFF9E9E9E.toInt(),
            textSize = 42f,
            cornerRadius = 60f
        )
    ),
    val nextButton: ButtonConfig = ButtonConfig(
        isVisible = true,
        text = "Next",
        placement = ButtonPlacement.BOTTOM_RIGHT,
        style = ButtonStyle(
            backgroundColor = 0xFF2196F3.toInt(),
            textColor = 0xFFFFFFFF.toInt(),
            cornerRadius = 72f,
            paddingHorizontal = 72,
            paddingVertical = 36
        )
    ),
    val previousButton: ButtonConfig = ButtonConfig(
        isVisible = true,
        text = "Previous",
        placement = ButtonPlacement.BOTTOM_LEFT,
        style = ButtonStyle(
            backgroundColor = 0x00000000,
            textColor = 0xFF9E9E9E.toInt(),
            borderWidth = 3,
            borderColor = 0xFF9E9E9E.toInt(),
            cornerRadius = 72f,
            paddingHorizontal = 60,
            paddingVertical = 36
        )
    ),
    val finishButton: ButtonConfig = ButtonConfig(
        isVisible = true,
        text = "Get Started",
        placement = ButtonPlacement.BOTTOM_CENTER,
        style = ButtonStyle(
            backgroundColor = 0xFF2196F3.toInt(),
            textColor = 0xFFFFFFFF.toInt(),
            cornerRadius = 84f,
            paddingHorizontal = 96,
            paddingVertical = 48,
            textSize = 48f,
            fontWeight = 600
        )
    ),

    val pageIndicator: PageIndicatorConfig = PageIndicatorConfig(
        isVisible = true,
        activeColor = 0xFF2196F3.toInt(),
        inactiveColor = 0x4D9E9E9E.toInt(),
        size = 24,
        spacing = 36,
        placement = IndicatorPlacement.CENTER_BELOW_DESCRIPTION,
        animationType = IndicatorAnimation.SLIDE,
        animationDuration = 300
    ),

    @ColorInt val titleTextColor: Int = 0xFF000000.toInt(),
    @Px val titleTextSize: Float = 84f,
    val titleTypeface: WelcomeTypeface = WelcomeTypeface.DEFAULT,

    @ColorInt val descriptionTextColor: Int = 0xFF9E9E9E.toInt(),
    @Px val descriptionTextSize: Float = 48f,
    val descriptionTypeface: WelcomeTypeface = WelcomeTypeface.DEFAULT,

    @ColorInt val backgroundColor: Int = 0xFFFFFFFF.toInt(),
    @ColorInt val backgroundGradientColor: Int? = null,
    val useGradientBackground: Boolean = false,
    @Px val contentPadding: Int = 48,

    // Image configuration
    @Px val imageCornerRadius: Float = 48f,
    @Px val imageElevation: Float = 12f,
    val imageScaleType: ImageScaleType = ImageScaleType.FIT,
    val imageSizeConfig: ImageSizeConfig = ImageSizeConfig(),

    val pageTransitionDuration: Int = 300,
    val enableParallaxEffect: Boolean = false,
    val parallaxIntensity: Float = 0.5f,

    val customSpacing: WelcomeScreenSpacing = WelcomeScreenSpacing(),
)

@Serializable
data class WelcomeScreenSpacing(
    @Px val imageToTitleSpacing: Int = 96,
    @Px val titleToDescriptionSpacing: Int = 48,
    @Px val descriptionToIndicatorSpacing: Int = 72,
    @Px val indicatorToButtonSpacing: Int = 96,
    @Px val horizontalPadding: Int = 72,
    @Px val verticalPadding: Int = 96
)

enum class ImageScaleType {
    FIT, CROP, FILL_WIDTH, FILL_HEIGHT
}