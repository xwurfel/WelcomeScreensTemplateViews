package com.volpis.welcome_screen

import android.content.Context
import android.content.Intent
import com.volpis.welcome_screen.config.WelcomeScreenConfig
import kotlinx.serialization.json.Json

object WelcomeScreensFactory {

    /**
     * Creates and launches welcome screens activity
     */
    fun launch(
        context: Context,
        screens: List<WelcomeScreenData>,
        config: WelcomeScreenConfig = WelcomeScreenConfig(),
        onSkip: (() -> Unit)? = null,
        onFinish: (() -> Unit)? = null
    ) {
        val intent = Intent(context, WelcomeScreensActivity::class.java)
        context.startActivity(intent)
    }

    /**
     * Creates a welcome screens fragment that can be embedded in other activities
     */
    fun createFragment(
        screens: List<WelcomeScreenData>,
        config: WelcomeScreenConfig = WelcomeScreenConfig(),
        onSkip: (() -> Unit)? = null,
        onFinish: (() -> Unit)? = null
    ): WelcomeScreensFragment {
        return WelcomeScreensFragment.newInstance(screens, config, onSkip, onFinish)
    }

    private const val EXTRA_SCREENS = "extra_screens"
    private const val EXTRA_CONFIG = "extra_config"
}