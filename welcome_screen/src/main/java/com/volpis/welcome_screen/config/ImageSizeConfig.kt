package com.volpis.welcome_screen.config

import androidx.annotation.Px

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
    @Px val fixedWidth: Int? = null,
    @Px val fixedHeight: Int? = null,
    val widthFraction: Float = 1f,
    @Px val horizontalPadding: Int = 96,
    @Px val verticalPadding: Int = 0,
    @Px val maxWidth: Int? = null,
    @Px val maxHeight: Int? = null,
    @Px val minWidth: Int? = null,
    @Px val minHeight: Int? = null
) {
    companion object {
        fun small() = ImageSizeConfig(
            sizeMode = ImageSizeMode.PERCENTAGE_WIDTH,
            aspectRatio = 1f,
            widthFraction = 0.6f,
            horizontalPadding = 144
        )

        fun banner() = ImageSizeConfig(
            sizeMode = ImageSizeMode.ASPECT_RATIO,
            aspectRatio = 2.5f,
            horizontalPadding = 48
        )

        fun portrait() = ImageSizeConfig(
            sizeMode = ImageSizeMode.ASPECT_RATIO,
            aspectRatio = 0.8f,
            horizontalPadding = 144
        )

        fun fixedSize(width: Int, height: Int) = ImageSizeConfig(
            sizeMode = ImageSizeMode.FIXED_SIZE,
            fixedWidth = width,
            fixedHeight = height,
            horizontalPadding = 72
        )

        fun fillWidth(aspectRatio: Float = 1.2f, padding: Int = 48) = ImageSizeConfig(
            sizeMode = ImageSizeMode.FILL_WIDTH,
            aspectRatio = aspectRatio,
            horizontalPadding = padding
        )
    }
}