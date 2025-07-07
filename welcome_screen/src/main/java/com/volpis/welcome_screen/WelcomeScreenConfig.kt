package com.volpis.welcome_screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

fun welcomeScreenConfig(block: WelcomeScreenConfig.() -> Unit): WelcomeScreenConfig {
    return WelcomeScreenConfig().apply(block)
}

data class WelcomeScreenConfig(
    val skipButton: ButtonConfig = ButtonConfig(
        isVisible = true,
        text = "Skip",
        placement = ButtonPlacement.TOP_RIGHT,
        style = ButtonStyle(
            backgroundColor = Color.Transparent,
            textColor = Color.Gray,
            fontSize = 14.sp,
            cornerRadius = 20.dp
        )
    ),
    val nextButton: ButtonConfig = ButtonConfig(
        isVisible = true,
        text = "Next",
        placement = ButtonPlacement.BOTTOM_RIGHT,
        style = ButtonStyle(
            backgroundColor = Color.Blue,
            textColor = Color.White,
            cornerRadius = 24.dp,
            paddingHorizontal = 24.dp,
            paddingVertical = 12.dp
        )
    ),
    val previousButton: ButtonConfig = ButtonConfig(
        isVisible = true,
        text = "Previous",
        placement = ButtonPlacement.BOTTOM_LEFT,
        style = ButtonStyle(
            backgroundColor = Color.Transparent,
            textColor = Color.Gray,
            borderWidth = 1.dp,
            borderColor = Color.Gray,
            cornerRadius = 24.dp,
            paddingHorizontal = 20.dp,
            paddingVertical = 12.dp
        )
    ),
    val finishButton: ButtonConfig = ButtonConfig(
        isVisible = true,
        text = "Get Started",
        placement = ButtonPlacement.BOTTOM_CENTER,
        style = ButtonStyle(
            backgroundColor = Color.Blue,
            textColor = Color.White,
            cornerRadius = 28.dp,
            paddingHorizontal = 32.dp,
            paddingVertical = 16.dp,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    ),

    val pageIndicator: PageIndicatorConfig = PageIndicatorConfig(
        isVisible = true,
        activeColor = Color.Blue,
        inactiveColor = Color.Gray.copy(alpha = 0.3f),
        size = 8.dp,
        spacing = 12.dp,
        placement = IndicatorPlacement.CENTER_BELOW_DESCRIPTION,
        animationType = IndicatorAnimation.SLIDE,
        animationDuration = 300
    ),

    val titleTextStyle: TextStyle = TextStyle(
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black,
        letterSpacing = 0.5.sp
    ),
    val descriptionTextStyle: TextStyle = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        color = Color.Gray,
        letterSpacing = 0.25.sp
    ),

    val backgroundColor: Color = Color.White,
    val backgroundGradientColor: Color? = null,
    val useGradientBackground: Boolean = false,
    val contentPadding: PaddingValues = PaddingValues(16.dp),

    // Image configuration
    val imageCornerRadius: Dp = 16.dp,
    val imageElevation: Dp = 4.dp,
    val imageScaleType: ImageScaleType = ImageScaleType.FIT,
    val imageSizeConfig: ImageSizeConfig = ImageSizeConfig(),

    val pageTransitionDuration: Int = 300,
    val enableParallaxEffect: Boolean = false,
    val parallaxIntensity: Float = 0.5f,

    val customSpacing: WelcomeScreenSpacing = WelcomeScreenSpacing(),
)

data class WelcomeScreenSpacing(
    val imageToTitleSpacing: Dp = 32.dp,
    val titleToDescriptionSpacing: Dp = 16.dp,
    val descriptionToIndicatorSpacing: Dp = 24.dp,
    val indicatorToButtonSpacing: Dp = 32.dp,
    val horizontalPadding: Dp = 24.dp,
    val verticalPadding: Dp = 32.dp
)

enum class ImageScaleType {
    FIT, CROP, FILL_WIDTH, FILL_HEIGHT
}