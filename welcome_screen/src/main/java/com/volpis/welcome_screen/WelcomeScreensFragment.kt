package com.volpis.welcome_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Button
import android.widget.FrameLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.volpis.welcome_screen.config.IndicatorPlacement
import com.volpis.welcome_screen.config.WelcomeScreenConfig
import com.volpis.welcome_screen.utils.configure
import com.volpis.welcome_screen.utils.createStyledButton
import com.volpis.welcome_screen.utils.positionButton
import com.volpis.welcome_screen.views.PageIndicatorView
import com.volpis.welcome_screen.views.WelcomeScreenPagerAdapter
import kotlin.math.abs

class WelcomeScreensFragment : Fragment() {

    private lateinit var rootContainer: FrameLayout
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
        handleSystemInsets(view)
    }

    private fun initViews(view: View) {
        rootContainer = view.findViewById(R.id.welcome_screens_root)
        viewPager = view.findViewById(R.id.viewPager)
        pageIndicator = view.findViewById(R.id.pageIndicator)
        buttonContainer = view.findViewById(R.id.buttonContainer)

        skipButton = requireContext().createStyledButton(config.skipButton)
        nextButton = requireContext().createStyledButton(config.nextButton)
        previousButton = requireContext().createStyledButton(config.previousButton)
        finishButton = requireContext().createStyledButton(config.finishButton)

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
        animateInitialEntrance()
    }

    private fun setupViewPager() {
        val adapter = WelcomeScreenPagerAdapter(screens, config)
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = 1
        viewPager.isUserInputEnabled = true

        viewPager.setPageTransformer { page, position ->
            applyPageTransformation(page, position)
        }

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateButtonVisibility(position)
                animateButtonChanges()
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                if (config.enableParallaxEffect) {
                    applyParallaxEffect(position, positionOffset)
                }
            }
        })
    }

    private fun applyPageTransformation(page: View, position: Float) {
        when {
            position < -1 -> {
                page.alpha = 0f
            }

            position <= 1 -> {
                page.alpha = 1f

                val scaleFactor = 0.95f.coerceAtLeast(1 - abs(position) * 0.05f)
                page.scaleX = scaleFactor
                page.scaleY = scaleFactor

                page.translationX = position * -30f
            }

            else -> {
                page.alpha = 0f
            }
        }
    }

    private fun applyParallaxEffect(position: Int, positionOffset: Float) {
        val parallaxOffset = (position + positionOffset) * config.parallaxIntensity * 50f
        rootContainer.translationX = -parallaxOffset
    }

    private fun setupPageIndicator() {
        pageIndicator.setupWithViewPager(viewPager, config.pageIndicator)
        positionPageIndicator()
    }

    private fun positionPageIndicator() {
        val indicatorParams = pageIndicator.layoutParams as FrameLayout.LayoutParams

        when (config.pageIndicator.placement) {
            IndicatorPlacement.CENTER_BELOW_DESCRIPTION -> {
                indicatorParams.gravity =
                    android.view.Gravity.CENTER_HORIZONTAL or android.view.Gravity.BOTTOM
                indicatorParams.bottomMargin =
                    requireContext().resources.getDimensionPixelSize(R.dimen.page_indicator_margin_bottom)
            }

            IndicatorPlacement.BOTTOM_CENTER -> {
                indicatorParams.gravity =
                    android.view.Gravity.CENTER_HORIZONTAL or android.view.Gravity.BOTTOM
                indicatorParams.bottomMargin =
                    requireContext().resources.getDimensionPixelSize(R.dimen.welcome_button_margin) * 2
            }

            IndicatorPlacement.TOP_CENTER -> {
                indicatorParams.gravity =
                    android.view.Gravity.CENTER_HORIZONTAL or android.view.Gravity.TOP
                indicatorParams.topMargin =
                    requireContext().resources.getDimensionPixelSize(R.dimen.welcome_button_margin) * 2
            }
        }

        pageIndicator.layoutParams = indicatorParams
    }

    private fun setupButtons() {
        skipButton.configure(config.skipButton) {
            animateButtonClick(skipButton) {
                onSkip?.invoke()
            }
        }

        nextButton.configure(config.nextButton) {
            animateButtonClick(nextButton) {
                val currentItem = viewPager.currentItem
                if (currentItem < screens.size - 1) {
                    viewPager.setCurrentItem(currentItem + 1, true)
                }
            }
        }

        previousButton.configure(config.previousButton) {
            animateButtonClick(previousButton) {
                val currentItem = viewPager.currentItem
                if (currentItem > 0) {
                    viewPager.setCurrentItem(currentItem - 1, true)
                }
            }
        }

        finishButton.configure(config.finishButton) {
            animateButtonClick(finishButton) {
                onFinish?.invoke()
            }
        }

        skipButton.positionButton(config.skipButton.placement, 0, 0)
        nextButton.positionButton(config.nextButton.placement, 0, 0)
        previousButton.positionButton(config.previousButton.placement, 0, 0)
        finishButton.positionButton(config.finishButton.placement, 0, 0)

        updateButtonVisibility(0)
    }

    private fun animateButtonClick(button: Button, action: () -> Unit) {
        button.animate()
            .scaleX(0.95f)
            .scaleY(0.95f)
            .setDuration(100)
            .withEndAction {
                button.animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(100)
                    .withEndAction { action() }
                    .start()
            }
            .start()
    }

    private fun updateButtonVisibility(position: Int) {
        val isFirstPage = position == 0
        val isLastPage = position == screens.size - 1

        val skipVisibility = if (config.skipButton.isVisible && !isLastPage) {
            View.VISIBLE
        } else {
            View.GONE
        }

        val previousVisibility = if (config.previousButton.isVisible && !isFirstPage) {
            View.VISIBLE
        } else {
            View.GONE
        }

        val nextVisibility = if (config.nextButton.isVisible && !isLastPage) {
            View.VISIBLE
        } else {
            View.GONE
        }

        val finishVisibility = if (config.finishButton.isVisible && isLastPage) {
            View.VISIBLE
        } else {
            View.GONE
        }

        skipButton.visibility = skipVisibility
        previousButton.visibility = previousVisibility
        nextButton.visibility = nextVisibility
        finishButton.visibility = finishVisibility
    }

    private fun animateButtonChanges() {
        listOf(skipButton, nextButton, previousButton, finishButton).forEach { button ->
            if (button.isVisible) {
                button.alpha = 0f
                button.animate()
                    .alpha(1f)
                    .setDuration(300)
                    .setInterpolator(AccelerateDecelerateInterpolator())
                    .start()
            }
        }
    }

    private fun setupBackground() {
        rootContainer.setBackgroundColor(config.backgroundColor)

        if (config.useGradientBackground && config.backgroundGradientColor != null) {
            rootContainer.background = createGradientDrawable(
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

    private fun animateInitialEntrance() {
        rootContainer.alpha = 0f
        rootContainer.animate()
            .alpha(1f)
            .setDuration(500)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .start()

        pageIndicator.alpha = 0f
        pageIndicator.translationY = 50f
        pageIndicator.animate()
            .alpha(1f)
            .translationY(0f)
            .setDuration(600)
            .setStartDelay(300)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .start()

        animateButtonsEntrance()
    }

    private fun animateButtonsEntrance() {
        val visibleButtons = listOf(skipButton, nextButton, previousButton, finishButton)
            .filter { it.isVisible }

        visibleButtons.forEachIndexed { index, button ->
            button.alpha = 0f
            button.translationY = 30f
            button.animate()
                .alpha(1f)
                .translationY(0f)
                .setDuration(400)
                .setStartDelay(500 + index * 100L)
                .setInterpolator(AccelerateDecelerateInterpolator())
                .start()
        }
    }

    private fun handleSystemInsets(view: View) {
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )

            adjustButtonPositionsForInsets(systemBars)

            insets
        }
    }

    private fun adjustButtonPositionsForInsets(systemBars: androidx.core.graphics.Insets) {
        val extraBottomMargin = systemBars.bottom

        listOf(nextButton, previousButton, finishButton).forEach { button ->
            (button.layoutParams as? FrameLayout.LayoutParams)?.let { params ->
                params.bottomMargin += extraBottomMargin
                button.layoutParams = params
            }
        }
    }

    override fun onResume() {
        super.onResume()
        pageIndicator.resetAnimations()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewPager.adapter = null
    }
}