package com.volpis.welcome_screen.views

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
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

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val strokePaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var animatorSet: AnimatorSet? = null
    private var animatedPosition = 0f
    private var animatedSize = 0f
    private var animatedAlpha = 1f
    private var animatedRotation = 0f

    init {
        setupPaints()
    }

    private fun setupPaints() {
        paint.style = Paint.Style.FILL
        strokePaint.style = Paint.Style.STROKE
    }

    fun setupWithViewPager(viewPager: ViewPager2, config: PageIndicatorConfig) {
        this.config = config
        this.totalPages = viewPager.adapter?.itemCount ?: 0

        setupPaints()
        visibility = if (config.isVisible && totalPages > 1) VISIBLE else GONE

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                pageOffset = positionOffset
                invalidate()
            }

            override fun onPageSelected(position: Int) {
                setCurrentPage(position)
            }
        })

        requestLayout()
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
        val scaleAnimator = ValueAnimator.ofFloat(animatedSize, config.size.toFloat()).apply {
            duration = config.animationDuration.toLong()
            interpolator = AccelerateDecelerateInterpolator()
            addUpdateListener { animator ->
                animatedSize = animator.animatedValue as Float
                invalidate()
            }
        }

        animatorSet = AnimatorSet().apply {
            play(scaleAnimator)
            start()
        }
    }

    private fun animateFade() {
        val fadeAnimator = ValueAnimator.ofFloat(animatedAlpha, 1f).apply {
            duration = config.animationDuration.toLong()
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

    private fun animateSlide() {
        val slideAnimator = ValueAnimator.ofFloat(animatedPosition, currentPage.toFloat()).apply {
            duration = config.animationDuration.toLong()
            interpolator = AccelerateDecelerateInterpolator()
            addUpdateListener { animator ->
                animatedPosition = animator.animatedValue as Float
                invalidate()
            }
        }

        animatorSet = AnimatorSet().apply {
            play(slideAnimator)
            start()
        }
    }

    private fun animateMorphing() {
        animateSlide()
    }

    private fun animateBounce() {
        val bounceAnimator = ObjectAnimator.ofFloat(this, "scaleX", 1f, 1.3f, 1f).apply {
            duration = config.animationDuration.toLong()
            repeatCount = 2
        }

        val bounceY = ObjectAnimator.ofFloat(this, "scaleY", 1f, 1.3f, 1f).apply {
            duration = config.animationDuration.toLong()
            repeatCount = 2
        }

        animatorSet = AnimatorSet().apply {
            playTogether(bounceAnimator, bounceY)
            start()
        }
    }

    private fun animatePulse() {
        val pulseAnimator = ValueAnimator.ofFloat(0.5f, 1f).apply {
            duration = config.animationDuration.toLong() * 2
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.REVERSE
            addUpdateListener { animator ->
                animatedAlpha = animator.animatedValue as Float
                invalidate()
            }
        }

        animatorSet = AnimatorSet().apply {
            play(pulseAnimator)
            start()
        }
    }

    private fun animateRotate() {
        val rotateAnimator =
            ValueAnimator.ofFloat(animatedRotation, animatedRotation + 360f).apply {
                duration = config.animationDuration.toLong() * 4
                repeatCount = ValueAnimator.INFINITE
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

        val width = when (config.animationType) {
            IndicatorAnimation.SLIDE, IndicatorAnimation.MORPHING -> {
                (totalPages - 1) * (config.size + config.spacing) + (config.size * 2.5f).toInt()
            }

            else -> {
                totalPages * config.size + (totalPages - 1) * config.spacing
            }
        }

        val height = if (config.useProgressiveSizing) config.maxSize else config.size

        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (totalPages <= 1) return

        val centerY = height / 2f
        var startX = 0f

        for (i in 0 until totalPages) {
            val isActive = i == currentPage
            val isNear = abs(i - currentPage) <= 1

            val indicatorSize = when {
                isActive && config.useProgressiveSizing -> config.maxSize.toFloat()
                isNear && config.useProgressiveSizing -> ((config.maxSize + config.minSize) / 2).toFloat()
                config.useProgressiveSizing -> config.minSize.toFloat()
                else -> config.size.toFloat()
            }

            val color = if (isActive) config.activeColor else config.inactiveColor
            paint.color = color

            if (config.strokeWidth > 0) {
                strokePaint.strokeWidth = config.strokeWidth.toFloat()
                strokePaint.color = config.strokeColor
            }

            val currentSize = when (config.animationType) {
                IndicatorAnimation.SCALE -> if (isActive) indicatorSize * 1.2f else indicatorSize
                IndicatorAnimation.FADE -> indicatorSize
                else -> indicatorSize
            }

            val currentAlpha = when (config.animationType) {
                IndicatorAnimation.FADE -> if (isActive) 255 else (255 * 0.3f).toInt()
                IndicatorAnimation.PULSE -> if (isActive) (255 * animatedAlpha).toInt() else 255
                else -> 255
            }

            paint.alpha = currentAlpha

            val centerX = startX + currentSize / 2f

            canvas.withSave {
                if (config.animationType == IndicatorAnimation.ROTATE && isActive) {
                    rotate(animatedRotation, centerX, centerY)
                }

                when (config.shape) {
                    IndicatorShape.CIRCLE -> {
                        drawCircle(centerX, centerY, currentSize / 2f, paint)
                        if (config.strokeWidth > 0) {
                            drawCircle(centerX, centerY, currentSize / 2f, strokePaint)
                        }
                    }

                    IndicatorShape.ROUNDED_RECTANGLE -> {
                        val width =
                            if (config.animationType == IndicatorAnimation.SLIDE && isActive) {
                                currentSize * 2.5f
                            } else currentSize

                        val rect = RectF(
                            centerX - width / 2f,
                            centerY - currentSize / 2f,
                            centerX + width / 2f,
                            centerY + currentSize / 2f
                        )
                        drawRoundRect(
                            rect,
                            config.customCornerRadius,
                            config.customCornerRadius,
                            paint
                        )
                    }

                    IndicatorShape.RECTANGLE -> {
                        val rect = RectF(
                            centerX - currentSize / 2f,
                            centerY - currentSize / 2f,
                            centerX + currentSize / 2f,
                            centerY + currentSize / 2f
                        )
                        drawRect(rect, paint)
                    }

                    IndicatorShape.DIAMOND -> {
                        val path = Path().apply {
                            moveTo(centerX, centerY - currentSize / 2f)
                            lineTo(centerX + currentSize / 2f, centerY)
                            lineTo(centerX, centerY + currentSize / 2f)
                            lineTo(centerX - currentSize / 2f, centerY)
                            close()
                        }
                        drawPath(path, paint)
                    }
                }

            }

            startX += when (config.animationType) {
                IndicatorAnimation.SLIDE, IndicatorAnimation.MORPHING -> {
                    if (isActive) currentSize * 2.5f + config.spacing else currentSize + config.spacing
                }

                else -> currentSize + config.spacing
            }
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        animatorSet?.cancel()
    }
}