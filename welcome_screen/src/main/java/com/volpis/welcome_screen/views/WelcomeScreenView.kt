package com.volpis.welcome_screen.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.volpis.welcome_screen.config.ImageScaleType
import com.volpis.welcome_screen.config.ImageSizeMode
import com.volpis.welcome_screen.R
import com.volpis.welcome_screen.config.WelcomeScreenConfig
import com.volpis.welcome_screen.WelcomeScreenData
import com.volpis.welcome_screen.databinding.ViewWelcomeScreenContentBinding
import com.volpis.welcome_screen.utils.dpToPx
import com.volpis.welcome_screen.utils.setGradientBackground
import com.volpis.welcome_screen.utils.setRoundedCorners

class WelcomeScreenView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: ViewWelcomeScreenContentBinding =
        ViewWelcomeScreenContentBinding.inflate(LayoutInflater.from(context), this, true)

    fun setupScreen(screenData: WelcomeScreenData, config: WelcomeScreenConfig) {
        setupBackground(screenData, config)
        setupImage(screenData, config)
        setupText(screenData, config)
        setupSpacing(config)
        setupAccessibility(screenData)
    }

    private fun setupBackground(screenData: WelcomeScreenData, config: WelcomeScreenConfig) {
        val backgroundColor = screenData.backgroundColor ?: config.backgroundColor
        setBackgroundColor(backgroundColor)

        if (config.useGradientBackground && config.backgroundGradientColor != null) {
            setGradientBackground(config.backgroundColor, config.backgroundGradientColor)
        }
    }

    private fun setupImage(screenData: WelcomeScreenData, config: WelcomeScreenConfig) {
        val imageConfig = config.imageSizeConfig

        // Configure image view layout parameters
        binding.imageView.updateLayoutParams<LinearLayout.LayoutParams> {
            when (imageConfig.sizeMode) {
                ImageSizeMode.FIXED_SIZE -> {
                    width = imageConfig.fixedWidth ?: context.dpToPx(200)
                    height = imageConfig.fixedHeight ?: context.dpToPx(200)
                    weight = 0f
                }

                ImageSizeMode.ASPECT_RATIO -> {
                    width = LinearLayout.LayoutParams.MATCH_PARENT
                    height = 0
                    weight = 2f // Use weight for flexible sizing
                }

                ImageSizeMode.FILL_WIDTH -> {
                    width = LinearLayout.LayoutParams.MATCH_PARENT
                    height = LinearLayout.LayoutParams.WRAP_CONTENT
                    weight = 0f
                }

                ImageSizeMode.PERCENTAGE_WIDTH -> {
                    width = LinearLayout.LayoutParams.MATCH_PARENT
                    height = 0
                    weight = 2f * imageConfig.widthFraction
                }

                ImageSizeMode.CUSTOM -> {
                    width = imageConfig.fixedWidth ?: LinearLayout.LayoutParams.MATCH_PARENT
                    height = imageConfig.fixedHeight ?: LinearLayout.LayoutParams.WRAP_CONTENT
                    weight = if (imageConfig.fixedHeight == null) 2f else 0f
                }
            }

            // Set margins as padding equivalent
            setMargins(
                imageConfig.horizontalPadding,
                imageConfig.verticalPadding,
                imageConfig.horizontalPadding,
                imageConfig.verticalPadding
            )
        }

        // Configure scale type
        binding.imageView.scaleType = when (config.imageScaleType) {
            ImageScaleType.FIT -> android.widget.ImageView.ScaleType.FIT_CENTER
            ImageScaleType.CROP -> android.widget.ImageView.ScaleType.CENTER_CROP
            ImageScaleType.FILL_WIDTH -> android.widget.ImageView.ScaleType.FIT_XY
            ImageScaleType.FILL_HEIGHT -> android.widget.ImageView.ScaleType.MATRIX
        }

        // Set elevation and corner radius
        binding.imageView.elevation = config.imageElevation
        binding.imageView.setRoundedCorners(config.imageCornerRadius)

        // Load image with Glide
        val cornerRadiusPx = config.imageCornerRadius.toInt()

        when {
            screenData.imageRes != null -> {
                Glide.with(context)
                    .load(screenData.imageRes)
                    .transform(RoundedCorners(cornerRadiusPx))
                    .into(binding.imageView)
            }

            screenData.imageDrawable != null -> {
                Glide.with(context)
                    .load(screenData.imageDrawable)
                    .transform(RoundedCorners(cornerRadiusPx))
                    .into(binding.imageView)
            }

            screenData.imageUrl != null -> {
                Glide.with(context)
                    .load(screenData.imageUrl)
                    .transform(RoundedCorners(cornerRadiusPx))
                    .placeholder(R.drawable.welcome_image_placeholder)
                    .error(R.drawable.welcome_image_error)
                    .into(binding.imageView)
            }

            else -> {
                // Hide image view if no image is provided
                binding.imageView.visibility = GONE
            }
        }
    }

    private fun setupText(screenData: WelcomeScreenData, config: WelcomeScreenConfig) {
        // Title configuration
        binding.titleText.apply {
            text = screenData.title
            setTextColor(screenData.textColor ?: config.titleTextColor)
            textSize = config.titleTextSize / resources.displayMetrics.scaledDensity
            typeface = config.titleTypeface
        }

        // Description configuration
        binding.descriptionText.apply {
            text = screenData.description
            setTextColor(screenData.textColor ?: config.descriptionTextColor)
            textSize = config.descriptionTextSize / resources.displayMetrics.scaledDensity
            typeface = config.descriptionTypeface
        }
    }

    private fun setupSpacing(config: WelcomeScreenConfig) {
        val spacing = config.customSpacing

        // Configure spacing between elements using layout parameters
        binding.titleText.updateLayoutParams<LinearLayout.LayoutParams> {
            topMargin = spacing.imageToTitleSpacing
        }

        binding.descriptionText.updateLayoutParams<LinearLayout.LayoutParams> {
            topMargin = spacing.titleToDescriptionSpacing
        }

        // Set container padding
        setPadding(
            spacing.horizontalPadding,
            spacing.verticalPadding,
            spacing.horizontalPadding,
            spacing.verticalPadding
        )
    }

    private fun setupAccessibility(screenData: WelcomeScreenData) {
        // Set content descriptions for accessibility
        binding.imageView.contentDescription = screenData.contentDescription
            ?: context.getString(R.string.welcome_image_description)

        // Make the entire view focusable for screen readers
        isFocusable = true
        contentDescription = "${screenData.title}. ${screenData.description}"
    }
}