package com.volpis.welcome_screen

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


enum class ImageSizeMode {
    ASPECT_RATIO, FIXED_SIZE, FILL_WIDTH, PERCENTAGE_WIDTH, CUSTOM
}

/**
 * Configuration for image sizing and positioning in welcome screens
 *
 * @param sizeMode How the image should be sized
 * @param aspectRatio Aspect ratio for the image (width/height)
 * @param fixedWidth Fixed width in dp (used with FIXED_SIZE mode)
 * @param fixedHeight Fixed height in dp (used with FIXED_SIZE mode)
 * @param widthFraction Fraction of available width (0.0 to 1.0) for PERCENTAGE_WIDTH mode
 * @param horizontalPadding Horizontal padding around the image
 * @param verticalPadding Vertical padding around the image
 * @param maxWidth Maximum width constraint
 * @param maxHeight Maximum height constraint
 * @param minWidth Minimum width constraint
 * @param minHeight Minimum height constraint
 */
data class ImageSizeConfig(
    val sizeMode: ImageSizeMode = ImageSizeMode.ASPECT_RATIO,
    val aspectRatio: Float = 1.2f,
    val fixedWidth: Dp? = null,
    val fixedHeight: Dp? = null,
    val widthFraction: Float = 1f,
    val horizontalPadding: Dp = 32.dp,
    val verticalPadding: Dp = 0.dp,
    val maxWidth: Dp? = null,
    val maxHeight: Dp? = null,
    val minWidth: Dp? = null,
    val minHeight: Dp? = null
) {
    companion object {
        fun small() = ImageSizeConfig(
            sizeMode = ImageSizeMode.PERCENTAGE_WIDTH,
            aspectRatio = 1f,
            widthFraction = 0.6f,
            horizontalPadding = 48.dp
        )

        fun banner() = ImageSizeConfig(
            sizeMode = ImageSizeMode.ASPECT_RATIO, aspectRatio = 2.5f, horizontalPadding = 16.dp
        )

        fun portrait() = ImageSizeConfig(
            sizeMode = ImageSizeMode.ASPECT_RATIO, aspectRatio = 0.8f, horizontalPadding = 48.dp
        )

        fun fixedSize(width: Dp, height: Dp) = ImageSizeConfig(
            sizeMode = ImageSizeMode.FIXED_SIZE,
            fixedWidth = width,
            fixedHeight = height,
            horizontalPadding = 24.dp
        )

        fun fillWidth(aspectRatio: Float = 1.2f, padding: Dp = 16.dp) = ImageSizeConfig(
            sizeMode = ImageSizeMode.FILL_WIDTH,
            aspectRatio = aspectRatio,
            horizontalPadding = padding
        )
    }
}