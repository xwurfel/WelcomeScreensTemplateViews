package com.volpis.welcome_screen.config

import android.graphics.Typeface

object WelcomeScreenConfigFactory {

    fun createMinimalConfig(): WelcomeScreenConfig {
        return WelcomeScreenConfig(
            skipButton = ButtonConfig(isVisible = false),
            nextButton = ButtonConfig(isVisible = false),
            previousButton = ButtonConfig(isVisible = false),
            finishButton = ButtonConfig(
                isVisible = true,
                text = "Get Started",
                style = ButtonStyle(
                    backgroundColor = 0xFF007AFF.toInt(),
                    textColor = 0xFFFFFFFF.toInt(),
                    cornerRadius = 84f,
                    paddingHorizontal = 120,
                    paddingVertical = 48,
                    textSize = 48f,
                    fontWeight = 600
                )
            ),
            pageIndicator = PageIndicatorConfig(
                animationType = IndicatorAnimation.FADE,
                activeColor = 0xFF007AFF.toInt(),
                inactiveColor = 0x4D9E9E9E.toInt()
            ),
            imageSizeConfig = ImageSizeConfig.small()
        )
    }

    fun createModernConfig(): WelcomeScreenConfig {
        return WelcomeScreenConfig(
            skipButton = ButtonConfig(
                isVisible = true,
                text = "Skip",
                placement = ButtonPlacement.TOP_RIGHT,
                style = ButtonStyle(
                    backgroundColor = 0x00000000,
                    textColor = 0xFF8E8E93.toInt(),
                    textSize = 48f,
                    cornerRadius = 0f
                )
            ),
            nextButton = ButtonConfig(isVisible = false),
            previousButton = ButtonConfig(isVisible = false),
            finishButton = ButtonConfig(
                isVisible = true,
                text = "Next",
                placement = ButtonPlacement.BOTTOM_CENTER,
                style = ButtonStyle(
                    backgroundColor = 0xFF007AFF.toInt(),
                    textColor = 0xFFFFFFFF.toInt(),
                    cornerRadius = 42f,
                    paddingHorizontal = 0,
                    paddingVertical = 48,
                    textSize = 48f,
                    fontWeight = 600
                )
            ),
            pageIndicator = PageIndicatorConfig(
                activeColor = 0xFF007AFF.toInt(),
                inactiveColor = 0xFFE5E5EA.toInt(),
                animationType = IndicatorAnimation.SLIDE,
                size = 18,
                spacing = 24
            ),
            titleTextColor = 0xFF1C1C1E.toInt(),
            titleTextSize = 72f,
            titleTypeface = Typeface.DEFAULT_BOLD,
            descriptionTextColor = 0xFF8E8E93.toInt(),
            descriptionTextSize = 48f,
            backgroundColor = 0xFFFFFFFF.toInt(),
            imageCornerRadius = 60f,
            imageElevation = 0f,
            imageSizeConfig = ImageSizeConfig.banner(),
            customSpacing = WelcomeScreenSpacing(
                imageToTitleSpacing = 120,
                titleToDescriptionSpacing = 36,
                descriptionToIndicatorSpacing = 96,
                indicatorToButtonSpacing = 120,
                horizontalPadding = 96,
                verticalPadding = 144
            )
        )
    }

    fun createDarkThemeConfig(): WelcomeScreenConfig {
        return WelcomeScreenConfig(
            skipButton = ButtonConfig(
                isVisible = true,
                text = "Skip",
                placement = ButtonPlacement.TOP_RIGHT,
                style = ButtonStyle(
                    backgroundColor = 0x00000000,
                    textColor = 0xFF8E8E93.toInt(),
                    textSize = 48f
                )
            ),
            nextButton = ButtonConfig(
                isVisible = true,
                text = "Next",
                placement = ButtonPlacement.BOTTOM_RIGHT,
                style = ButtonStyle(
                    backgroundColor = 0xFF0A84FF.toInt(),
                    textColor = 0xFFFFFFFF.toInt(),
                    cornerRadius = 72f,
                    paddingHorizontal = 72,
                    paddingVertical = 36
                )
            ),
            previousButton = ButtonConfig(
                isVisible = true,
                text = "Back",
                placement = ButtonPlacement.BOTTOM_LEFT,
                style = ButtonStyle(
                    backgroundColor = 0x00000000,
                    textColor = 0xFF8E8E93.toInt(),
                    borderWidth = 3,
                    borderColor = 0xFF3A3A3C.toInt(),
                    cornerRadius = 72f,
                    paddingHorizontal = 60,
                    paddingVertical = 36
                )
            ),
            finishButton = ButtonConfig(
                isVisible = true,
                text = "Get Started",
                style = ButtonStyle(
                    backgroundColor = 0xFF30D158.toInt(),
                    textColor = 0xFF000000.toInt(),
                    cornerRadius = 84f,
                    paddingHorizontal = 96,
                    paddingVertical = 48,
                    fontWeight = 600
                )
            ),
            pageIndicator = PageIndicatorConfig(
                activeColor = 0xFF0A84FF.toInt(),
                inactiveColor = 0xFF3A3A3C.toInt(),
                animationType = IndicatorAnimation.MORPHING
            ),
            titleTextColor = 0xFFFFFFFF.toInt(),
            titleTextSize = 84f,
            titleTypeface = Typeface.DEFAULT_BOLD,
            descriptionTextColor = 0xFF8E8E93.toInt(),
            descriptionTextSize = 48f,
            backgroundColor = 0xFF1C1C1E.toInt(),
            useGradientBackground = true,
            backgroundGradientColor = 0xFF2C2C2E.toInt(),
            imageSizeConfig = ImageSizeConfig.portrait()
        )
    }

    fun createCustomThemeConfig(
        primaryColor: Int,
        backgroundColor: Int,
        textColor: Int,
        accentColor: Int = primaryColor
    ): WelcomeScreenConfig {
        return WelcomeScreenConfig(
            skipButton = ButtonConfig(
                isVisible = true,
                text = "Skip",
                placement = ButtonPlacement.TOP_RIGHT,
                style = ButtonStyle(
                    backgroundColor = 0x00000000,
                    textColor = (textColor and 0x00FFFFFF) or 0xB3000000.toInt(),
                    textSize = 42f
                )
            ),
            nextButton = ButtonConfig(
                text = "Continue",
                style = ButtonStyle(
                    backgroundColor = primaryColor,
                    textColor = 0xFFFFFFFF.toInt(),
                    cornerRadius = 72f,
                    paddingHorizontal = 72,
                    paddingVertical = 36
                )
            ),
            finishButton = ButtonConfig(
                text = "Start Journey",
                style = ButtonStyle(
                    backgroundColor = accentColor,
                    textColor = 0xFFFFFFFF.toInt(),
                    cornerRadius = 84f,
                    paddingHorizontal = 96,
                    paddingVertical = 48,
                    fontWeight = 600
                )
            ),
            pageIndicator = PageIndicatorConfig(
                activeColor = primaryColor,
                inactiveColor = (primaryColor and 0x00FFFFFF) or 0x4D000000,
                animationType = IndicatorAnimation.SLIDE
            ),
            titleTextColor = textColor,
            titleTextSize = 84f,
            titleTypeface = Typeface.DEFAULT_BOLD,
            descriptionTextColor = (textColor and 0x00FFFFFF) or 0xB3000000.toInt(),
            descriptionTextSize = 48f,
            backgroundColor = backgroundColor,
            imageSizeConfig = ImageSizeConfig()
        )
    }
}