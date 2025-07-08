package com.volpis.welcome_screen.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.volpis.welcome_screen.R
import com.volpis.welcome_screen.WelcomeScreenData
import com.volpis.welcome_screen.config.WelcomeScreenConfig

class WelcomeScreenPagerAdapter(
    private val screens: List<WelcomeScreenData>,
    private val config: WelcomeScreenConfig
) : RecyclerView.Adapter<WelcomeScreenPagerAdapter.WelcomeScreenViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WelcomeScreenViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_welcome_screen, parent, false)
        return WelcomeScreenViewHolder(view as android.widget.FrameLayout)
    }

    override fun onBindViewHolder(holder: WelcomeScreenViewHolder, position: Int) {
        holder.bind(screens[position], config, position)
    }

    override fun getItemCount(): Int = screens.size

    override fun onViewAttachedToWindow(holder: WelcomeScreenViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.startEntranceAnimation()
    }

    override fun onViewDetachedFromWindow(holder: WelcomeScreenViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.stopAnimations()
    }

    class WelcomeScreenViewHolder(
        private val containerView: android.widget.FrameLayout
    ) : RecyclerView.ViewHolder(containerView) {

        private var welcomeScreenView: WelcomeScreenView? = null

        fun bind(screenData: WelcomeScreenData, config: WelcomeScreenConfig, position: Int) {
            containerView.removeAllViews()

            welcomeScreenView = WelcomeScreenView(containerView.context).apply {
                setupScreen(screenData, config)

                contentDescription = "Welcome screen ${position + 1} of ${
                    config.let {
                        "Welcome screen ${position + 1}"
                    }
                }"

                importantForAccessibility = android.view.View.IMPORTANT_FOR_ACCESSIBILITY_YES
            }

            val layoutParams = android.widget.FrameLayout.LayoutParams(
                android.widget.FrameLayout.LayoutParams.MATCH_PARENT,
                android.widget.FrameLayout.LayoutParams.MATCH_PARENT
            )

            containerView.addView(welcomeScreenView, layoutParams)

            setupGestureHandling()
        }

        private fun setupGestureHandling() {
            containerView.apply {
                isClickable = true
                isFocusable = true

                setOnTouchListener { view, event ->
                    when (event.action) {
                        android.view.MotionEvent.ACTION_DOWN -> {
                            view.animate()
                                .scaleX(0.98f)
                                .scaleY(0.98f)
                                .setDuration(100)
                                .start()
                        }

                        android.view.MotionEvent.ACTION_UP,
                        android.view.MotionEvent.ACTION_CANCEL -> {
                            view.animate()
                                .scaleX(1f)
                                .scaleY(1f)
                                .setDuration(100)
                                .start()
                        }
                    }
                    // TODO: check if view.performClick() doesn't break anything
                    //  view.performClick()
                    false
                }
            }
        }

        fun startEntranceAnimation() {
            welcomeScreenView?.startEntranceAnimation()
        }

        fun stopAnimations() {
            // Cancel any running animations
            containerView.animate().cancel()
            welcomeScreenView?.animate()?.cancel()
        }
    }

    fun getItemAt(position: Int): WelcomeScreenData? {
        return if (position in 0 until screens.size) screens[position] else null
    }

    fun isValidPosition(position: Int): Boolean {
        return position in 0 until screens.size
    }
}