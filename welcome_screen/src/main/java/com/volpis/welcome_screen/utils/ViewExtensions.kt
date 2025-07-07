package com.volpis.welcome_screen.utils

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.TypedValue
import android.view.View
import android.widget.Button
import androidx.core.content.ContextCompat
import com.volpis.welcome_screen.config.ButtonConfig
import com.volpis.welcome_screen.config.ButtonPlacement

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

fun Button.configure(buttonConfig: ButtonConfig, onClick: () -> Unit) {
    if (!buttonConfig.isVisible) {
        visibility = View.GONE
        return
    }

    visibility = View.VISIBLE
    text = buttonConfig.text

    val style = buttonConfig.style

    // Configure appearance
    setTextColor(style.textColor)
    textSize = style.textSize / resources.displayMetrics.scaledDensity
    typeface = style.typeface

    // Configure background
    val backgroundDrawable = GradientDrawable().apply {
        setColor(style.backgroundColor)
        cornerRadius = style.cornerRadius
        if (style.borderWidth > 0) {
            setStroke(style.borderWidth, style.borderColor)
        }
    }

    background = backgroundDrawable

    // Configure padding
    setPadding(
        style.paddingHorizontal,
        style.paddingVertical,
        style.paddingHorizontal,
        style.paddingVertical
    )

    // Configure click listener
    setOnClickListener { onClick() }

    // Configure elevation (for Material Design)
    elevation = context.dpToPx(4).toFloat()

    // Handle text styling
    if (style.isUnderlined || style.isBold) {
        val paintFlags = paint.flags
        if (style.isUnderlined) {
            paint.flags = paintFlags or android.graphics.Paint.UNDERLINE_TEXT_FLAG
        }
        if (style.isBold) {
            typeface = android.graphics.Typeface.DEFAULT_BOLD
        }
    }
}

fun View.positionButton(placement: ButtonPlacement, parentWidth: Int, parentHeight: Int) {
    val layoutParams = layoutParams as android.widget.FrameLayout.LayoutParams

    when (placement) {
        ButtonPlacement.TOP_LEFT -> {
            layoutParams.gravity = android.view.Gravity.TOP or android.view.Gravity.START
            layoutParams.setMargins(context.dpToPx(16), context.dpToPx(16), 0, 0)
        }

        ButtonPlacement.TOP_RIGHT -> {
            layoutParams.gravity = android.view.Gravity.TOP or android.view.Gravity.END
            layoutParams.setMargins(0, context.dpToPx(16), context.dpToPx(16), 0)
        }

        ButtonPlacement.TOP_CENTER -> {
            layoutParams.gravity =
                android.view.Gravity.TOP or android.view.Gravity.CENTER_HORIZONTAL
            layoutParams.setMargins(0, context.dpToPx(16), 0, 0)
        }

        ButtonPlacement.BOTTOM_LEFT -> {
            layoutParams.gravity = android.view.Gravity.BOTTOM or android.view.Gravity.START
            layoutParams.setMargins(context.dpToPx(16), 0, 0, context.dpToPx(16))
        }

        ButtonPlacement.BOTTOM_RIGHT -> {
            layoutParams.gravity = android.view.Gravity.BOTTOM or android.view.Gravity.END
            layoutParams.setMargins(0, 0, context.dpToPx(16), context.dpToPx(16))
        }

        ButtonPlacement.BOTTOM_CENTER -> {
            layoutParams.gravity =
                android.view.Gravity.BOTTOM or android.view.Gravity.CENTER_HORIZONTAL
            layoutParams.setMargins(0, 0, 0, context.dpToPx(16))
        }

        ButtonPlacement.CENTER_LEFT -> {
            layoutParams.gravity =
                android.view.Gravity.CENTER_VERTICAL or android.view.Gravity.START
            layoutParams.setMargins(context.dpToPx(16), 0, 0, 0)
        }

        ButtonPlacement.CENTER_RIGHT -> {
            layoutParams.gravity = android.view.Gravity.CENTER_VERTICAL or android.view.Gravity.END
            layoutParams.setMargins(0, 0, context.dpToPx(16), 0)
        }
    }

    this.layoutParams = layoutParams
}