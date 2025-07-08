package com.volpis.welcome_screen.config

import android.graphics.Typeface

object WelcomeScreenConfigFactory {

    fun createMinimalConfig(): WelcomeScreenConfig {
        return WelcomeScreenConfig(
            skipButton = ButtonConfig(isVisible = false),
            nextButton = ButtonConfig(isVisible = false),
            previousButton = ButtonConfig(isVisible = false),
            finishButton = ButtonConfig(
                isVisible = true, text = "Get Started", style = ButtonStyle(
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
            titleTypeface = Typeface.DEFAULT_BOLD.asWelcomeTypeface(),
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
                    backgroundColor = 0x00000000, textColor = 0xFF8E8E93.toInt(), textSize = 48f
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
                isVisible = true, text = "Get Started", style = ButtonStyle(
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
            titleTypeface = Typeface.DEFAULT_BOLD.asWelcomeTypeface(),
            descriptionTextColor = 0xFF8E8E93.toInt(),
            descriptionTextSize = 48f,
            backgroundColor = 0xFF1C1C1E.toInt(),
            useGradientBackground = true,
            backgroundGradientColor = 0xFF2C2C2E.toInt(),
            imageSizeConfig = ImageSizeConfig.portrait()
        )
    }

    fun createCustomThemeConfig(
        primaryColor: Int, backgroundColor: Int, textColor: Int, accentColor: Int = primaryColor
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
                text = "Continue", style = ButtonStyle(
                    backgroundColor = primaryColor,
                    textColor = 0xFFFFFFFF.toInt(),
                    cornerRadius = 72f,
                    paddingHorizontal = 72,
                    paddingVertical = 36
                )
            ),
            finishButton = ButtonConfig(
                text = "Start Journey", style = ButtonStyle(
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
            titleTypeface = Typeface.DEFAULT_BOLD.asWelcomeTypeface(),
            descriptionTextColor = (textColor and 0x00FFFFFF) or 0xB3000000.toInt(),
            descriptionTextSize = 48f,
            backgroundColor = backgroundColor,
            imageSizeConfig = ImageSizeConfig()
        )
    }

    fun createMaterial3Theme(): WelcomeScreenConfig {
        return WelcomeScreenConfig(
            skipButton = ButtonConfig(
                isVisible = true,
                text = "Skip",
                placement = ButtonPlacement.TOP_RIGHT,
                style = ButtonStyle(
                    backgroundColor = 0x00000000,
                    textColor = 0xFF6750A4.toInt(),
                    textSize = 42f,
                    cornerRadius = 60f,
                    isUnderlined = true
                )
            ),
            nextButton = ButtonConfig(
                isVisible = true,
                text = "Continue",
                placement = ButtonPlacement.BOTTOM_RIGHT,
                style = ButtonStyle(
                    backgroundColor = 0xFF6750A4.toInt(),
                    textColor = 0xFFFFFFFF.toInt(),
                    cornerRadius = 60f,
                    paddingHorizontal = 72,
                    paddingVertical = 48,
                    fontWeight = 500
                )
            ),
            previousButton = ButtonConfig(
                isVisible = true,
                text = "Back",
                placement = ButtonPlacement.BOTTOM_LEFT,
                style = ButtonStyle(
                    backgroundColor = 0x00000000,
                    textColor = 0xFF6750A4.toInt(),
                    borderWidth = 3,
                    borderColor = 0xFF6750A4.toInt(),
                    cornerRadius = 60f,
                    paddingHorizontal = 60,
                    paddingVertical = 48
                )
            ),
            finishButton = ButtonConfig(
                isVisible = true, text = "Get Started", style = ButtonStyle(
                    backgroundColor = 0xFF6750A4.toInt(),
                    textColor = 0xFFFFFFFF.toInt(),
                    cornerRadius = 60f,
                    paddingHorizontal = 96,
                    paddingVertical = 48,
                    fontWeight = 600
                )
            ),
            pageIndicator = PageIndicatorConfig(
                activeColor = 0xFF6750A4.toInt(),
                inactiveColor = 0xFFE7E0EC.toInt(),
                animationType = IndicatorAnimation.SLIDE,
                shape = IndicatorShape.ROUNDED_RECTANGLE,
                customCornerRadius = 12f,
                useProgressiveSizing = true,
                maxSize = 36,
                minSize = 24
            ),
            titleTextColor = 0xFF1C1B1F.toInt(),
            titleTextSize = 72f,
            descriptionTextColor = 0xFF49454F.toInt(),
            descriptionTextSize = 42f,
            backgroundColor = 0xFFFFFBFE.toInt(),
            imageCornerRadius = 48f,
            imageElevation = 24f,
            imageSizeConfig = ImageSizeConfig()
        )
    }

    fun createNeonDarkTheme(): WelcomeScreenConfig {
        return WelcomeScreenConfig(
            skipButton = ButtonConfig(
                isVisible = true,
                text = "Skip",
                placement = ButtonPlacement.TOP_RIGHT,
                style = ButtonStyle(
                    backgroundColor = 0x00000000, textColor = 0xFF9CA3AF.toInt(), textSize = 42f
                )
            ),
            nextButton = ButtonConfig(
                isVisible = true,
                text = "Next",
                placement = ButtonPlacement.BOTTOM_RIGHT,
                style = ButtonStyle(
                    backgroundColor = 0xFF10B981.toInt(),
                    textColor = 0xFF000000.toInt(),
                    cornerRadius = 72f,
                    paddingHorizontal = 72,
                    paddingVertical = 36,
                    fontWeight = 600
                )
            ),
            previousButton = ButtonConfig(
                isVisible = true,
                text = "Back",
                placement = ButtonPlacement.BOTTOM_LEFT,
                style = ButtonStyle(
                    backgroundColor = 0x00000000,
                    textColor = 0xFF9CA3AF.toInt(),
                    borderWidth = 2,
                    borderColor = 0xFF374151.toInt(),
                    cornerRadius = 72f,
                    paddingHorizontal = 60,
                    paddingVertical = 36
                )
            ),
            finishButton = ButtonConfig(
                isVisible = true, text = "Launch", style = ButtonStyle(
                    backgroundColor = 0xFF10B981.toInt(),
                    textColor = 0xFF000000.toInt(),
                    cornerRadius = 84f,
                    paddingHorizontal = 96,
                    paddingVertical = 48,
                    fontWeight = 700
                )
            ),
            pageIndicator = PageIndicatorConfig(
                activeColor = 0xFF10B981.toInt(),
                inactiveColor = 0xFF374151.toInt(),
                animationType = IndicatorAnimation.PULSE,
                shape = IndicatorShape.CIRCLE,
                enableGlow = true,
                glowColor = 0xFF10B981.toInt(),
                glowRadius = 12f
            ),
            titleTextColor = 0xFFFFFFFF.toInt(),
            titleTextSize = 84f,
            titleTypeface = WelcomeTypeface.DEFAULT_BOLD,
            descriptionTextColor = 0xFF9CA3AF.toInt(),
            descriptionTextSize = 48f,
            backgroundColor = 0xFF111827.toInt(),
            useGradientBackground = true,
            backgroundGradientColor = 0xFF1F2937.toInt(),
            imageCornerRadius = 72f,
            imageElevation = 48f,
            imageSizeConfig = ImageSizeConfig.portrait()
        )
    }

    fun createMonochromeTheme(): WelcomeScreenConfig {
        return WelcomeScreenConfig(
            skipButton = ButtonConfig(
                isVisible = true,
                text = "Skip",
                placement = ButtonPlacement.TOP_RIGHT,
                style = ButtonStyle(
                    backgroundColor = 0x00000000,
                    textColor = 0xFF6B7280.toInt(),
                    textSize = 36f,
                    isUnderlined = true
                )
            ),
            nextButton = ButtonConfig(isVisible = false),
            previousButton = ButtonConfig(isVisible = false),
            finishButton = ButtonConfig(
                isVisible = true,
                text = "Begin",
                placement = ButtonPlacement.BOTTOM_CENTER,
                style = ButtonStyle(
                    backgroundColor = 0xFF000000.toInt(),
                    textColor = 0xFFFFFFFF.toInt(),
                    paddingHorizontal = 120,
                    paddingVertical = 48,
                    textSize = 42f,
                    fontWeight = 400
                )
            ),
            pageIndicator = PageIndicatorConfig(
                activeColor = 0xFF000000.toInt(),
                inactiveColor = 0xFFE5E7EB.toInt(),
                animationType = IndicatorAnimation.FADE,
                shape = IndicatorShape.RECTANGLE,
                size = 18,
                spacing = 24
            ),
            titleTextColor = 0xFF000000.toInt(),
            titleTextSize = 72f,
            titleTypeface = WelcomeTypeface.SERIF,
            descriptionTextColor = 0xFF6B7280.toInt(),
            descriptionTextSize = 42f,
            descriptionTypeface = WelcomeTypeface.SERIF,
            backgroundColor = 0xFFFFFFFF.toInt(),
            imageElevation = 0f,
            imageSizeConfig = ImageSizeConfig.small(),
            customSpacing = WelcomeScreenSpacing(
                imageToTitleSpacing = 96,
                titleToDescriptionSpacing = 24,
                descriptionToIndicatorSpacing = 48,
                horizontalPadding = 120,
                verticalPadding = 96
            )
        )
    }


    fun createPlayfulTheme(): WelcomeScreenConfig {
        return WelcomeScreenConfig(
            skipButton = ButtonConfig(
                isVisible = true,
                text = "Skip",
                placement = ButtonPlacement.TOP_RIGHT,
                style = ButtonStyle(
                    backgroundColor = 0x4DFFFFFF.toInt(),
                    textColor = 0xFFFFFFFF.toInt(),
                    textSize = 42f,
                    cornerRadius = 72f,
                    paddingHorizontal = 48,
                    paddingVertical = 24
                )
            ),
            nextButton = ButtonConfig(
                isVisible = true,
                text = "Next",
                placement = ButtonPlacement.BOTTOM_RIGHT,
                style = ButtonStyle(
                    backgroundColor = 0xFFFF6B6B.toInt(),
                    textColor = 0xFFFFFFFF.toInt(),
                    cornerRadius = 96f,
                    paddingHorizontal = 72,
                    paddingVertical = 36,
                    fontWeight = 600
                )
            ),
            previousButton = ButtonConfig(
                isVisible = true,
                text = "Back",
                placement = ButtonPlacement.BOTTOM_LEFT,
                style = ButtonStyle(
                    backgroundColor = 0x4DFFFFFF.toInt(),
                    textColor = 0xFFFFFFFF.toInt(),
                    cornerRadius = 96f,
                    paddingHorizontal = 60,
                    paddingVertical = 36
                )
            ),
            finishButton = ButtonConfig(
                isVisible = true, text = "Let's Go! 🚀", style = ButtonStyle(
                    backgroundColor = 0xFFFF6B6B.toInt(),
                    textColor = 0xFFFFFFFF.toInt(),
                    cornerRadius = 96f,
                    paddingHorizontal = 96,
                    paddingVertical = 48,
                    textSize = 54f,
                    fontWeight = 700
                )
            ),
            pageIndicator = PageIndicatorConfig(
                activeColor = 0xFFFFFFFF.toInt(),
                inactiveColor = 0x80FFFFFF.toInt(),
                animationType = IndicatorAnimation.BOUNCE,
                shape = IndicatorShape.CIRCLE,
                size = 30,
                spacing = 36
            ),
            titleTextColor = 0xFFFFFFFF.toInt(),
            titleTextSize = 96f,
            titleTypeface = WelcomeTypeface.DEFAULT_BOLD,
            descriptionTextColor = 0xE6FFFFFF.toInt(),
            descriptionTextSize = 48f,
            backgroundColor = 0xFF667EEA.toInt(),
            useGradientBackground = true,
            backgroundGradientColor = 0xFF764BA2.toInt(),
            imageCornerRadius = 96f,
            imageElevation = 24f,
            imageSizeConfig = ImageSizeConfig(
                sizeMode = ImageSizeMode.PERCENTAGE_WIDTH, aspectRatio = 1f, widthFraction = 0.7f
            )
        )
    }

    fun createCorporateTheme(): WelcomeScreenConfig {
        return WelcomeScreenConfig(
            skipButton = ButtonConfig(
                isVisible = true,
                text = "Skip Introduction",
                placement = ButtonPlacement.TOP_RIGHT,
                style = ButtonStyle(
                    backgroundColor = 0x00000000,
                    textColor = 0xFF64748B.toInt(),
                    textSize = 36f,
                    cornerRadius = 24f,
                    paddingHorizontal = 36,
                    paddingVertical = 18
                )
            ),
            nextButton = ButtonConfig(
                isVisible = true,
                text = "Continue",
                placement = ButtonPlacement.BOTTOM_RIGHT,
                style = ButtonStyle(
                    backgroundColor = 0xFF1E40AF.toInt(),
                    textColor = 0xFFFFFFFF.toInt(),
                    cornerRadius = 32f,
                    paddingHorizontal = 72,
                    paddingVertical = 42,
                    fontWeight = 500
                )
            ),
            previousButton = ButtonConfig(
                isVisible = true,
                text = "Previous",
                placement = ButtonPlacement.BOTTOM_LEFT,
                style = ButtonStyle(
                    backgroundColor = 0x00000000,
                    textColor = 0xFF1E40AF.toInt(),
                    borderWidth = 2,
                    borderColor = 0xFF1E40AF.toInt(),
                    cornerRadius = 32f,
                    paddingHorizontal = 60,
                    paddingVertical = 42
                )
            ),
            finishButton = ButtonConfig(
                isVisible = true, text = "Get Started", style = ButtonStyle(
                    backgroundColor = 0xFF1E40AF.toInt(),
                    textColor = 0xFFFFFFFF.toInt(),
                    cornerRadius = 32f,
                    paddingHorizontal = 96,
                    paddingVertical = 48,
                    fontWeight = 600
                )
            ),
            pageIndicator = PageIndicatorConfig(
                activeColor = 0xFF1E40AF.toInt(),
                inactiveColor = 0xFFCBD5E1.toInt(),
                animationType = IndicatorAnimation.SLIDE,
                shape = IndicatorShape.ROUNDED_RECTANGLE,
                customCornerRadius = 8f,
                size = 20,
                spacing = 30
            ),
            titleTextColor = 0xFF1E293B.toInt(),
            titleTextSize = 72f,
            titleTypeface = WelcomeTypeface.DEFAULT_BOLD,
            descriptionTextColor = 0xFF475569.toInt(),
            descriptionTextSize = 42f,
            backgroundColor = 0xFFF8FAFC.toInt(),
            imageCornerRadius = 24f,
            imageElevation = 12f,
            imageSizeConfig = ImageSizeConfig(
                aspectRatio = 1.5f, horizontalPadding = 72
            ),
            customSpacing = WelcomeScreenSpacing(
                imageToTitleSpacing = 96,
                titleToDescriptionSpacing = 36,
                descriptionToIndicatorSpacing = 72,
                horizontalPadding = 72,
                verticalPadding = 96
            )
        )
    }
}