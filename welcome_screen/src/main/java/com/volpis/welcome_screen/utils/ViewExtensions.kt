package com.volpis.welcome_screen.utils

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.BlurMaskFilter
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Button
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import com.volpis.welcome_screen.config.ButtonConfig
import com.volpis.welcome_screen.config.ButtonPlacement
import com.volpis.welcome_screen.config.WelcomeTypeface

fun Context.dpToPx(dp: Int): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp.toFloat(),
        resources.displayMetrics
    ).toInt()
}

fun Context.spToPx(sp: Float): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        sp,
        resources.displayMetrics
    )
}

fun View.setRoundedCorners(radius: Float) {
    val drawable = GradientDrawable().apply {
        cornerRadius = radius
        setColor(ContextCompat.getColor(context, android.R.color.transparent))
    }
    background = drawable
    clipToOutline = true
}

fun View.setGradientBackground(startColor: Int, endColor: Int) {
    val gradient = GradientDrawable(
        GradientDrawable.Orientation.TOP_BOTTOM,
        intArrayOf(startColor, endColor)
    )
    background = gradient
}

fun View.addGlowEffect(glowColor: Int, glowRadius: Float) {
    setLayerType(View.LAYER_TYPE_SOFTWARE, null)
    val paint = Paint().apply {
        maskFilter = BlurMaskFilter(glowRadius, BlurMaskFilter.Blur.OUTER)
        color = glowColor
    }
    ViewCompat.setElevation(this, glowRadius)
}

fun Button.configure(buttonConfig: ButtonConfig, onClick: () -> Unit) {
    if (!buttonConfig.isVisible) {
        visibility = View.GONE
        return
    }

    visibility = View.VISIBLE
    text = buttonConfig.text

    val style = buttonConfig.style

    setTextColor(style.textColor)
    setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textSize)

    typeface = when (style.fontWeight) {
        in 600..900 -> WelcomeTypeface.DEFAULT_BOLD.toTypeface()
        else -> style.typeface.toTypeface()
    }

    val backgroundDrawable = GradientDrawable().apply {
        setColor(style.backgroundColor)
        cornerRadius = style.cornerRadius

        if (style.borderWidth > 0) {
            setStroke(style.borderWidth, style.borderColor)
        }
    }

    background = backgroundDrawable

    setPadding(
        style.paddingHorizontal,
        style.paddingVertical,
        style.paddingHorizontal,
        style.paddingVertical
    )

    if (style.isUnderlined || style.isBold) {
        val paintFlags = paint.flags
        if (style.isUnderlined) {
            paint.flags = paintFlags or Paint.UNDERLINE_TEXT_FLAG
        }
        if (style.isBold || style.fontWeight >= 600) {
            typeface = Typeface.DEFAULT_BOLD
        }
    }

    minWidth = context.dpToPx(88)
    minHeight = context.dpToPx(48)

    elevation = context.dpToPx(2).toFloat() // Reduced elevation for better performance

    isClickable = true
    isFocusable = true

    // Simplified click handling - removed animations for better performance
    setOnClickListener {
        try {
            performHapticFeedback(android.view.HapticFeedbackConstants.VIRTUAL_KEY)
        } catch (e: Exception) {
            // Ignore if haptic feedback is not available
        }
        onClick()
    }

    contentDescription = "${buttonConfig.text} button"

    // Start with full visibility and scale for immediate responsiveness
    alpha = 1f
    scaleX = 1f
    scaleY = 1f
}

fun View.positionButton(placement: ButtonPlacement, parentWidth: Int, systemBarHeight: Int) {
    val layoutParams = FrameLayout.LayoutParams(
        FrameLayout.LayoutParams.WRAP_CONTENT,
        FrameLayout.LayoutParams.WRAP_CONTENT
    )

    val margin16 = context.dpToPx(16)
    val margin24 = context.dpToPx(24)
    val margin32 = context.dpToPx(32)
    val margin48 = context.dpToPx(48) // Additional margin for better spacing

    when (placement) {
        ButtonPlacement.TOP_LEFT -> {
            layoutParams.gravity = Gravity.TOP or Gravity.START
            layoutParams.setMargins(margin24, margin32, 0, 0)
        }

        ButtonPlacement.TOP_RIGHT -> {
            layoutParams.gravity = Gravity.TOP or Gravity.END
            layoutParams.setMargins(0, margin32, margin24, 0)
        }

        ButtonPlacement.TOP_CENTER -> {
            layoutParams.gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
            layoutParams.setMargins(margin24, margin32, margin24, 0)
        }

        ButtonPlacement.BOTTOM_LEFT -> {
            layoutParams.gravity = Gravity.BOTTOM or Gravity.START
            // Increased margins and spacing for better separation
            layoutParams.setMargins(margin24, 0, margin48, margin32 + systemBarHeight)
        }

        ButtonPlacement.BOTTOM_RIGHT -> {
            layoutParams.gravity = Gravity.BOTTOM or Gravity.END
            // Increased margins and spacing for better separation
            layoutParams.setMargins(margin48, 0, margin24, margin32 + systemBarHeight)
        }

        ButtonPlacement.BOTTOM_CENTER -> {
            layoutParams.gravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
            layoutParams.setMargins(margin32, 0, margin32, margin32 + systemBarHeight)
            // Full width for center buttons to prevent overlap
            layoutParams.width = FrameLayout.LayoutParams.MATCH_PARENT
        }

        ButtonPlacement.CENTER_LEFT -> {
            layoutParams.gravity = Gravity.CENTER_VERTICAL or Gravity.START
            layoutParams.setMargins(margin24, 0, 0, 0)
        }

        ButtonPlacement.CENTER_RIGHT -> {
            layoutParams.gravity = Gravity.CENTER_VERTICAL or Gravity.END
            layoutParams.setMargins(0, 0, margin24, 0)
        }
    }

    this.layoutParams = layoutParams
}

