package com.volpis.welcome_screen.config

import android.graphics.Typeface
import android.graphics.Typeface.DEFAULT
import android.graphics.Typeface.DEFAULT_BOLD
import android.graphics.Typeface.MONOSPACE
import android.graphics.Typeface.SANS_SERIF
import android.graphics.Typeface.SERIF

enum class WelcomeTypeface {
    DEFAULT, DEFAULT_BOLD, SANS_SERIF, SERIF, MONOSPACE

    ;

    fun toTypeface(): Typeface {
        return when (this) {
            DEFAULT -> Typeface.DEFAULT
            DEFAULT_BOLD -> Typeface.DEFAULT_BOLD
            SANS_SERIF -> Typeface.SANS_SERIF
            SERIF -> Typeface.SERIF
            MONOSPACE -> Typeface.MONOSPACE
        }
    }
}

fun Typeface.asWelcomeTypeface(): WelcomeTypeface {
    return when (this) {
        DEFAULT -> WelcomeTypeface.DEFAULT
        DEFAULT_BOLD -> WelcomeTypeface.DEFAULT_BOLD
        SANS_SERIF -> WelcomeTypeface.SANS_SERIF
        SERIF -> WelcomeTypeface.SERIF
        MONOSPACE -> WelcomeTypeface.MONOSPACE
        else -> WelcomeTypeface.DEFAULT
    }
}