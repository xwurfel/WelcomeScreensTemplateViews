package com.volpis.welcome_screen.views

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.BounceInterpolator
import android.view.animation.OvershootInterpolator
import androidx.core.graphics.withSave
import androidx.viewpager2.widget.ViewPager2
import com.volpis.welcome_screen.config.IndicatorAnimation
import com.volpis.welcome_screen.config.IndicatorShape
import com.volpis.welcome_screen.config.PageIndicatorConfig
import kotlin.math.abs

class PageIndicatorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var config = PageIndicatorConfig()
    private var totalPages = 0
    private var currentPage = 0
    private var pageOffset = 0f

    private val activePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val inactivePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val strokePaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var animatorSet: AnimatorSet? = null
    private var animatedScale = 1f
    private var animatedAlpha = 1f
    private var animatedRotation = 0f
    private var animatedColor = 0

    private var slideOffset = 0f
    private var isAnimating = false

    init {
        setupPaints()
    }

    private fun setupPaints() {
        activePaint.style = Paint.Style.FILL
        inactivePaint.style = Paint.Style.FILL
        strokePaint.style = Paint.Style.STROKE
    }

    fun setupWithViewPager(viewPager: ViewPager2, config: PageIndicatorConfig) {
        this.config = config
        this.totalPages = viewPager.adapter?.itemCount ?: 0

        updatePaintColors()
        visibility = if (config.isVisible && totalPages > 1) VISIBLE else GONE

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                // Immediate visual feedback for smooth scrolling
                pageOffset = positionOffset
                slideOffset = position + positionOffset
                invalidate()
            }

            override fun onPageSelected(position: Int) {
                setCurrentPage(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                isAnimating = state != ViewPager2.SCROLL_STATE_IDLE
            }
        })

        requestLayout()
    }

    private fun updatePaintColors() {
        activePaint.color = config.activeColor
        inactivePaint.color = config.inactiveColor

        if (config.strokeWidth > 0) {
            strokePaint.strokeWidth = config.strokeWidth.toFloat()
            strokePaint.color = config.strokeColor
        }

        animatedColor = config.activeColor
    }

    fun setCurrentPage(page: Int) {
        if (currentPage != page) {
            currentPage = page
            if (!isAnimating) {
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
                invalidate()
            }

            IndicatorAnimation.NONE -> invalidate()
        }
    }

    private fun animateScale() {
        val scaleAnimator = ValueAnimator.ofFloat(1f, 1.3f, 1f).apply {
            duration = (config.animationDuration * 0.6f).toLong() // Faster animation
            interpolator = OvershootInterpolator(0.8f) // Reduced overshoot
            addUpdateListener { animator ->
                animatedScale = animator.animatedValue as Float
                invalidate()
            }
        }

        animatorSet = AnimatorSet().apply {
            play(scaleAnimator)
            start()
        }
    }

    private fun animateFade() {
        val fadeAnimator = ValueAnimator.ofFloat(0.5f, 1f).apply {
            duration = (config.animationDuration * 0.5f).toLong()
            interpolator = AccelerateDecelerateInterpolator()
            addUpdateListener { animator ->
                animatedAlpha = animator.animatedValue as Float
                invalidate()
            }
        }

        animatorSet = AnimatorSet().apply {
            play(fadeAnimator)
            start()
        }
    }

    private fun animateBounce() {
        val bounceScale = ValueAnimator.ofFloat(1f, 1.2f, 1f).apply {
            duration = (config.animationDuration * 0.7f).toLong()
            interpolator = BounceInterpolator()
            addUpdateListener { animator ->
                animatedScale = animator.animatedValue as Float
                invalidate()
            }
        }

        animatorSet = AnimatorSet().apply {
            play(bounceScale)
            start()
        }
    }

    private fun animatePulse() {
        val pulseAnimator = ValueAnimator.ofFloat(1f, 1.15f, 1f).apply {
            duration = config.animationDuration.toLong()
            repeatCount = 2 // Limited repeats for performance
            repeatMode = ValueAnimator.REVERSE
            interpolator = AccelerateDecelerateInterpolator()
            addUpdateListener { animator ->
                animatedScale = animator.animatedValue as Float
                invalidate()
            }
        }

        animatorSet = AnimatorSet().apply {
            play(pulseAnimator)
            start()
        }
    }

    private fun animateRotate() {
        val rotateAnimator = ValueAnimator.ofFloat(0f, 180f).apply {
            duration = (config.animationDuration * 0.8f).toLong()
            interpolator = AccelerateDecelerateInterpolator()
            addUpdateListener { animator ->
                animatedRotation = animator.animatedValue as Float
                invalidate()
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

        val indicatorWidth = when (config.animationType) {
            IndicatorAnimation.SLIDE, IndicatorAnimation.MORPHING -> {
                // Calculate width for slide animation with proper spacing
                val regularIndicators = totalPages - 1
                val activeIndicatorWidth = config.size * 2.5f
                (regularIndicators * (config.size + config.spacing) + activeIndicatorWidth + config.spacing).toInt()
            }

            else -> {
                totalPages * config.size + (totalPages - 1) * config.spacing
            }
        }

        val indicatorHeight = if (config.useProgressiveSizing) {
            config.maxSize
        } else {
            config.size
        }

        val finalWidth = indicatorWidth + paddingLeft + paddingRight
        val finalHeight = indicatorHeight + paddingTop + paddingBottom

        setMeasuredDimension(finalWidth, finalHeight)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (totalPages <= 1) return

        val centerY = height / 2f

        val totalIndicatorWidth: Float = when (config.animationType) {
            IndicatorAnimation.SLIDE, IndicatorAnimation.MORPHING -> {
                val regularWidth = (totalPages - 1) * (config.size + config.spacing)
                val activeWidth = config.size * 2.5f
                regularWidth + activeWidth
            }

            else -> {
                (totalPages * config.size + (totalPages - 1) * config.spacing).toFloat()
            }
        }

        var currentX = (width - totalIndicatorWidth) / 2f

        for (i in 0 until totalPages) {
            val distanceFromActive = abs(i - slideOffset)
            val isActive = i == currentPage
            val baseSize = if (config.useProgressiveSizing) {
                when {
                    distanceFromActive < 0.5f -> config.maxSize.toFloat()
                    distanceFromActive < 1.5f -> {
                        val progress = (distanceFromActive - 0.5f)
                        config.maxSize - progress * (config.maxSize - config.minSize)
                    }

                    else -> config.minSize.toFloat()
                }
            } else {
                config.size.toFloat()
            }

            val finalSize = if (isActive) baseSize * animatedScale else baseSize

            val alpha = when (config.animationType) {
                IndicatorAnimation.FADE -> {
                    if (isActive) (255 * animatedAlpha).toInt()
                    else if (distanceFromActive < 1f) (255 * (1f - distanceFromActive * 0.4f)).toInt()
                    else (255 * 0.6f).toInt()
                }

                else -> {
                    if (distanceFromActive < 1f) (255 * (1f - distanceFromActive * 0.3f)).toInt()
                    else (255 * 0.7f).toInt()
                }
            }

            val paint = if (isActive || distanceFromActive < 1f) {
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

            val indicatorWidth = when (config.animationType) {
                IndicatorAnimation.SLIDE, IndicatorAnimation.MORPHING -> {
                    if (isActive) {
                        val baseWidth = finalSize * 2.5f
                        if (isAnimating) {
                            val offsetFactor = 1f - abs(pageOffset)
                            finalSize + (baseWidth - finalSize) * offsetFactor
                        } else {
                            baseWidth
                        }
                    } else {
                        finalSize
                    }
                }

                else -> finalSize
            }

            val centerX = currentX + indicatorWidth / 2f

            canvas.withSave {
                if (config.animationType == IndicatorAnimation.ROTATE && isActive) {
                    rotate(animatedRotation, centerX, centerY)
                }

                drawIndicator(
                    canvas = this,
                    centerX = centerX,
                    centerY = centerY,
                    width = indicatorWidth,
                    height = finalSize,
                    paint = paint,
                    isActive = isActive
                )
            }

            currentX += indicatorWidth + config.spacing
        }
    }

    private fun drawIndicator(
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
                val rect = RectF(
                    centerX - width / 2f,
                    centerY - height / 2f,
                    centerX + width / 2f,
                    centerY + height / 2f
                )

                canvas.drawRoundRect(
                    rect,
                    config.customCornerRadius,
                    config.customCornerRadius,
                    paint
                )

                if (config.strokeWidth > 0 && isActive) {
                    canvas.drawRoundRect(
                        rect,
                        config.customCornerRadius,
                        config.customCornerRadius,
                        strokePaint
                    )
                }
            }

            IndicatorShape.RECTANGLE -> {
                val rect = RectF(
                    centerX - width / 2f,
                    centerY - height / 2f,
                    centerX + width / 2f,
                    centerY + height / 2f
                )
                canvas.drawRect(rect, paint)
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

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        animatorSet?.cancel()
    }

    fun resetAnimations() {
        animatorSet?.cancel()
        animatedScale = 1f
        animatedAlpha = 1f
        animatedRotation = 0f
        slideOffset = currentPage.toFloat()
        isAnimating = false
        invalidate()
    }
}