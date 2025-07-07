package com.volpis.welcome_screen.config

import androidx.annotation.ColorInt
import androidx.annotation.Px
import kotlinx.serialization.Serializable

fun welcomeScreenConfig(block: WelcomeScreenConfigBuilder.() -> Unit): WelcomeScreenConfig {
    return WelcomeScreenConfigBuilder().apply(block).build()
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


class WelcomeScreenConfigBuilder {
    private var skipButton: ButtonConfig = ButtonConfig(
        isVisible = true,
        text = "Skip",
        placement = ButtonPlacement.TOP_RIGHT,
        style = ButtonStyle(
            backgroundColor = 0x00000000,
            textColor = 0xFF9E9E9E.toInt(),
            textSize = 42f,
            cornerRadius = 60f
        )
    )

    private var nextButton: ButtonConfig = ButtonConfig(
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
    )

    private var previousButton: ButtonConfig = ButtonConfig(
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
    )

    private var finishButton: ButtonConfig = ButtonConfig(
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
    )

    private var pageIndicator: PageIndicatorConfig = PageIndicatorConfig(
        isVisible = true,
        activeColor = 0xFF2196F3.toInt(),
        inactiveColor = 0x4D9E9E9E.toInt(),
        size = 24,
        spacing = 36,
        placement = IndicatorPlacement.CENTER_BELOW_DESCRIPTION,
        animationType = IndicatorAnimation.SLIDE,
        animationDuration = 300
    )

    private var titleTextColor: Int = 0xFF000000.toInt()
    private var titleTextSize: Float = 84f
    private var titleTypeface: WelcomeTypeface = WelcomeTypeface.DEFAULT

    private var descriptionTextColor: Int = 0xFF9E9E9E.toInt()
    private var descriptionTextSize: Float = 48f
    private var descriptionTypeface: WelcomeTypeface = WelcomeTypeface.DEFAULT

    private var backgroundColor: Int = 0xFFFFFFFF.toInt()
    private var backgroundGradientColor: Int? = null
    private var useGradientBackground: Boolean = false
    private var contentPadding: Int = 48

    private var imageCornerRadius: Float = 48f
    private var imageElevation: Float = 12f
    private var imageScaleType: ImageScaleType = ImageScaleType.FIT
    private var imageSizeConfig: ImageSizeConfig = ImageSizeConfig()

    private var pageTransitionDuration: Int = 300
    private var enableParallaxEffect: Boolean = false
    private var parallaxIntensity: Float = 0.5f

    private var customSpacing: WelcomeScreenSpacing = WelcomeScreenSpacing()

    // Button configuration methods
    fun skipButton(config: ButtonConfig) = apply { this.skipButton = config }
    fun skipButton(block: ButtonConfigBuilder.() -> Unit) = apply {
        this.skipButton = ButtonConfigBuilder().apply(block).build()
    }

    fun nextButton(config: ButtonConfig) = apply { this.nextButton = config }
    fun nextButton(block: ButtonConfigBuilder.() -> Unit) = apply {
        this.nextButton = ButtonConfigBuilder().apply(block).build()
    }

    fun previousButton(config: ButtonConfig) = apply { this.previousButton = config }
    fun previousButton(block: ButtonConfigBuilder.() -> Unit) = apply {
        this.previousButton = ButtonConfigBuilder().apply(block).build()
    }

    fun finishButton(config: ButtonConfig) = apply { this.finishButton = config }
    fun finishButton(block: ButtonConfigBuilder.() -> Unit) = apply {
        this.finishButton = ButtonConfigBuilder().apply(block).build()
    }

    // Page indicator configuration
    fun pageIndicator(config: PageIndicatorConfig) = apply { this.pageIndicator = config }
    fun pageIndicator(block: PageIndicatorConfigBuilder.() -> Unit) = apply {
        this.pageIndicator = PageIndicatorConfigBuilder().apply(block).build()
    }

    // Title text configuration
    fun titleTextColor(@ColorInt color: Int) = apply { this.titleTextColor = color }
    fun titleTextSize(@Px size: Float) = apply { this.titleTextSize = size }
    fun titleTypeface(typeface: WelcomeTypeface) = apply { this.titleTypeface = typeface }

    // Description text configuration
    fun descriptionTextColor(@ColorInt color: Int) = apply { this.descriptionTextColor = color }
    fun descriptionTextSize(@Px size: Float) = apply { this.descriptionTextSize = size }
    fun descriptionTypeface(typeface: WelcomeTypeface) =
        apply { this.descriptionTypeface = typeface }

    // Background configuration
    fun backgroundColor(@ColorInt color: Int) = apply { this.backgroundColor = color }
    fun backgroundGradientColor(@ColorInt color: Int?) =
        apply { this.backgroundGradientColor = color }

    fun useGradientBackground(enabled: Boolean) = apply { this.useGradientBackground = enabled }
    fun contentPadding(@Px padding: Int) = apply { this.contentPadding = padding }

    // Image configuration
    fun imageCornerRadius(@Px radius: Float) = apply { this.imageCornerRadius = radius }
    fun imageElevation(@Px elevation: Float) = apply { this.imageElevation = elevation }
    fun imageScaleType(scaleType: ImageScaleType) = apply { this.imageScaleType = scaleType }
    fun imageSizeConfig(config: ImageSizeConfig) = apply { this.imageSizeConfig = config }

    // Animation and transition configuration
    fun pageTransitionDuration(duration: Int) = apply { this.pageTransitionDuration = duration }
    fun enableParallaxEffect(enabled: Boolean) = apply { this.enableParallaxEffect = enabled }
    fun parallaxIntensity(intensity: Float) = apply { this.parallaxIntensity = intensity }

    // Spacing configuration
    fun customSpacing(spacing: WelcomeScreenSpacing) = apply { this.customSpacing = spacing }
    fun customSpacing(block: WelcomeScreenSpacingBuilder.() -> Unit) = apply {
        this.customSpacing = WelcomeScreenSpacingBuilder().apply(block).build()
    }

    fun build() = WelcomeScreenConfig(
        skipButton = skipButton,
        nextButton = nextButton,
        previousButton = previousButton,
        finishButton = finishButton,
        pageIndicator = pageIndicator,
        titleTextColor = titleTextColor,
        titleTextSize = titleTextSize,
        titleTypeface = titleTypeface,
        descriptionTextColor = descriptionTextColor,
        descriptionTextSize = descriptionTextSize,
        descriptionTypeface = descriptionTypeface,
        backgroundColor = backgroundColor,
        backgroundGradientColor = backgroundGradientColor,
        useGradientBackground = useGradientBackground,
        contentPadding = contentPadding,
        imageCornerRadius = imageCornerRadius,
        imageElevation = imageElevation,
        imageScaleType = imageScaleType,
        imageSizeConfig = imageSizeConfig,
        pageTransitionDuration = pageTransitionDuration,
        enableParallaxEffect = enableParallaxEffect,
        parallaxIntensity = parallaxIntensity,
        customSpacing = customSpacing
    )
}