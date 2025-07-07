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
        holder.bind(screens[position], config)
    }

    override fun getItemCount(): Int = screens.size

    class WelcomeScreenViewHolder(
        private val containerView: android.widget.FrameLayout
    ) : RecyclerView.ViewHolder(containerView) {

        fun bind(screenData: WelcomeScreenData, config: WelcomeScreenConfig) {
            containerView.removeAllViews()

            val welcomeScreenView = WelcomeScreenView(containerView.context)
            welcomeScreenView.setupScreen(screenData, config)

            val layoutParams = android.widget.FrameLayout.LayoutParams(
                android.widget.FrameLayout.LayoutParams.MATCH_PARENT,
                android.widget.FrameLayout.LayoutParams.MATCH_PARENT
            )
            containerView.addView(welcomeScreenView, layoutParams)
        }
    }
}