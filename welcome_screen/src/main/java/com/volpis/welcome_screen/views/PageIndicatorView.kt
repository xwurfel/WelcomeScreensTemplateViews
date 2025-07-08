package com.volpis.welcome_screen.views

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.BounceInterpolator
import android.view.animation.LinearInterpolator
import android.view.animation.OvershootInterpolator
import androidx.core.graphics.withSave
import androidx.viewpager2.widget.ViewPager2
import com.volpis.welcome_screen.config.IndicatorAnimation
import com.volpis.welcome_screen.config.IndicatorShape
import com.volpis.welcome_screen.config.PageIndicatorConfig
import kotlin.math.abs
import kotlin.math.min

class PageIndicatorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var config = PageIndicatorConfig()
    private var totalPages = 0
    private var currentPage = 0
    private var targetPage = 0

    private var slideProgress = 0f // 0f to 1f transition progress
    private var isTransitioning = false
    private var animationDirection = 0 // -1 left, 1 right, 0 none

    private var indicatorSpacing = 0f
    private var baseIndicatorSize = 0f
    private var expandedIndicatorWidth = 0f
    private var totalWidth = 0f
    private var startX = 0f

    private val activePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }
    private val inactivePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }
    private val strokePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
    }

    private var animatorSet: AnimatorSet? = null
    private var animatedScale = 1f
    private var animatedAlpha = 1f
    private var animatedRotation = 0f

    private var lastInvalidateTime = 0L
    private val invalidateThreshold = 16L // ~60fps

    init {
        setLayerType(LAYER_TYPE_HARDWARE, null)
        setupPaints()
    }

    private fun setupPaints() {
        activePaint.isAntiAlias = true
        inactivePaint.isAntiAlias = true
        strokePaint.isAntiAlias = true
        strokePaint.style = Paint.Style.STROKE
    }

    fun setupWithViewPager(viewPager: ViewPager2, config: PageIndicatorConfig) {
        this.config = config
        this.totalPages = viewPager.adapter?.itemCount ?: 0
        this.currentPage = 0
        this.targetPage = 0

        updatePaintColors()
        preCalculateValues()

        visibility = if (config.isVisible && totalPages > 1) VISIBLE else GONE

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                handlePageScrolled(position, positionOffset)
            }

            override fun onPageSelected(position: Int) {
                setCurrentPage(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                isTransitioning = state != ViewPager2.SCROLL_STATE_IDLE
                if (state == ViewPager2.SCROLL_STATE_IDLE) {
                    slideProgress = 0f
                    animationDirection = 0
                    optimizedInvalidate()
                }
            }
        })

        requestLayout()
    }

    private fun handlePageScrolled(position: Int, positionOffset: Float) {
        if (abs(positionOffset) < 0.001f && !isTransitioning) return

        val newTargetPage = if (positionOffset > 0.5f) position + 1 else position

        if (targetPage != newTargetPage) {
            targetPage = newTargetPage
            animationDirection = if (newTargetPage > currentPage) 1 else -1
        }

        slideProgress = when {
            positionOffset <= 0.5f -> positionOffset * 2f
            else -> (1f - positionOffset) * 2f
        }

        optimizedInvalidate()
    }

    private fun preCalculateValues() {
        baseIndicatorSize = config.size.toFloat()
        indicatorSpacing = config.spacing.toFloat()

        when (config.animationType) {
            IndicatorAnimation.SLIDE, IndicatorAnimation.MORPHING -> {
                expandedIndicatorWidth = baseIndicatorSize * 2.5f
                totalWidth =
                    ((totalPages - 1) * (baseIndicatorSize + indicatorSpacing)) + expandedIndicatorWidth
            }

            else -> {
                expandedIndicatorWidth = baseIndicatorSize
                totalWidth =
                    (totalPages * baseIndicatorSize) + ((totalPages - 1) * indicatorSpacing)
            }
        }
    }

    private fun updatePaintColors() {
        activePaint.color = config.activeColor
        inactivePaint.color = config.inactiveColor

        if (config.strokeWidth > 0) {
            strokePaint.strokeWidth = config.strokeWidth.toFloat()
            strokePaint.color = config.strokeColor
        }
    }

    fun setCurrentPage(page: Int) {
        if (currentPage != page && page in 0 until totalPages) {
            currentPage = page
            targetPage = page
            slideProgress = 0f
            animationDirection = 0

            if (!isTransitioning) {
                animatePageChange()
            }
        }
    }

    private fun animatePageChange() {
        animatorSet?.cancel()

        when (config.animationType) {
            IndicatorAnimation.SCALE -> animateScale()
            IndicatorAnimation.FADE -> animateFade()
            IndicatorAnimation.BOUNCE -> animateBounce()
            IndicatorAnimation.PULSE -> animatePulse()
            IndicatorAnimation.ROTATE -> animateRotate()
            IndicatorAnimation.SLIDE, IndicatorAnimation.MORPHING -> {
                optimizedInvalidate()
            }

            IndicatorAnimation.NONE -> optimizedInvalidate()
        }
    }

    private fun animateScale() {
        val scaleAnimator = ValueAnimator.ofFloat(1f, 1.2f, 1f).apply {
            duration = min(config.animationDuration.toLong(), 300L)
            interpolator = OvershootInterpolator(0.6f)
            addUpdateListener { animator ->
                animatedScale = animator.animatedValue as Float
                optimizedInvalidate()
            }
        }

        animatorSet = AnimatorSet().apply {
            play(scaleAnimator)
            start()
        }
    }

    private fun animateFade() {
        val fadeAnimator = ValueAnimator.ofFloat(0.6f, 1f).apply {
            duration = min(config.animationDuration.toLong(), 250L)
            interpolator = AccelerateDecelerateInterpolator()
            addUpdateListener { animator ->
                animatedAlpha = animator.animatedValue as Float
                optimizedInvalidate()
            }
        }

        animatorSet = AnimatorSet().apply {
            play(fadeAnimator)
            start()
        }
    }

    private fun animateBounce() {
        val bounceScale = ValueAnimator.ofFloat(1f, 1.15f, 1f).apply {
            duration = min(config.animationDuration.toLong(), 400L)
            interpolator = BounceInterpolator()
            addUpdateListener { animator ->
                animatedScale = animator.animatedValue as Float
                optimizedInvalidate()
            }
        }

        animatorSet = AnimatorSet().apply {
            play(bounceScale)
            start()
        }
    }

    private fun animatePulse() {
        val pulseAnimator = ValueAnimator.ofFloat(1f, 1.1f, 1f).apply {
            duration = min(config.animationDuration.toLong(), 600L)
            repeatCount = 1
            repeatMode = ValueAnimator.REVERSE
            interpolator = LinearInterpolator()
            addUpdateListener { animator ->
                animatedScale = animator.animatedValue as Float
                optimizedInvalidate()
            }
        }

        animatorSet = AnimatorSet().apply {
            play(pulseAnimator)
            start()
        }
    }

    private fun animateRotate() {
        val rotateAnimator = ValueAnimator.ofFloat(0f, 90f).apply {
            duration = min(config.animationDuration.toLong(), 300L)
            interpolator = AccelerateDecelerateInterpolator()
            addUpdateListener { animator ->
                animatedRotation = animator.animatedValue as Float
                optimizedInvalidate()
            }
        }

        animatorSet = AnimatorSet().apply {
            play(rotateAnimator)
            start()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (totalPages <= 1) {
            setMeasuredDimension(0, 0)
            return
        }

        val maxSize = if (config.useProgressiveSizing) config.maxSize else config.size
        val finalWidth = (totalWidth + paddingLeft + paddingRight).toInt()
        val finalHeight = maxSize + paddingTop + paddingBottom

        setMeasuredDimension(finalWidth, finalHeight)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        startX = (w - totalWidth) / 2f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (totalPages <= 1) return

        val centerY = height / 2f
        var currentX = startX

        for (i in 0 until totalPages) {
            val isActive = i == currentPage
            val isTarget = i == targetPage
            val alpha = calculateAlpha(i)
            val size = calculateSize(i)
            val width = calculateWidth(i)

            val paint = if (isActive || isTarget) {
                activePaint.apply {
                    color = config.activeColor
                    this.alpha = alpha
                }
            } else {
                inactivePaint.apply {
                    color = config.inactiveColor
                    this.alpha = alpha
                }
            }

            val centerX = currentX + width / 2f

            canvas.withSave {
                if (config.animationType == IndicatorAnimation.ROTATE && isActive) {
                    rotate(animatedRotation, centerX, centerY)
                }

                drawOptimizedIndicator(
                    canvas = this,
                    centerX = centerX,
                    centerY = centerY,
                    width = width,
                    height = size,
                    paint = paint,
                    isActive = isActive
                )
            }

            currentX += width + indicatorSpacing
        }
    }

    private fun calculateAlpha(index: Int): Int {
        val isActive = index == currentPage
        val isTarget = index == targetPage

        return when (config.animationType) {
            IndicatorAnimation.FADE -> {
                when {
                    isActive -> (255 * animatedAlpha).toInt()
                    isTarget && isTransitioning -> (255 * slideProgress).toInt()
                    else -> (255 * 0.6f).toInt()
                }
            }

            else -> {
                when {
                    isActive || isTarget -> 255
                    else -> (255 * 0.7f).toInt()
                }
            }
        }
    }

    private fun calculateSize(index: Int): Float {
        val isActive = index == currentPage
        val baseSize = if (config.useProgressiveSizing) {
            val distance = abs(index - currentPage)
            when (distance) {
                0 -> config.maxSize.toFloat()
                1 -> ((config.maxSize + config.minSize) / 2f)
                else -> config.minSize.toFloat()
            }
        } else {
            baseIndicatorSize
        }

        return when {
            isActive && (config.animationType == IndicatorAnimation.SCALE ||
                    config.animationType == IndicatorAnimation.BOUNCE ||
                    config.animationType == IndicatorAnimation.PULSE) -> {
                baseSize * animatedScale
            }

            else -> baseSize
        }
    }

    private fun calculateWidth(index: Int): Float {
        val isActive = index == currentPage
        val isTarget = index == targetPage

        return when (config.animationType) {
            IndicatorAnimation.SLIDE, IndicatorAnimation.MORPHING -> {
                when {
                    isActive && !isTransitioning -> expandedIndicatorWidth
                    isActive && isTransitioning -> {
                        val progress = 1f - slideProgress
                        baseIndicatorSize + (expandedIndicatorWidth - baseIndicatorSize) * progress
                    }

                    isTarget && isTransitioning -> {
                        baseIndicatorSize + (expandedIndicatorWidth - baseIndicatorSize) * slideProgress
                    }

                    else -> baseIndicatorSize
                }
            }

            else -> calculateSize(index)
        }
    }

    private fun drawOptimizedIndicator(
        canvas: Canvas,
        centerX: Float,
        centerY: Float,
        width: Float,
        height: Float,
        paint: Paint,
        isActive: Boolean
    ) {
        when (config.shape) {
            IndicatorShape.CIRCLE -> {
                val radius = height / 2f
                canvas.drawCircle(centerX, centerY, radius, paint)

                if (config.strokeWidth > 0 && isActive) {
                    canvas.drawCircle(centerX, centerY, radius, strokePaint)
                }
            }

            IndicatorShape.ROUNDED_RECTANGLE -> {
                val left = centerX - width / 2f
                val top = centerY - height / 2f
                val right = centerX + width / 2f
                val bottom = centerY + height / 2f

                canvas.drawRoundRect(
                    left, top, right, bottom,
                    config.customCornerRadius,
                    config.customCornerRadius,
                    paint
                )

                if (config.strokeWidth > 0 && isActive) {
                    canvas.drawRoundRect(
                        left, top, right, bottom,
                        config.customCornerRadius,
                        config.customCornerRadius,
                        strokePaint
                    )
                }
            }

            IndicatorShape.RECTANGLE -> {
                val left = centerX - width / 2f
                val top = centerY - height / 2f
                val right = centerX + width / 2f
                val bottom = centerY + height / 2f

                canvas.drawRect(left, top, right, bottom, paint)
            }

            IndicatorShape.DIAMOND -> {
                val path = Path().apply {
                    moveTo(centerX, centerY - height / 2f)
                    lineTo(centerX + width / 2f, centerY)
                    lineTo(centerX, centerY + height / 2f)
                    lineTo(centerX - width / 2f, centerY)
                    close()
                }
                canvas.drawPath(path, paint)
            }
        }
    }

    private fun optimizedInvalidate() {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastInvalidateTime >= invalidateThreshold) {
            invalidate()
            lastInvalidateTime = currentTime
        } else {
            post { invalidate() }
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        animatorSet?.cancel()
        animatorSet = null
    }

    fun resetAnimations() {
        animatorSet?.cancel()
        animatedScale = 1f
        animatedAlpha = 1f
        animatedRotation = 0f
        slideProgress = 0f
        animationDirection = 0
        isTransitioning = false
        optimizedInvalidate()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        setLayerType(LAYER_TYPE_HARDWARE, null)
    }
}