fun Context.createStyledButton(buttonConfig: ButtonConfig): Button {
    return Button(this).apply {
        // Remove state list animator for better performance
        stateListAnimator = null

        // Apply configuration
        configure(buttonConfig) { /* onClick will be set separately */ }

        isClickable = true
        isFocusable = true
        importantForAccessibility = View.IMPORTANT_FOR_ACCESSIBILITY_YES

        // Optimize for performance
        setLayerType(View.LAYER_TYPE_NONE, null)
    }
}

// Performance-optimized animation functions
fun View.fadeIn(duration: Long = 200, delay: Long = 0) {
    if (alpha == 1f) return // Skip if already visible

    alpha = 0f
    animate()
        .alpha(1f)
        .setDuration(duration)
        .setStartDelay(delay)
        .setInterpolator(AccelerateDecelerateInterpolator())
        .start()
}

fun View.fadeOut(duration: Long = 200, delay: Long = 0) {
    if (alpha == 0f) return // Skip if already hidden

    animate()
        .alpha(0f)
        .setDuration(duration)
        .setStartDelay(delay)
        .setInterpolator(AccelerateDecelerateInterpolator())
        .start()
}

fun View.slideInFromBottom(duration: Long = 300, delay: Long = 0) {
    translationY = context.dpToPx(30).toFloat() // Reduced translation for better performance
    alpha = 0f
    animate()
        .translationY(0f)
        .alpha(1f)
        .setDuration(duration)
        .setStartDelay(delay)
        .setInterpolator(AccelerateDecelerateInterpolator())
        .start()
}

fun View.slideOutToBottom(duration: Long = 300, delay: Long = 0) {
    animate()
        .translationY(context.dpToPx(30).toFloat())
        .alpha(0f)
        .setDuration(duration)
        .setStartDelay(delay)
        .setInterpolator(AccelerateDecelerateInterpolator())
        .start()
}

fun View.scaleIn(duration: Long = 250, delay: Long = 0) {
    scaleX = 0.9f // Less dramatic scaling for better performance
    scaleY = 0.9f
    alpha = 0f
    animate()
        .scaleX(1f)
        .scaleY(1f)
        .alpha(1f)
        .setDuration(duration)
        .setStartDelay(delay)
        .setInterpolator(AccelerateDecelerateInterpolator())
        .start()
}

// Simplified pulse animation with limited duration for performance
fun View.pulseAnimation(duration: Long = 800, cycles: Int = 3) {
    val animator = ValueAnimator.ofFloat(1f, 1.05f, 1f)
    animator.duration = duration
    animator.repeatCount = cycles - 1 // Limited cycles for performance
    animator.addUpdateListener { animation ->
        val scale = animation.animatedValue as Float
        scaleX = scale
        scaleY = scale
    }
    animator.start()
}

fun Int.withAlpha(alpha: Float): Int {
    val a = (alpha * 255).toInt().coerceIn(0, 255)
    return (this and 0x00FFFFFF) or (a shl 24)
}

fun interpolateColor(color1: Int, color2: Int, fraction: Float): Int {
    val clampedFraction = fraction.coerceIn(0f, 1f)

    val a1 = (color1 shr 24 and 0xff) / 255f
    val r1 = (color1 shr 16 and 0xff) / 255f
    val g1 = (color1 shr 8 and 0xff) / 255f
    val b1 = (color1 and 0xff) / 255f

    val a2 = (color2 shr 24 and 0xff) / 255f
    val r2 = (color2 shr 16 and 0xff) / 255f
    val g2 = (color2 shr 8 and 0xff) / 255f
    val b2 = (color2 and 0xff) / 255f

    val a = a1 + (a2 - a1) * clampedFraction
    val r = r1 + (r2 - r1) * clampedFraction
    val g = g1 + (g2 - g1) * clampedFraction
    val b = b1 + (b2 - b1) * clampedFraction

    return ((a * 255).toInt() shl 24) or
            ((r * 255).toInt() shl 16) or
            ((g * 255).toInt() shl 8) or
            (b * 255).toInt()
}

// Extension to check if multiple buttons are using the same placement
fun List<ButtonPlacement>.hasConflict(): Boolean {
    return this.groupBy { it }.any { it.value.size > 1 }
}

// Extension to get safe margins for button placement
fun ButtonPlacement.getSafeMargins(context: Context): IntArray {
    val baseMargin = context.dpToPx(24)
    val extraMargin = context.dpToPx(16)

    return when (this) {
        ButtonPlacement.BOTTOM_LEFT -> intArrayOf(
            baseMargin,
            0,
            baseMargin + extraMargin,
            baseMargin
        )

        ButtonPlacement.BOTTOM_RIGHT -> intArrayOf(
            baseMargin + extraMargin,
            0,
            baseMargin,
            baseMargin
        )

        ButtonPlacement.BOTTOM_CENTER -> intArrayOf(baseMargin * 2, 0, baseMargin * 2, baseMargin)
        else -> intArrayOf(baseMargin, baseMargin, baseMargin, baseMargin)
    }
}