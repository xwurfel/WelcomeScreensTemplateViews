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

    elevation = context.dpToPx(4).toFloat()

    isClickable = true
    isFocusable = true

    setOnClickListener {
        performHapticFeedback(android.view.HapticFeedbackConstants.VIRTUAL_KEY)
        onClick()
    }

    contentDescription = "${buttonConfig.text} button"

    alpha = 0f
    scaleX = 0.9f
    scaleY = 0.9f
}

fun View.positionButton(placement: ButtonPlacement, parentWidth: Int, parentHeight: Int) {
    val layoutParams = FrameLayout.LayoutParams(
        FrameLayout.LayoutParams.WRAP_CONTENT,
        FrameLayout.LayoutParams.WRAP_CONTENT
    )

    val margin16 = context.dpToPx(16)
    val margin24 = context.dpToPx(24)
    val margin32 = context.dpToPx(32)

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
            layoutParams.setMargins(margin24, 0, 0, margin32)
        }

        ButtonPlacement.BOTTOM_RIGHT -> {
            layoutParams.gravity = Gravity.BOTTOM or Gravity.END
            layoutParams.setMargins(0, 0, margin24, margin32)
        }

        ButtonPlacement.BOTTOM_CENTER -> {
            layoutParams.gravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
            layoutParams.setMargins(margin24, 0, margin24, margin32)
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
        val outValue = TypedValue()
        theme.resolveAttribute(android.R.attr.selectableItemBackground, outValue, true)

        configure(buttonConfig) { /* onClick will be set separately */ }

        stateListAnimator = null

        isClickable = true
        isFocusable = true
        importantForAccessibility = View.IMPORTANT_FOR_ACCESSIBILITY_YES
    }
}

fun View.fadeIn(duration: Long = 300, delay: Long = 0) {
    alpha = 0f
    animate()
        .alpha(1f)
        .setDuration(duration)
        .setStartDelay(delay)
        .setInterpolator(AccelerateDecelerateInterpolator())
        .start()
}

fun View.fadeOut(duration: Long = 300, delay: Long = 0) {
    animate()
        .alpha(0f)
        .setDuration(duration)
        .setStartDelay(delay)
        .setInterpolator(AccelerateDecelerateInterpolator())
        .start()
}

fun View.slideInFromBottom(duration: Long = 400, delay: Long = 0) {
    translationY = height.toFloat()
    alpha = 0f
    animate()
        .translationY(0f)
        .alpha(1f)
        .setDuration(duration)
        .setStartDelay(delay)
        .setInterpolator(AccelerateDecelerateInterpolator())
        .start()
}

fun View.slideOutToBottom(duration: Long = 400, delay: Long = 0) {
    animate()
        .translationY(height.toFloat())
        .alpha(0f)
        .setDuration(duration)
        .setStartDelay(delay)
        .setInterpolator(AccelerateDecelerateInterpolator())
        .start()
}

fun View.scaleIn(duration: Long = 300, delay: Long = 0) {
    scaleX = 0.8f
    scaleY = 0.8f
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

fun View.pulseAnimation(duration: Long = 1000) {
    val animator = ValueAnimator.ofFloat(1f, 1.1f, 1f)
    animator.duration = duration
    animator.repeatCount = ValueAnimator.INFINITE
    animator.addUpdateListener { animation ->
        val scale = animation.animatedValue as Float
        scaleX = scale
        scaleY = scale
    }
    animator.start()
}

fun Int.withAlpha(alpha: Float): Int {
    val a = (alpha * 255).toInt()
    return (this and 0x00FFFFFF) or (a shl 24)
}

fun interpolateColor(color1: Int, color2: Int, fraction: Float): Int {
    val a1 = (color1 shr 24 and 0xff) / 255f
    val r1 = (color1 shr 16 and 0xff) / 255f
    val g1 = (color1 shr 8 and 0xff) / 255f
    val b1 = (color1 and 0xff) / 255f

    val a2 = (color2 shr 24 and 0xff) / 255f
    val r2 = (color2 shr 16 and 0xff) / 255f
    val g2 = (color2 shr 8 and 0xff) / 255f
    val b2 = (color2 and 0xff) / 255f

    val a = a1 + (a2 - a1) * fraction
    val r = r1 + (r2 - r1) * fraction
    val g = g1 + (g2 - g1) * fraction
    val b = b1 + (b2 - b1) * fraction

    return ((a * 255).toInt() shl 24) or
            ((r * 255).toInt() shl 16) or
            ((g * 255).toInt() shl 8) or
            (b * 255).toInt()
}