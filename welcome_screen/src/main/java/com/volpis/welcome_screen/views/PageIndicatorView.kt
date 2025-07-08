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
    private var animatedTranslationX = 0f
    private var animatedColor = 0

    private var morphProgress = 0f
    private var slidePosition = 0f

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
                pageOffset = positionOffset

                slidePosition = position + positionOffset

                invalidate()
            }

            override fun onPageSelected(position: Int) {
                setCurrentPage(position)
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
            val oldPage = currentPage
            currentPage = page
            animatePageChange(oldPage, page)
        }
    }

    private fun animatePageChange(fromPage: Int, toPage: Int) {
        animatorSet?.cancel()

        when (config.animationType) {
            IndicatorAnimation.SCALE -> animateScale()
            IndicatorAnimation.FADE -> animateFade()
            IndicatorAnimation.SLIDE -> animateSlide()
            IndicatorAnimation.MORPHING -> animateMorphing()
            IndicatorAnimation.BOUNCE -> animateBounce()
            IndicatorAnimation.PULSE -> animatePulse()
            IndicatorAnimation.ROTATE -> animateRotate()
            IndicatorAnimation.NONE -> invalidate()
        }
    }

    private fun animateScale() {
        val scaleAnimator = ValueAnimator.ofFloat(animatedScale, 1.2f, 1f).apply {
            duration = config.animationDuration.toLong()
            interpolator = OvershootInterpolator(1.2f)
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
        val fadeOut = ValueAnimator.ofFloat(1f, 0.3f).apply {
            duration = (config.animationDuration / 2).toLong()
            addUpdateListener { animator ->
                animatedAlpha = animator.animatedValue as Float
                invalidate()
            }
        }

        val fadeIn = ValueAnimator.ofFloat(0.3f, 1f).apply {
            duration = (config.animationDuration / 2).toLong()
            addUpdateListener { animator ->
                animatedAlpha = animator.animatedValue as Float
                invalidate()
            }
        }

        animatorSet = AnimatorSet().apply {
            playSequentially(fadeOut, fadeIn)
            start()
        }
    }

    private fun animateSlide() {
        val slideAnimator = ValueAnimator.ofFloat(slidePosition, currentPage.toFloat()).apply {
            duration = config.animationDuration.toLong()
            interpolator = AccelerateDecelerateInterpolator()
            addUpdateListener { animator ->
                slidePosition = animator.animatedValue as Float
                invalidate()
            }
        }

        animatorSet = AnimatorSet().apply {
            play(slideAnimator)
            start()
        }
    }

    private fun animateMorphing() {
        val morphAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = config.animationDuration.toLong()
            interpolator = AccelerateDecelerateInterpolator()
            addUpdateListener { animator ->
                morphProgress = animator.animatedValue as Float
                invalidate()
            }
        }

        animatorSet = AnimatorSet().apply {
            play(morphAnimator)
            start()
        }
    }

    private fun animateBounce() {
        val bounceScale = ValueAnimator.ofFloat(1f, 1.4f, 1f).apply {
            duration = config.animationDuration.toLong()
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
        val pulseAnimator = ValueAnimator.ofFloat(1f, 1.3f, 1f).apply {
            duration = config.animationDuration.toLong() * 2
            repeatCount = ValueAnimator.INFINITE
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
        val rotateAnimator = ValueAnimator.ofFloat(0f, 360f).apply {
            duration = config.animationDuration.toLong()
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
                val regularIndicators = totalPages - 1
                val activeIndicatorWidth = config.size * 2.5f
                (regularIndicators * (config.size + config.spacing) + activeIndicatorWidth).toInt()
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
        var currentX = paddingLeft.toFloat()

        for (i in 0 until totalPages) {
            val isActive = i == currentPage
            val distanceFromActive = abs(i - slidePosition)

            // Calculate size based on proximity to active position
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

            // Apply animation scaling
            val finalSize = baseSize * if (isActive) animatedScale else 1f

            // Calculate colors with smooth transitions
            val alpha = when (config.animationType) {
                IndicatorAnimation.FADE -> if (isActive) (255 * animatedAlpha).toInt() else 255
                else -> if (distanceFromActive < 1f) 255 else (255 * 0.6f).toInt()
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

            // Calculate indicator width for slide/morphing animations
            val indicatorWidth = when (config.animationType) {
                IndicatorAnimation.SLIDE, IndicatorAnimation.MORPHING -> {
                    if (isActive) finalSize * 2.5f else finalSize
                }

                else -> finalSize
            }

            val centerX = currentX + indicatorWidth / 2f

            // Apply rotation for rotate animation
            canvas.withSave {
                if (config.animationType == IndicatorAnimation.ROTATE && isActive) {
                    rotate(animatedRotation, centerX, centerY)
                }

                // Draw the indicator based on shape
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
                canvas.drawCircle(centerX, centerY, height / 2f, paint)

                if (config.strokeWidth > 0 && isActive) {
                    canvas.drawCircle(centerX, centerY, height / 2f, strokePaint)
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
        animatedTranslationX = 0f
        morphProgress = 0f
        slidePosition = currentPage.toFloat()
        invalidate()
    }
}