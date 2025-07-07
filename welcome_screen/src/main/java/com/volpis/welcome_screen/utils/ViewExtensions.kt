package com.volpis.welcome_screen.utils

import android.content.Context
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
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

    setTextColor(style.textColor)
    setTextSize(TypedValue.COMPLEX_UNIT_PX, style.textSize)

    typeface = when (style.fontWeight) {
        600, 700, 800, 900 -> Typeface.DEFAULT_BOLD
        else -> style.typeface
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

    setOnClickListener { onClick() }

    elevation = context.dpToPx(4).toFloat()

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
}

fun View.positionButton(placement: ButtonPlacement, parentWidth: Int, parentHeight: Int) {
    val layoutParams = FrameLayout.LayoutParams(
        FrameLayout.LayoutParams.WRAP_CONTENT,
        FrameLayout.LayoutParams.WRAP_CONTENT
    )

    val margin16 = context.dpToPx(16)
    val margin24 = context.dpToPx(24)

    when (placement) {
        ButtonPlacement.TOP_LEFT -> {
            layoutParams.gravity = Gravity.TOP or Gravity.START
            layoutParams.setMargins(margin16, margin16, 0, 0)
        }

        ButtonPlacement.TOP_RIGHT -> {
            layoutParams.gravity = Gravity.TOP or Gravity.END
            layoutParams.setMargins(0, margin16, margin16, 0)
        }

        ButtonPlacement.TOP_CENTER -> {
            layoutParams.gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
            layoutParams.setMargins(0, margin16, 0, 0)
        }

        ButtonPlacement.BOTTOM_LEFT -> {
            layoutParams.gravity = Gravity.BOTTOM or Gravity.START
            layoutParams.setMargins(margin24, 0, 0, margin24)
        }

        ButtonPlacement.BOTTOM_RIGHT -> {
            layoutParams.gravity = Gravity.BOTTOM or Gravity.END
            layoutParams.setMargins(0, 0, margin24, margin24)
        }

        ButtonPlacement.BOTTOM_CENTER -> {
            layoutParams.gravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
            layoutParams.setMargins(margin24, 0, margin24, margin24)
            layoutParams.width = FrameLayout.LayoutParams.MATCH_PARENT
        }

        ButtonPlacement.CENTER_LEFT -> {
            layoutParams.gravity = Gravity.CENTER_VERTICAL or Gravity.START
            layoutParams.setMargins(margin16, 0, 0, 0)
        }

        ButtonPlacement.CENTER_RIGHT -> {
            layoutParams.gravity = Gravity.CENTER_VERTICAL or Gravity.END
            layoutParams.setMargins(0, 0, margin16, 0)
        }
    }

    this.layoutParams = layoutParams
}

fun Context.createStyledButton(buttonConfig: ButtonConfig): Button {
    return Button(this).apply {
        val outValue = TypedValue()
        theme.resolveAttribute(android.R.attr.selectableItemBackground, outValue, true)

        configure(buttonConfig) { /* onClick will be set separately */ }

        isClickable = true
        isFocusable = true
    }
}