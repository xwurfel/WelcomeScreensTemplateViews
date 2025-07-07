package com.volpis.welcome_screen

import androidx.fragment.app.Fragment
import com.volpis.welcome_screen.config.WelcomeScreenConfig
import com.volpis.welcome_screen.config.WelcomeScreenConfigFactory

object WelcomeScreensFactory {

    /**
     * Creates a WelcomeScreensFragment with the provided configuration
     */
    fun createFragment(
        screens: List<WelcomeScreenData>,
        config: WelcomeScreenConfig = WelcomeScreenConfig(),
        onSkip: (() -> Unit)? = null,
        onFinish: (() -> Unit)? = null
    ): Fragment {
        return WelcomeScreensFragment.newInstance(
            screens = screens,
            config = config,
            onSkip = onSkip,
            onFinish = onFinish
        )
    }


    /**
     * Create a simple fragment with default modern configuration
     */
    fun createModernFragment(
        screens: List<WelcomeScreenData>,
        onSkip: (() -> Unit)? = null,
        onFinish: (() -> Unit)? = null
    ): Fragment {
        return createFragment(
            screens = screens,
            config = WelcomeScreenConfigFactory.createModernConfig(),
            onSkip = onSkip,
            onFinish = onFinish
        )
    }

    /**
     * Create a minimal fragment with simple configuration
     */
    fun createMinimalFragment(
        screens: List<WelcomeScreenData>,
        onSkip: (() -> Unit)? = null,
        onFinish: (() -> Unit)? = null
    ): Fragment {
        return createFragment(
            screens = screens,
            config = WelcomeScreenConfigFactory.createMinimalConfig(),
            onSkip = onSkip,
            onFinish = onFinish
        )
    }

    /**
     * Create a dark theme fragment
     */
    fun createDarkFragment(
        screens: List<WelcomeScreenData>,
        onSkip: (() -> Unit)? = null,
        onFinish: (() -> Unit)? = null
    ): Fragment {
        return createFragment(
            screens = screens,
            config = WelcomeScreenConfigFactory.createDarkThemeConfig(),
            onSkip = onSkip,
            onFinish = onFinish
        )
    }
}