package com.volpis.welcome_screen.views

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.OvershootInterpolator
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Space
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.volpis.welcome_screen.R
import com.volpis.welcome_screen.WelcomeScreenData
import com.volpis.welcome_screen.config.ImageScaleType
import com.volpis.welcome_screen.config.ImageSizeConfig
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
    private lateinit var contentContainer: LinearLayout
    private lateinit var titleText: TextView
    private lateinit var descriptionText: TextView
    private lateinit var bottomSpacer: Space

    private var isAnimated = false
    private var animatorSet: AnimatorSet? = null

    private companion object {
        const val ANIMATION_DURATION_FAST = 250L
        const val ANIMATION_DURATION_MEDIUM = 350L
        const val ANIMATION_DELAY_SHORT = 50L
        const val ANIMATION_DELAY_MEDIUM = 100L
        const val ANIMATION_DELAY_LONG = 150L
        const val TRANSLATION_DISTANCE = 20f
        const val SCALE_FROM = 0.96f
        const val SCALE_TO = 1f
    }

    init {
        orientation = VERTICAL
        gravity = Gravity.CENTER_HORIZONTAL
        setLayerType(LAYER_TYPE_HARDWARE, null)
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
            cardElevation = context.dpToPx(8).toFloat()
            radius = context.dpToPx(16).toFloat()
            useCompatPadding = true
            setLayerType(LAYER_TYPE_HARDWARE, null)
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

        contentContainer = LinearLayout(context).apply {
            orientation = VERTICAL
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(context.dpToPx(24), context.dpToPx(32), context.dpToPx(24), 0)
            }
        }

        titleText = TextView(context).apply {
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
            )
            gravity = Gravity.CENTER
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 28f)
            typeface = Typeface.DEFAULT_BOLD
            setTextColor(ContextCompat.getColor(context, android.R.color.black))
            letterSpacing = 0f
            setLineSpacing(context.dpToPx(4).toFloat(), 1f)
            setLayerType(LAYER_TYPE_HARDWARE, null)
        }

        descriptionText = TextView(context).apply {
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, context.dpToPx(16), 0, 0)
            }
            gravity = Gravity.CENTER
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
            setTextColor(ContextCompat.getColor(context, android.R.color.darker_gray))
            setLineSpacing(context.dpToPx(6).toFloat(), 1.1f)
            letterSpacing = 0.01f
            setLayerType(LAYER_TYPE_HARDWARE, null)
        }

        contentContainer.addView(titleText)
        contentContainer.addView(descriptionText)
        addView(contentContainer)

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

        isAnimated = false
        resetViewStates()
    }

    private fun resetViewStates() {
        titleText.alpha = 0f
        titleText.translationY = context.dpToPx(TRANSLATION_DISTANCE.toInt()).toFloat()

        descriptionText.alpha = 0f
        descriptionText.translationY = context.dpToPx(TRANSLATION_DISTANCE.toInt()).toFloat()

        if (imageContainer.isVisible) {
            imageContainer.alpha = 0f
            imageContainer.scaleX = SCALE_FROM
            imageContainer.scaleY = SCALE_FROM
        }
    }

    private fun setupBackground(screenData: WelcomeScreenData, config: WelcomeScreenConfig) {
        val backgroundColor = screenData.backgroundColor ?: config.backgroundColor
        setBackgroundColor(backgroundColor)

        if (config.useGradientBackground && config.backgroundGradientColor != null) {
            setGradientBackground(backgroundColor, config.backgroundGradientColor)
        }
    }

    private fun setupImage(screenData: WelcomeScreenData, config: WelcomeScreenConfig) {
        val imageConfig = config.imageSizeConfig
        configureImageContainer(imageConfig)

        imageView.scaleType = when (config.imageScaleType) {
            ImageScaleType.FIT -> ImageView.ScaleType.FIT_CENTER
            ImageScaleType.CROP -> ImageView.ScaleType.CENTER_CROP
            ImageScaleType.FILL_WIDTH -> ImageView.ScaleType.FIT_XY
            ImageScaleType.FILL_HEIGHT -> ImageView.ScaleType.MATRIX
        }

        imageContainer.apply {
            cardElevation = config.imageElevation
            radius = config.imageCornerRadius
        }

        loadImage(screenData, config)
    }

    private fun configureImageContainer(imageConfig: ImageSizeConfig) {
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
                containerParams.weight = calculateWeight(imageConfig.aspectRatio)
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

        imageConfig.maxWidth?.let { maxWidth ->
            if (containerParams.width > maxWidth) {
                containerParams.width = maxWidth
            }
        }

        imageConfig.maxHeight?.let { maxHeight ->
            if (containerParams.height > maxHeight) {
                containerParams.height = maxHeight
            }
        }

        containerParams.setMargins(
            imageConfig.horizontalPadding,
            imageConfig.verticalPadding,
            imageConfig.horizontalPadding,
            imageConfig.verticalPadding
        )

        imageContainer.layoutParams = containerParams
    }

    private fun calculateWeight(aspectRatio: Float): Float {
        return when {
            aspectRatio > 2f -> 1.5f
            aspectRatio > 1.5f -> 2f
            aspectRatio > 0.8f -> 2.5f
            else -> 3f
        }
    }

    private fun loadImage(screenData: WelcomeScreenData, config: WelcomeScreenConfig) {
        val cornerRadiusPx = config.imageCornerRadius.toInt()
        val requestOptions = RequestOptions()
            .transform(RoundedCorners(cornerRadiusPx))
            .placeholder(android.R.drawable.ic_menu_gallery)
            .error(android.R.drawable.ic_menu_close_clear_cancel)

        when {
            screenData.imageRes != null -> {
                showImageContainer()
                Glide.with(context)
                    .load(screenData.imageRes)
                    .apply(requestOptions)
                    .into(imageView)
            }

            screenData.imageDrawable != null -> {
                showImageContainer()
                Glide.with(context)
                    .load(screenData.imageDrawable)
                    .apply(requestOptions)
                    .into(imageView)
            }

            screenData.imageUrl != null -> {
                showImageContainer()
                Glide.with(context)
                    .load(screenData.imageUrl)
                    .apply(requestOptions)
                    .into(imageView)
            }

            else -> {
                hideImageContainer()
            }
        }
    }

    private fun showImageContainer() {
        imageView.visibility = VISIBLE
        imageContainer.visibility = VISIBLE
    }

    private fun hideImageContainer() {
        imageView.visibility = GONE
        imageContainer.visibility = GONE

        contentContainer.updateLayoutParams<LayoutParams> {
            topMargin = context.dpToPx(64)
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

        contentContainer.updateLayoutParams<LayoutParams> {
            topMargin = spacing.imageToTitleSpacing
            setMargins(spacing.horizontalPadding, topMargin, spacing.horizontalPadding, 0)
        }

        descriptionText.updateLayoutParams<LayoutParams> {
            topMargin = spacing.titleToDescriptionSpacing
        }

        setPadding(
            0,
            spacing.verticalPadding,
            0,
            spacing.verticalPadding + context.dpToPx(140)
        )
    }

    private fun setupAccessibility(screenData: WelcomeScreenData) {
        imageView.contentDescription = screenData.contentDescription
            ?: context.getString(R.string.welcome_image_description)

        contentDescription = buildString {
            append(screenData.title)
            append(". ")
            append(screenData.description)
            screenData.contentDescription?.let {
                append(". ")
                append(it)
            }
        }

        isFocusable = true
        importantForAccessibility = View.IMPORTANT_FOR_ACCESSIBILITY_YES
    }

    fun startEntranceAnimation() {
        if (isAnimated) return

        isAnimated = true
        animatorSet?.cancel()

        val animators = mutableListOf<ObjectAnimator>()

        val titleAnimator = ObjectAnimator.ofPropertyValuesHolder(
            titleText,
            PropertyValuesHolder.ofFloat("alpha", 0f, 1f),
            PropertyValuesHolder.ofFloat("translationY", titleText.translationY, 0f)
        ).apply {
            duration = ANIMATION_DURATION_FAST
            startDelay = ANIMATION_DELAY_MEDIUM
            interpolator = AccelerateDecelerateInterpolator()
        }
        animators.add(titleAnimator)

        val descriptionAnimator = ObjectAnimator.ofPropertyValuesHolder(
            descriptionText,
            PropertyValuesHolder.ofFloat("alpha", 0f, 1f),
            PropertyValuesHolder.ofFloat("translationY", descriptionText.translationY, 0f)
        ).apply {
            duration = ANIMATION_DURATION_FAST
            startDelay = ANIMATION_DELAY_LONG
            interpolator = AccelerateDecelerateInterpolator()
        }
        animators.add(descriptionAnimator)

        if (imageContainer.isVisible) {
            val imageAnimator = ObjectAnimator.ofPropertyValuesHolder(
                imageContainer,
                PropertyValuesHolder.ofFloat("alpha", 0f, 1f),
                PropertyValuesHolder.ofFloat("scaleX", SCALE_FROM, SCALE_TO),
                PropertyValuesHolder.ofFloat("scaleY", SCALE_FROM, SCALE_TO)
            ).apply {
                duration = ANIMATION_DURATION_MEDIUM
                startDelay = ANIMATION_DELAY_SHORT
                interpolator = OvershootInterpolator(0.8f)
            }
            animators.add(imageAnimator)
        }

        animatorSet = AnimatorSet().apply {
            playTogether(animators.map { it as Animator })
            start()
        }
    }

    fun resetAnimation() {
        animatorSet?.cancel()
        isAnimated = false
        resetViewStates()
    }

    fun showContentImmediately() {
        animatorSet?.cancel()
        isAnimated = true

        titleText.alpha = 1f
        titleText.translationY = 0f
        descriptionText.alpha = 1f
        descriptionText.translationY = 0f

        if (imageContainer.isVisible) {
            imageContainer.alpha = 1f
            imageContainer.scaleX = SCALE_TO
            imageContainer.scaleY = SCALE_TO
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        animatorSet?.cancel()
        animatorSet = null
        setLayerType(LAYER_TYPE_NONE, null)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        setLayerType(LAYER_TYPE_HARDWARE, null)
    }
}