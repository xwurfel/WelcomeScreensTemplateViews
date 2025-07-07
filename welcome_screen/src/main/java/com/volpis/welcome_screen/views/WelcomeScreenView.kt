package com.volpis.welcome_screen.views

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Space
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.volpis.welcome_screen.R
import com.volpis.welcome_screen.WelcomeScreenData
import com.volpis.welcome_screen.config.ImageScaleType
import com.volpis.welcome_screen.config.ImageSizeMode
import com.volpis.welcome_screen.config.WelcomeScreenConfig
import com.volpis.welcome_screen.utils.dpToPx
import com.volpis.welcome_screen.utils.setGradientBackground

class WelcomeScreenView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private lateinit var topSpacer: Space
    private lateinit var imageContainer: CardView
    private lateinit var imageView: ImageView
    private lateinit var titleText: TextView
    private lateinit var descriptionText: TextView
    private lateinit var bottomSpacer: Space

    init {
        orientation = VERTICAL
        gravity = Gravity.CENTER_HORIZONTAL
        initViews()
    }

    private fun initViews() {
        topSpacer = Space(context).apply {
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                0,
                0.5f
            )
        }
        addView(topSpacer)

        imageContainer = CardView(context).apply {
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                0,
                2f
            ).apply {
                setMargins(context.dpToPx(32), 0, context.dpToPx(32), 0)
            }
        }

        imageView = ImageView(context).apply {
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
            )
            scaleType = ImageView.ScaleType.CENTER_CROP
            contentDescription = context.getString(R.string.welcome_image_description)
        }

        imageContainer.addView(imageView)
        addView(imageContainer)

        titleText = TextView(context).apply {
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(context.dpToPx(16), context.dpToPx(32), context.dpToPx(16), 0)
            }
            gravity = Gravity.CENTER
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 28f)
            typeface = Typeface.DEFAULT_BOLD
            setTextColor(ContextCompat.getColor(context, android.R.color.black))
        }
        addView(titleText)

        descriptionText = TextView(context).apply {
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(context.dpToPx(8), context.dpToPx(16), context.dpToPx(8), 0)
            }
            gravity = Gravity.CENTER
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
            setTextColor(ContextCompat.getColor(context, android.R.color.darker_gray))
            setLineSpacing(context.dpToPx(6).toFloat(), 1f)
        }
        addView(descriptionText)

        bottomSpacer = Space(context).apply {
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                0,
                1f
            )
        }
        addView(bottomSpacer)
    }

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

        val containerParams = imageContainer.layoutParams as LayoutParams

        when (imageConfig.sizeMode) {
            ImageSizeMode.FIXED_SIZE -> {
                containerParams.width = imageConfig.fixedWidth ?: context.dpToPx(200)
                containerParams.height = imageConfig.fixedHeight ?: context.dpToPx(200)
                containerParams.weight = 0f
            }

            ImageSizeMode.ASPECT_RATIO -> {
                containerParams.width = LayoutParams.MATCH_PARENT
                containerParams.height = 0
                containerParams.weight = 2f
            }

            ImageSizeMode.FILL_WIDTH -> {
                containerParams.width = LayoutParams.MATCH_PARENT
                containerParams.height = LayoutParams.WRAP_CONTENT
                containerParams.weight = 0f
            }

            ImageSizeMode.PERCENTAGE_WIDTH -> {
                containerParams.width = LayoutParams.MATCH_PARENT
                containerParams.height = 0
                containerParams.weight = 2f * imageConfig.widthFraction
            }

            ImageSizeMode.CUSTOM -> {
                containerParams.width = imageConfig.fixedWidth ?: LayoutParams.MATCH_PARENT
                containerParams.height = imageConfig.fixedHeight ?: LayoutParams.WRAP_CONTENT
                containerParams.weight = if (imageConfig.fixedHeight == null) 2f else 0f
            }
        }

        containerParams.setMargins(
            imageConfig.horizontalPadding,
            imageConfig.verticalPadding,
            imageConfig.horizontalPadding,
            imageConfig.verticalPadding
        )

        imageContainer.layoutParams = containerParams

        imageView.scaleType = when (config.imageScaleType) {
            ImageScaleType.FIT -> ImageView.ScaleType.FIT_CENTER
            ImageScaleType.CROP -> ImageView.ScaleType.CENTER_CROP
            ImageScaleType.FILL_WIDTH -> ImageView.ScaleType.FIT_XY
            ImageScaleType.FILL_HEIGHT -> ImageView.ScaleType.MATRIX
        }

        imageContainer.cardElevation = config.imageElevation
        imageContainer.radius = config.imageCornerRadius

        val cornerRadiusPx = config.imageCornerRadius.toInt()

        when {
            screenData.imageRes != null -> {
                imageView.visibility = VISIBLE
                imageContainer.visibility = VISIBLE
                Glide.with(context)
                    .load(screenData.imageRes)
                    .transform(RoundedCorners(cornerRadiusPx))
                    .into(imageView)
            }

            screenData.imageDrawable != null -> {
                imageView.visibility = VISIBLE
                imageContainer.visibility = VISIBLE
                Glide.with(context)
                    .load(screenData.imageDrawable)
                    .transform(RoundedCorners(cornerRadiusPx))
                    .into(imageView)
            }

            screenData.imageUrl != null -> {
                imageView.visibility = VISIBLE
                imageContainer.visibility = VISIBLE
                Glide.with(context)
                    .load(screenData.imageUrl)
                    .transform(RoundedCorners(cornerRadiusPx))
                    .placeholder(android.R.drawable.ic_menu_gallery)
                    .error(android.R.drawable.ic_menu_close_clear_cancel)
                    .into(imageView)
            }

            else -> {
                imageView.visibility = GONE
                imageContainer.visibility = GONE
            }
        }
    }

    private fun setupText(screenData: WelcomeScreenData, config: WelcomeScreenConfig) {
        titleText.apply {
            text = screenData.title
            setTextColor(screenData.textColor ?: config.titleTextColor)
            setTextSize(TypedValue.COMPLEX_UNIT_PX, config.titleTextSize)
            typeface = config.titleTypeface.toTypeface()
        }

        descriptionText.apply {
            text = screenData.description
            setTextColor(screenData.textColor ?: config.descriptionTextColor)
            setTextSize(TypedValue.COMPLEX_UNIT_PX, config.descriptionTextSize)
            typeface = config.descriptionTypeface.toTypeface()
        }
    }

    private fun setupSpacing(config: WelcomeScreenConfig) {
        val spacing = config.customSpacing

        val titleParams = titleText.layoutParams as LayoutParams
        titleParams.topMargin = spacing.imageToTitleSpacing
        titleText.layoutParams = titleParams

        val descriptionParams = descriptionText.layoutParams as LayoutParams
        descriptionParams.topMargin = spacing.titleToDescriptionSpacing
        descriptionText.layoutParams = descriptionParams

        setPadding(
            spacing.horizontalPadding,
            spacing.verticalPadding,
            spacing.horizontalPadding,
            spacing.verticalPadding + context.dpToPx(120)
        )
    }

    private fun setupAccessibility(screenData: WelcomeScreenData) {
        imageView.contentDescription = screenData.contentDescription
            ?: context.getString(R.string.welcome_image_description)

        isFocusable = true
        contentDescription = "${screenData.title}. ${screenData.description}"
    }
}