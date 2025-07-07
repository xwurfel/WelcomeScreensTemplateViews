package com.volpis.welcome_screen

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
                    backgroundColor = Color(0xFF007AFF),
                    textColor = Color.White,
                    cornerRadius = 28.dp,
                    paddingHorizontal = 40.dp,
                    paddingVertical = 16.dp,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            ),
            pageIndicator = PageIndicatorConfig(
                animationType = IndicatorAnimation.FADE,
                activeColor = Color(0xFF007AFF),
                inactiveColor = Color.Gray.copy(alpha = 0.3f)
            ),
            imageSizeConfig = ImageSizeConfig.small()
        )
    }

    fun createFullFeaturedConfig(): WelcomeScreenConfig {
        return WelcomeScreenConfig(
            skipButton = ButtonConfig(
                isVisible = true,
                text = "Skip",
                placement = ButtonPlacement.TOP_RIGHT,
                style = ButtonStyle(
                    backgroundColor = Color.Transparent,
                    textColor = Color.Gray,
                    fontSize = 14.sp,
                    cornerRadius = 20.dp,
                    paddingHorizontal = 16.dp,
                    paddingVertical = 8.dp
                )
            ),
            nextButton = ButtonConfig(
                isVisible = true,
                text = "Next",
                placement = ButtonPlacement.BOTTOM_RIGHT,
                style = ButtonStyle(
                    backgroundColor = Color(0xFF007AFF),
                    textColor = Color.White,
                    cornerRadius = 24.dp,
                    paddingHorizontal = 24.dp,
                    paddingVertical = 12.dp,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium
                )
            ),
            previousButton = ButtonConfig(
                isVisible = true,
                text = "Back",
                placement = ButtonPlacement.BOTTOM_LEFT,
                style = ButtonStyle(
                    backgroundColor = Color.Transparent,
                    textColor = Color.Gray,
                    borderWidth = 1.dp,
                    borderColor = Color.Gray.copy(alpha = 0.5f),
                    cornerRadius = 24.dp,
                    paddingHorizontal = 20.dp,
                    paddingVertical = 12.dp,
                    fontSize = 15.sp
                )
            ),
            finishButton = ButtonConfig(
                isVisible = true,
                text = "Get Started",
                placement = ButtonPlacement.BOTTOM_CENTER,
                style = ButtonStyle(
                    backgroundColor = Color(0xFF34C759),
                    textColor = Color.White,
                    cornerRadius = 28.dp,
                    paddingHorizontal = 32.dp,
                    paddingVertical = 16.dp,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            ),
            pageIndicator = PageIndicatorConfig(
                activeColor = Color(0xFF007AFF),
                inactiveColor = Color.Gray.copy(alpha = 0.3f),
                animationType = IndicatorAnimation.SLIDE,
                size = 8.dp,
                spacing = 12.dp
            ),
            imageCornerRadius = 20.dp,
            imageElevation = 8.dp,
            imageSizeConfig = ImageSizeConfig()
        )
    }

    fun createModernConfig(): WelcomeScreenConfig {
        return WelcomeScreenConfig(
            skipButton = ButtonConfig(
                isVisible = true,
                text = "Skip",
                placement = ButtonPlacement.TOP_RIGHT,
                style = ButtonStyle(
                    backgroundColor = Color.Transparent,
                    textColor = Color(0xFF8E8E93),
                    fontSize = 16.sp,
                    cornerRadius = 0.dp
                )
            ),
            nextButton = ButtonConfig(
                isVisible = false
            ),
            previousButton = ButtonConfig(
                isVisible = false
            ),
            finishButton = ButtonConfig(
                isVisible = true,
                text = "Next",
                placement = ButtonPlacement.BOTTOM_CENTER,
                style = ButtonStyle(
                    backgroundColor = Color(0xFF007AFF),
                    textColor = Color.White,
                    cornerRadius = 14.dp,
                    paddingHorizontal = 0.dp,
                    paddingVertical = 16.dp,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            ),
            pageIndicator = PageIndicatorConfig(
                activeColor = Color(0xFF007AFF),
                inactiveColor = Color(0xFFE5E5EA),
                animationType = IndicatorAnimation.SLIDE,
                size = 6.dp,
                spacing = 8.dp
            ),
            titleTextStyle = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1C1C1E),
                letterSpacing = 0.5.sp
            ),
            descriptionTextStyle = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF8E8E93),
                letterSpacing = 0.25.sp
            ),
            backgroundColor = Color.White,
            imageCornerRadius = 20.dp,
            imageElevation = 0.dp,
            imageSizeConfig = ImageSizeConfig.banner(),
            customSpacing = WelcomeScreenSpacing(
                imageToTitleSpacing = 40.dp,
                titleToDescriptionSpacing = 12.dp,
                descriptionToIndicatorSpacing = 32.dp,
                indicatorToButtonSpacing = 40.dp,
                horizontalPadding = 32.dp,
                verticalPadding = 48.dp
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
                    backgroundColor = Color.Transparent,
                    textColor = Color(0xFF8E8E93),
                    fontSize = 16.sp
                )
            ),
            nextButton = ButtonConfig(
                isVisible = true,
                text = "Next",
                placement = ButtonPlacement.BOTTOM_RIGHT,
                style = ButtonStyle(
                    backgroundColor = Color(0xFF0A84FF),
                    textColor = Color.White,
                    cornerRadius = 24.dp,
                    paddingHorizontal = 24.dp,
                    paddingVertical = 12.dp
                )
            ),
            previousButton = ButtonConfig(
                isVisible = true,
                text = "Back",
                placement = ButtonPlacement.BOTTOM_LEFT,
                style = ButtonStyle(
                    backgroundColor = Color.Transparent,
                    textColor = Color(0xFF8E8E93),
                    borderWidth = 1.dp,
                    borderColor = Color(0xFF3A3A3C),
                    cornerRadius = 24.dp,
                    paddingHorizontal = 20.dp,
                    paddingVertical = 12.dp
                )
            ),
            finishButton = ButtonConfig(
                isVisible = true,
                text = "Get Started",
                style = ButtonStyle(
                    backgroundColor = Color(0xFF30D158),
                    textColor = Color.Black,
                    cornerRadius = 28.dp,
                    paddingHorizontal = 32.dp,
                    paddingVertical = 16.dp,
                    fontWeight = FontWeight.SemiBold
                )
            ),
            pageIndicator = PageIndicatorConfig(
                activeColor = Color(0xFF0A84FF),
                inactiveColor = Color(0xFF3A3A3C),
                animationType = IndicatorAnimation.MORPHING
            ),
            titleTextStyle = TextStyle(
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            ),
            descriptionTextStyle = TextStyle(
                fontSize = 16.sp,
                color = Color(0xFF8E8E93)
            ),
            backgroundColor = Color(0xFF1C1C1E),
            useGradientBackground = true,
            backgroundGradientColor = Color(0xFF2C2C2E),
            imageSizeConfig = ImageSizeConfig.portrait()
        )
    }

    fun createCustomThemeConfig(
        primaryColor: Color,
        backgroundColor: Color,
        textColor: Color,
        accentColor: Color = primaryColor
    ): WelcomeScreenConfig {
        return WelcomeScreenConfig(
            skipButton = ButtonConfig(
                isVisible = true,
                text = "Skip",
                placement = ButtonPlacement.TOP_RIGHT,
                style = ButtonStyle(
                    backgroundColor = Color.Transparent,
                    textColor = textColor.copy(alpha = 0.7f),
                    fontSize = 14.sp
                )
            ),
            nextButton = ButtonConfig(
                text = "Continue",
                style = ButtonStyle(
                    backgroundColor = primaryColor,
                    textColor = Color.White,
                    cornerRadius = 24.dp,
                    paddingHorizontal = 24.dp,
                    paddingVertical = 12.dp
                )
            ),
            finishButton = ButtonConfig(
                text = "Start Journey",
                style = ButtonStyle(
                    backgroundColor = accentColor,
                    textColor = Color.White,
                    cornerRadius = 28.dp,
                    paddingHorizontal = 32.dp,
                    paddingVertical = 16.dp,
                    fontWeight = FontWeight.SemiBold
                )
            ),
            pageIndicator = PageIndicatorConfig(
                activeColor = primaryColor,
                inactiveColor = primaryColor.copy(alpha = 0.3f),
                animationType = IndicatorAnimation.SLIDE
            ),
            titleTextStyle = TextStyle(
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = textColor
            ),
            descriptionTextStyle = TextStyle(
                fontSize = 16.sp,
                color = textColor.copy(alpha = 0.7f)
            ),
            backgroundColor = backgroundColor,
            imageSizeConfig = ImageSizeConfig()
        )
    }

    fun createContentFocusedConfig(): WelcomeScreenConfig {
        return WelcomeScreenConfig(
            skipButton = ButtonConfig(
                isVisible = true,
                text = "Skip",
                placement = ButtonPlacement.TOP_RIGHT,
                style = ButtonStyle(
                    backgroundColor = Color.Transparent,
                    textColor = Color.Gray,
                    fontSize = 14.sp
                )
            ),
            finishButton = ButtonConfig(
                isVisible = true,
                text = "Continue",
                placement = ButtonPlacement.BOTTOM_CENTER,
                style = ButtonStyle(
                    backgroundColor = Color(0xFF007AFF),
                    textColor = Color.White,
                    cornerRadius = 24.dp,
                    paddingHorizontal = 32.dp,
                    paddingVertical = 16.dp,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            ),
            pageIndicator = PageIndicatorConfig(
                activeColor = Color(0xFF007AFF),
                inactiveColor = Color.Gray.copy(alpha = 0.3f),
                animationType = IndicatorAnimation.FADE
            ),
            // Small, fixed-size images for content-focused design
            imageSizeConfig = ImageSizeConfig.fixedSize(120.dp, 120.dp),
            customSpacing = WelcomeScreenSpacing(
                imageToTitleSpacing = 24.dp,
                titleToDescriptionSpacing = 16.dp,
                descriptionToIndicatorSpacing = 32.dp,
                indicatorToButtonSpacing = 32.dp,
                horizontalPadding = 24.dp,
                verticalPadding = 16.dp
            )
        )
    }

    /**
     * Creates a config for hero-style screens with large, prominent images
     */
    fun createHeroConfig(): WelcomeScreenConfig {
        return WelcomeScreenConfig(
            skipButton = ButtonConfig(
                isVisible = true,
                text = "Skip",
                placement = ButtonPlacement.TOP_RIGHT,
                style = ButtonStyle(
                    backgroundColor = Color.Black.copy(alpha = 0.3f),
                    textColor = Color.White,
                    fontSize = 16.sp,
                    cornerRadius = 20.dp,
                    paddingHorizontal = 16.dp,
                    paddingVertical = 8.dp
                )
            ),
            finishButton = ButtonConfig(
                isVisible = true,
                text = "Get Started",
                placement = ButtonPlacement.BOTTOM_CENTER,
                style = ButtonStyle(
                    backgroundColor = Color.White,
                    textColor = Color.Black,
                    cornerRadius = 28.dp,
                    paddingHorizontal = 40.dp,
                    paddingVertical = 16.dp,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            ),
            pageIndicator = PageIndicatorConfig(
                activeColor = Color.White,
                inactiveColor = Color.White.copy(alpha = 0.4f),
                animationType = IndicatorAnimation.SCALE,
                size = 10.dp
            ),
            titleTextStyle = TextStyle(
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            ),
            descriptionTextStyle = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                color = Color.White.copy(alpha = 0.9f)
            ),
            imageSizeConfig = ImageSizeConfig.fillWidth(aspectRatio = 1.6f, padding = 0.dp),
            imageCornerRadius = 0.dp,
            imageElevation = 0.dp,
            customSpacing = WelcomeScreenSpacing(
                imageToTitleSpacing = 32.dp,
                titleToDescriptionSpacing = 16.dp,
                descriptionToIndicatorSpacing = 40.dp,
                indicatorToButtonSpacing = 40.dp,
                horizontalPadding = 24.dp,
                verticalPadding = 0.dp
            )
        )
    }
}