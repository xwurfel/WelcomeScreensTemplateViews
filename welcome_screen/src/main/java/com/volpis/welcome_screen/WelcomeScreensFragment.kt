package com.volpis.welcome_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.volpis.welcome_screen.config.WelcomeScreenConfig
import com.volpis.welcome_screen.utils.configure
import com.volpis.welcome_screen.utils.positionButton
import com.volpis.welcome_screen.views.PageIndicatorView
import com.volpis.welcome_screen.views.WelcomeScreenPagerAdapter

class WelcomeScreensFragment : Fragment() {

    private lateinit var viewPager: ViewPager2
    private lateinit var pageIndicator: PageIndicatorView
    private lateinit var buttonContainer: FrameLayout

    private lateinit var skipButton: Button
    private lateinit var nextButton: Button
    private lateinit var previousButton: Button
    private lateinit var finishButton: Button

    private lateinit var screens: List<WelcomeScreenData>
    private lateinit var config: WelcomeScreenConfig

    private var onSkip: (() -> Unit)? = null
    private var onFinish: (() -> Unit)? = null

    companion object {
        fun newInstance(
            screens: List<WelcomeScreenData>,
            config: WelcomeScreenConfig,
            onSkip: (() -> Unit)? = null,
            onFinish: (() -> Unit)? = null
        ): WelcomeScreensFragment {
            return WelcomeScreensFragment().apply {
                this.screens = screens
                this.config = config
                this.onSkip = onSkip
                this.onFinish = onFinish
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_welcome_screens, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
        setupWelcomeScreens()
    }

    private fun initViews(view: View) {
        viewPager = view.findViewById(R.id.viewPager)
        pageIndicator = view.findViewById(R.id.pageIndicator)
        buttonContainer = view.findViewById(R.id.buttonContainer)

        skipButton = Button(requireContext())
        nextButton = Button(requireContext())
        previousButton = Button(requireContext())
        finishButton = Button(requireContext())

        buttonContainer.addView(skipButton)
        buttonContainer.addView(nextButton)
        buttonContainer.addView(previousButton)
        buttonContainer.addView(finishButton)
    }

    private fun setupWelcomeScreens() {
        setupViewPager()
        setupPageIndicator()
        setupButtons()
        setupBackground()
    }

    private fun setupViewPager() {
        val adapter = WelcomeScreenPagerAdapter(screens, config)
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = 1

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateButtonVisibility(position)
            }
        })
    }

    private fun setupPageIndicator() {
        pageIndicator.setupWithViewPager(viewPager, config.pageIndicator)
    }

    private fun setupButtons() {
        // Skip button
        skipButton.configure(config.skipButton) {
            onSkip?.invoke()
        }
        skipButton.positionButton(config.skipButton.placement, 0, 0)

        // Next button
        nextButton.configure(config.nextButton) {
            val currentItem = viewPager.currentItem
            if (currentItem < screens.size - 1) {
                viewPager.currentItem = currentItem + 1
            }
        }
        nextButton.positionButton(config.nextButton.placement, 0, 0)

        // Previous button
        previousButton.configure(config.previousButton) {
            val currentItem = viewPager.currentItem
            if (currentItem > 0) {
                viewPager.currentItem = currentItem - 1
            }
        }
        previousButton.positionButton(config.previousButton.placement, 0, 0)

        // Finish button
        finishButton.configure(config.finishButton) {
            onFinish?.invoke()
        }
        finishButton.positionButton(config.finishButton.placement, 0, 0)

        updateButtonVisibility(0)
    }

    private fun updateButtonVisibility(position: Int) {
        val isFirstPage = position == 0
        val isLastPage = position == screens.size - 1

        // Skip button: visible on all pages except last (if configured)
        skipButton.visibility = if (config.skipButton.isVisible && !isLastPage) {
            View.VISIBLE
        } else {
            View.GONE
        }

        // Previous button: visible on all pages except first (if configured)
        previousButton.visibility = if (config.previousButton.isVisible && !isFirstPage) {
            View.VISIBLE
        } else {
            View.GONE
        }

        // Next button: visible on all pages except last (if configured)
        nextButton.visibility = if (config.nextButton.isVisible && !isLastPage) {
            View.VISIBLE
        } else {
            View.GONE
        }

        // Finish button: visible only on last page (if configured)
        finishButton.visibility = if (config.finishButton.isVisible && isLastPage) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    private fun setupBackground() {
        val rootView = view?.findViewById<View>(R.id.welcome_screens_root)
        rootView?.setBackgroundColor(config.backgroundColor)

        if (config.useGradientBackground && config.backgroundGradientColor != null) {
            rootView?.background = createGradientDrawable(
                config.backgroundColor,
                config.backgroundGradientColor!!
            )
        }
    }

    private fun createGradientDrawable(
        startColor: Int,
        endColor: Int
    ): android.graphics.drawable.GradientDrawable {
        return android.graphics.drawable.GradientDrawable(
            android.graphics.drawable.GradientDrawable.Orientation.TOP_BOTTOM,
            intArrayOf(startColor, endColor)
        )
    }
}